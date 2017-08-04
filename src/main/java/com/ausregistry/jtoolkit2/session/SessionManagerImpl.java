package com.ausregistry.jtoolkit2.session;

import java.io.IOException;
import java.util.logging.Logger;

import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.Greeting;
import com.ausregistry.jtoolkit2.se.Response;
import com.ausregistry.jtoolkit2.se.Result;
import com.ausregistry.jtoolkit2.se.ResultCode;
import com.ausregistry.jtoolkit2.xml.EPPSchemaProvider;
import com.ausregistry.jtoolkit2.xml.ParsingException;

/**
 * <p>
 * AusRegistry&rsquo;s basic implementation of the SessionManager interface. Upon successful configuration, it
 * guarantees that a pool of {@link com.ausregistry.jtoolkit2.session.Session}s will be available for processing
 * {@link com.ausregistry.jtoolkit2.session.Transaction}s. A SessionManager is configured from a
 * {@link com.ausregistry.jtoolkit2.session.SessionManagerProperties} object. This implementation provides only a
 * blocking implementation of the <code>execute</code> method. It will create a
 * {@link com.ausregistry.jtoolkit2.session.SessionPool}. By default, the
 * {@link com.ausregistry.jtoolkit2.session.SessionPool} will use the
 * {@link com.ausregistry.jtoolkit2.session.TLSSession} implementation of the
 * {@link com.ausregistry.jtoolkit2.session.Session} interface. It also implements its own
 * {@link com.ausregistry.jtoolkit2.session.StatsViewer}.
 * </p>
 *
 * <p>
 * Uses the debug and user level loggers.
 * </p>
 */
public class SessionManagerImpl implements SessionManager {
    private static final int MAX_SLEEP_INTERRUPTS_TO_FAIL = 3;
    private static final int MAX_ACCEPTABLE_FAIL_COUNT = 5;

    private enum SMState {
        STOPPED, STARTED, RUNNING;
    }

    private Thread runThread;
    private SMState state;
    private SessionPoolImpl sessionPool;

    private SessionManagerProperties properties;

    private Logger debugLogger;
    private Logger userLogger;

    private String pname;

    {
        pname = getClass().getPackage().getName();
        state = SMState.STOPPED;
    }

    // / For use by factory methods.
    SessionManagerImpl() {
    }

    SessionManagerImpl(SessionManagerProperties props) throws ConfigurationException {
        configure(props);
    }

    @Override
    public SessionManagerProperties getProperties() {
        return properties;
    }

    /**
     * Configure the SessionManager from the given set of properties. This can also be used to reconfigure the
     * SessionManager dynamically at any time, including changes to the managed
     * {@link com.ausregistry.jtoolkit2.session.SessionPool}. A change of password should be performed using {@link
     * #changePassword(String, String) changePassword}. Following a change of password,
     * the properties source should be updated to reflect the change. If this is not done, then the next invocation of
     * configure will fail due to session login failures. A successful call to configure indicates that subsequent
     * invocations of <a href="#execute(com.ausregistry.jtoolkit2.session.Transaction)">execute</a> may succeed
     * (dependent on the semantics of the command and the context). If the configuration fails and a previous invocation
     * of configure succeeded, then the previous configuration remains in effect, but the causal exception is thrown. If
     * the configuration fails and there was no previous valid configuration, then an exception is raised and the
     * SessionManager instance remains unusable (invocations of execute are guaranteed to fail, raising one of
     * FatalSessionException, IOException or InvalidStateException).
     */
    @Override
    public void configure(SessionManagerProperties properties) throws ConfigurationException {

        boolean cfgSucceeded = false;
        SessionManagerProperties prevProperties = this.properties;
        this.properties = properties;

        try {
            Exception exception = null;
            try {
                doConfigure();
                cfgSucceeded = true;
            } catch (Exception e) {
                cfgSucceeded = false;
                exception = e;
            }

            if (!cfgSucceeded) {
                if (prevProperties != null) {
                    this.properties = prevProperties;
                    try {
                        doConfigure();
                    } catch (Exception e) {
                        this.properties = null;
                        throw e;
                    }
                } else {
                    this.properties = null;
                }
                throw exception;
            }
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    private void doConfigure() throws Exception {
        debugLogger = Logger.getLogger(pname + ".debug");
        userLogger = Logger.getLogger(pname + ".user");
        debugLogger.info("Logging configured from file: " + System.getProperty("java.util.logging.config.file"));

        EPPSchemaProvider.init();
        EPPSchemaProvider.setValidating(properties.getSessionProperties().enforceStrictValidation());
        // Need to empty and re-initialise pool if already in use.
        if (sessionPool != null) {
            sessionPool.empty();
        }

        sessionPool = new SessionPoolImpl(properties.getSessionPoolProperties(), properties.getSessionProperties());
    }

    /**
     * Get the service discovery information embodied in the most recent Greeting received from the EPP server.
     *
     *
     * @throws SessionConfigurationException
     *             No prior connection to the EPP server had been established and the SessionPoolProperties provided to
     *             the SessionPool for the purpose of establishing a new Session were invalid.
     *
     * @throws SessionOpenException
     *             No prior connection to the EPP server had been established and the attempt to establish a connection
     *             for the purpose of retrieving service information failed.
     */
    @Override
    public Greeting getLastGreeting() throws SessionConfigurationException, SessionOpenException {

        try {
            return sessionPool.getLastGreeting();
        } catch (InterruptedException ie) {
            return null;
        }
    }

    /**
     * Prepare the SessionManager for providing Transaction processing services. This initialises the
     * {@link com.ausregistry.jtoolkit2.session.SessionPool} managed by the SessionManager, guaranteeing that any
     * requirements defined by SessionPool properties are met, providing the pool is initialised successfully.
     *
     * @throws SessionConfigurationException
     *             The pool was unable configure a session due to a configuration issue. Such problems include invalid
     *             server location specification and missing or invalid authentication resources.
     *
     * @throws SessionOpenException
     *             The pool was unable to open a session due to a configuration issue. Such problems include incorrect
     *             server location, failed authentication and network failure.
     */
    @Override
    public void startup() throws SessionConfigurationException, SessionOpenException {

        userLogger.info("Startup");

        int failCount = 0;
        boolean initialised = false;

        while (!initialised) {
            try {
                sessionPool.getLastGreeting();
                initialised = true;
                state = SMState.STARTED;
            } catch (InterruptedException ie) {
                failCount++;

                if (failCount < MAX_ACCEPTABLE_FAIL_COUNT) {
                    userLogger.warning(ErrorPkg.getMessage("startup.interrupted"));
                } else {
                    userLogger.severe(ErrorPkg.getMessage("startup.interrupted"));
                    throw new SessionOpenException(ie);
                }
            } catch (SessionOpenException soe) {
                Throwable t = soe.getCause();

                if (t instanceof LoginException && t.getCause() instanceof CommandFailedException
                        && failCount < MAX_ACCEPTABLE_FAIL_COUNT) {

                    failCount++;
                } else {
                    throw soe;
                }
            }
        }
    }

    /**
     * Shutdown the SessionManager, making it unavailable for further transaction processing.
     */
    @Override
    public void shutdown() {
        debugLogger.finest("enter");
        userLogger.info("Initiating shutdown");

        if (state == SMState.RUNNING) {
            debugLogger.info("state == RUNNING");
            state = SMState.STARTED;
            interruptThread(runThread);
        }

        if (state == SMState.STARTED) {
            debugLogger.info("state == STARTED");
            sessionPool.empty();

            state = SMState.STOPPED;
        }

        debugLogger.info("state == STOPPED");

        userLogger.info("Shutdown complete");
        debugLogger.finest("exit");
    }

    /**
     * Initiate the SessionPool's keep-alive system. This will run until <a href=#shutdown()>shutdown</a> is invoked on
     * the SessionManager.
     */
    @Override
    public void run() {
        debugLogger.finest("enter");

        if (state != SMState.STARTED) {
            return;
        }

        runThread = Thread.currentThread();
        state = SMState.RUNNING;

        try {
            while (state == SMState.RUNNING) {
                long sleepInterval = sessionPool.keepAlive();

                boolean interrupted = false;
                int retry = 0;
                int maxRetries = MAX_SLEEP_INTERRUPTS_TO_FAIL;

                long sleepTime, awakenTime;
                do {
                    sleepTime = Timer.now();

                    try {
                        retry++;
                        if (sleepInterval > 0) {
                            Thread.sleep(sleepInterval);
                        }
                        interrupted = false;
                    } catch (InterruptedException ie) {
                        // reduce the remaining sleep interval
                        awakenTime = Timer.now();
                        sleepInterval += sleepTime - awakenTime;
                        interrupted = true;
                    }
                } while (interrupted && retry < maxRetries && state == SMState.RUNNING);
            }
        } catch (IOException ioe) {
            userLogger.severe(ioe.getMessage());
            userLogger.severe(ioe.getCause().getMessage());
            userLogger.severe(ErrorPkg.getMessage("epp.session.poll.cfg.fail"));
        }

        debugLogger.finest("exit");
    }

    /**
     * Change the maximum size that the managed session pool will grow to. Note that this setting will not be saved to
     * the configuration source.
     */
    @Override
    public void changeMaxPoolSize(int size) {
        sessionPool.setMaxSize(size);
    }

    /**
     * Change the EPP client password from oldPassword to newPassword. Note that this does not update the configuration
     * source to reflect the change - that must be done separately before any future attempts to (re-)configure the
     * system.
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        debugLogger.finest("enter");

        userLogger.info(ErrorPkg.getMessage("reconfigure.pw.change.init", new String[] {"<<old>>", "<<new>>"},
                new String[] {oldPassword, newPassword}));

        sessionPool.empty();

        try {
            Session session = SessionFactory.newInstance(properties.getSessionProperties());
            session.changePassword(newPassword);
            // Attempts to get a session between changePassword and setClientPW
            // will fail if the password was successfully changed. It is the
            // application's responsibility to handle transaction failures
            // during a change of password. This is expected to occur very
            // infrequently.
            properties.getSessionProperties().setClientPW(newPassword);
        } catch (Exception ex) {
            userLogger.severe(ex.getMessage());
            userLogger.severe(ErrorPkg.getMessage("reconfigure.pw.change.fail"));
        }

        debugLogger.finest("exit");
    }

    /**
     * Try to process a single transaction. Up to {@code MAX_ACCEPTABLE_FAIL_COUNT} attempts will be made to process the
     * transaction in cases where I/O errors or non-protocol server errors occur during processing. Use of the
     * underlying session is protected against concurrent use by other threads by using the getSession/releaseSession
     * features of this SessionManager's {@link com.ausregistry.jtoolkit2.session.SessionPool}. This method guarantees
     * that the session used will be returned to the pool before the method returns.
     *
     * @throws FatalSessionException
     *             No session could be acquired to process the transaction. Check the exception message and log records
     *             for details.
     *
     * @throws IOException
     *             Every attempt to execute the transaction command failed due to an IOException. This is the last such
     *             IOException.
     *
     * @throws ParsingException
     *             Parsing of the response failed. Check the exception message for the cause.
     *
     * @throws CommandFailedException
     *             The acceptable limit on the number of failed commands due to server error was exceeded in trying to
     *             process the command. This probably indicates a server limitation related to the command being
     *             processed.
     *
     * @throws IllegalStateException
     *             The SessionManager had been shutdown or not started up prior to invoking this method.
     */
    @Override
    public void execute(Transaction tx) throws FatalSessionException, IOException, ParsingException,
            CommandFailedException, IllegalStateException {

        debugLogger.finest("enter");

        if (state == SMState.STOPPED) {
            throw new IllegalStateException();
        }

        Command cmd = tx.getCommand();
        Response response = tx.getResponse();

        int failCount = 0;
        boolean isExecuted = false;
        Session session = null;

        // if only processing one transaction, get a new session for each
        // attempt in case the session fails mid-transaction.
        while (!isExecuted && state != SMState.STOPPED) {
            try {
                session = sessionPool.getSession(cmd.getCommandType());
                StatsManager statsManager = session.getStatsManager();
                statsManager.incCommandCounter(cmd.getCommandType());

                tx.start();
                session.write(cmd);
                isExecuted = true;
                session.read(response);
                tx.setState(TransactionState.PROCESSED);
                statsManager.recordResponseTime(cmd.getCommandType(), tx.getResponseTime());

                Result[] results = response.getResults();
                assert results != null;
                if (results != null) {
                    for (Result result : results) {
                        assert result != null;
                        statsManager.incResultCounter(result.getResultCode());
                        int code = result.getResultCode();

                        if (ResultCode.CMD_FAILED == code || ResultCode.CMD_FAILED_CLOSING == code) {
                            throw new CommandFailedException();
                        }
                    }
                }
            } catch (CommandFailedException cfe) {
                userLogger.warning(cfe.getMessage());

                if (state != SMState.STOPPED && failCount < MAX_ACCEPTABLE_FAIL_COUNT) {

                    failCount++;
                } else {
                    throw cfe;
                }
            } catch (IOException ioe) {
                userLogger.warning(ioe.getMessage());
                userLogger.warning("net.socket.closed");
                session.close();
                if (state != SMState.STOPPED && failCount < MAX_ACCEPTABLE_FAIL_COUNT) {
                    failCount++;
                } else {
                    throw ioe;
                }
            } catch (InterruptedException ie) {
                // if interrupted by shutdown, then started will be false
                // Note: this still enters the finally block
                continue;
            } catch (SessionConfigurationException sce) {
                throw new FatalSessionException(sce);
            } catch (SessionOpenException soe) {
                throw new FatalSessionException(soe);
            } finally {
                sessionPool.releaseSession(session);
            }
        }

        if (!isExecuted && state == SMState.STOPPED) {
            throw new IllegalStateException();
        }

        debugLogger.finest("exit");
    }

    /**
     * Pipeline execute a sequence of commands over a single session. A single
     * {@link com.ausregistry.jtoolkit2.session.Session} is used in order to guarantee ordering of command effects. Only
     * those transactions in the UNPROCESSED or RETRY states are executed. The number of transactions in the PROCESSED
     * state following attempted execution of all eligible transactions is returned. If the number returned is less than
     * the number of transactions supplied, then the state of each transaction should be checked prior to getting
     * information from the contained response object. If the state is PROCESSED, then it is safe to use any of the
     * methods on the response. If the state is RETRY, then it is possible that a further attempt to execute the
     * transaction will succeed. If the state is FATAL_ERROR, then the transaction should not be re-attempted. In either
     * of these cases, the getCause method can be used to determine the reason the transaction failed. If the state
     * remains UNPROCESSED, it indicates that an error which occurred in processing an earlier transaction would have
     * prevented the successful processing of the UNPROCESSED transaction. These transactions should be retried once the
     * underlying cause of the first error has been resolved.
     *
     * @throws FatalSessionException
     *             No session was available to process any transactions (possibly due to misconfiguration or service
     *             unavailability - check the exception message and log records). See the description in
     *             {@link com.ausregistry.jtoolkit2.session.SessionManager#execute(Transaction[])} for recommended
     *             action.
     *
     * @throws IOException
     *             An I/O error occurred while trying to send some command in the given Transaction array.
     *
     * @throws IllegalStateException
     *             The SessionManager had been shutdown or not started up prior to invoking this method. See
     *             {@link com.ausregistry.jtoolkit2.session.SessionManager#startup} and
     *             {@link com.ausregistry.jtoolkit2.session.SessionManager#shutdown}.
     *
     * @return On success, the length of the transaction array; on failure, the index of the first failed transaction.
     */
    @Override
    public int execute(Transaction[] txs) throws FatalSessionException, IllegalStateException, IOException {

        debugLogger.finest("enter");

        if (state == SMState.STOPPED) {
            throw new IllegalStateException();
        }

        Session session = null;
        StatsManager statsManager = null;

        // when pipielining commands, a single session must be used for all
        // transactions in order to guarantee correct command sequence.

        // MUST ensure that this session is released before exit from this
        // method.
        session = sendCommandAndGetSession(txs);

        statsManager = session.getStatsManager();

        int successCount;

        int lastTxIdx = send(txs, session, statsManager);
        successCount = receive(txs, session, statsManager, lastTxIdx);

        sessionPool.releaseSession(session);

        debugLogger.finest("exit");
        return successCount;
    }

    /**
     * Return the index of the last transaction considered for sending.
     */
    private int send(Transaction[] txs, Session session, StatsManager statsManager) throws IOException {

        for (int i = 1; i < txs.length; i++) {
            switch (txs[i].getState()) {
            case PROCESSED:
            case FATAL_ERROR:
                continue;
            default:
            }

            Command command = txs[i].getCommand();
            txs[i].start();

            try {
                session.write(command);
                statsManager.incCommandCounter(command.getCommandType());
            } catch (ParsingException pe) {
                txs[i].setState(TransactionState.FATAL_ERROR);
                if (pe.getCause() instanceof SAXException) {
                    SAXException saxe = (SAXException) pe.getCause();
                    userLogger.warning(saxe.getMessage());
                    txs[i].setCause(saxe);
                } else {
                    userLogger.warning(pe.getMessage());
                    txs[i].setCause(pe);
                }
            } catch (IOException ioe) {
                userLogger.severe(ioe.getMessage());
                txs[i].setState(TransactionState.RETRY);
                txs[i].setCause(ioe);
                throw ioe;
            }
        }

        return txs.length - 1;
    }

    private int receive(Transaction[] txs, Session session, StatsManager statsManager, int lastTxIdx) {

        boolean isSessionReadable = true;
        int firstFailedIndex = Integer.MAX_VALUE;

        for (int j = 0; j <= lastTxIdx && isSessionReadable; j++) {
            switch (txs[j].getState()) {
            case PROCESSED:
                continue;
            case FATAL_ERROR:
                firstFailedIndex = Math.min(j, firstFailedIndex);
                continue;
            default:
            }

            Response response = txs[j].getResponse();

            try {
                session.read(response);
                txs[j].setState(TransactionState.PROCESSED);
                statsManager.recordResponseTime(txs[j].getCommand().getCommandType(), txs[j].getResponseTime());
            } catch (ParsingException pe) {
                txs[j].setState(TransactionState.FATAL_ERROR);
                if (pe.getCause() instanceof SAXException) {
                    SAXException saxe = (SAXException) pe.getCause();
                    userLogger.warning(saxe.getMessage());
                    txs[j].setCause(pe.getCause());
                } else {
                    userLogger.warning(pe.getMessage());
                    txs[j].setCause(pe);
                }

                // We can't trust that the message boundaries will be correct
                // following a parsing error, so the session must be closed in
                // order to prevent incorrect interpretation of further service
                // elements received via this session.
                session.close();
                firstFailedIndex = Math.min(j, firstFailedIndex);
                isSessionReadable = false;
            } catch (IOException ioe) {
                userLogger.warning(ioe.getMessage());
                txs[j].setState(TransactionState.RETRY);
                txs[j].setCause(ioe);
                firstFailedIndex = Math.min(j, firstFailedIndex);
                isSessionReadable = false;
            }
        }

        return Math.min(firstFailedIndex, txs.length);
    }

    private Session sendCommandAndGetSession(Transaction[] txs) throws FatalSessionException {

        Session session = null;
        Command command = txs[0].getCommand();
        int failCount = 0;

        while (state != SMState.STOPPED) {
            try {
                session = sessionPool.getSession(txs);
                txs[0].start();
                session.write(command);
                session.getStatsManager().incCommandCounter(command.getCommandType());
                return session;
            } catch (ParsingException pe) {
                txs[0].setState(TransactionState.FATAL_ERROR);
                txs[0].setCause(pe.getCause());
                return session;
            } catch (IOException ioe) {
                sessionPool.releaseSession(session);
                if (failCount < MAX_ACCEPTABLE_FAIL_COUNT) {
                    failCount++;
                } else {
                    throw new FatalSessionException(ioe);
                }
            } catch (InterruptedException ie) {
                sessionPool.releaseSession(session);
                userLogger.warning(ie.getMessage());
            } catch (SessionConfigurationException sce) {
                sessionPool.releaseSession(session);
                throw new FatalSessionException(sce);
            } catch (SessionOpenException soe) {
                sessionPool.releaseSession(session);
                throw new FatalSessionException(soe);
            }
        }

        throw new IllegalStateException();
    }

    private void interruptThread(Thread thread) {
        if (thread == null) {
            return;
        }
        try {
            thread.interrupt();
        } catch (SecurityException se) {
            userLogger.warning(ErrorPkg.getMessage("thread.interrupt.secex"));
        }
    }

    /**
     * Get the StatsViewer responsible for providing operating statistics about the SessionManager.
     */
    @Override
    public StatsViewer getStatsViewer() {
        return sessionPool.getStatsViewer();
    }
}
