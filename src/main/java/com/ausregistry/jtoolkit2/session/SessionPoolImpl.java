package com.ausregistry.jtoolkit2.session;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.ConfigurationError;
import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.CommandType;
import com.ausregistry.jtoolkit2.se.Greeting;

/**
 * This defines the operations or actions for managing a SessionPool and a StatsViewer.
 *
 * Uses the debug, support and user level loggers.
 */
public class SessionPoolImpl implements SessionPool, StatsViewer {
    private static final int MAX_ACCEPTABLE_FAIL_COUNT = 3;

    // / ratio of pollInterval to server timeout - must be < 0.5.
    private static final float PI_STO_FRACT = 0.4f;

    // / Minimum acceptable poll interval is 2 minutes. Reduce for testing.
    private static final long MIN_ACCEPTABLE_POLL_INTERVAL = 120000;

    private static final String[] CMD_LIMIT_PAIR = new String[] {"<<command>>", "<<limit>>" };

    private static final String[] SESSION_LIMIT_EXCEDED_ERROR_MSG_ARGS = new String[] {"<<total>>", "<<cutoff>>" };

    private static final String[] CMD_COUNT_LIMIT = new String[] {"<<command>>", "<<count>>", "<<limit>>" };

    private String pname;
    private Set<Session> pool;
    private Session lastSession;
    private int sessionsInUse;
    private int maximumSize;
    private long pollInterval;
    private final long waitTimeout;
    private long limitExceededWaitTimeout;

    // / Minimum duration to keep sessions alive.
    private final long clientTimeout;
    private final SessionProperties sessionProperties;

    private Logger debugLogger;
    private Logger userLogger;

    {
        pname = getClass().getPackage().getName();
        pool = new CopyOnWriteArraySet<Session>();
        sessionsInUse = 0;
        debugLogger = Logger.getLogger(pname + ".debug");
        userLogger = Logger.getLogger(pname + ".user");
    }

    SessionPoolImpl(SessionPoolProperties poolProps, SessionProperties sessionProps) {

        this.sessionProperties = sessionProps;
        maximumSize = poolProps.getMaximumPoolSize();
        pollInterval = (long) (poolProps.getServerTimeout() * PI_STO_FRACT);
        long minPollInterval = Long.valueOf(System.getProperty("client.pollinterval.min",
                String.valueOf(MIN_ACCEPTABLE_POLL_INTERVAL)));
        if (pollInterval < minPollInterval) {
            pollInterval = minPollInterval;
        }
        clientTimeout = poolProps.getClientTimeout();
        waitTimeout = poolProps.getWaitTimeout();

        // wait for lesser of 200ms and 10% command limit interval,
        // but no less than 50ms if command limit exceeded
        limitExceededWaitTimeout = Math.min(sessionProps.getCommandLimitInterval() / 10, 200L);
        limitExceededWaitTimeout = Math.max(limitExceededWaitTimeout, 50L);
    }

    @Override
    public void setMaxSize(int size) {
        maximumSize = size;
    }

    /**
     * Pull a greeting from the pool, forcing initialisation of the pool if a greeting has not yet been received.
     */
    @Override
    public Greeting getLastGreeting() throws SessionConfigurationException, SessionOpenException,
            InterruptedException {

        if (lastSession == null) {
            init();
        }

        return lastSession.getGreeting();
    }

    private void init() throws SessionConfigurationException, SessionOpenException, InterruptedException {
        releaseSession(getSession());
    }

    @Override
    public long keepAlive() throws IOException {
        debugLogger.finest("enter");

        debugLogger.fine("Pool size = " + pool.size());

        for (Session s : pool) {
            long mruInterval = s.getStatsManager().getMruInterval();

            if (pollInterval < mruInterval && mruInterval < clientTimeout) {
                s.keepAlive();
            }
        }

        debugLogger.finest("exit");
        return pollInterval;
    }

    @Override
    public synchronized void empty() {
        if (pool == null || pool.isEmpty()) {
            return;
        }

        for (Session session : pool) {
            if (session != null) {
                try {
                    try {
                        session.acquire();
                    } catch (TimeoutException te) {
                        userLogger.warning(ErrorPkg.getMessage("epp.session.pool.empty.acquire.timeout"));
                    }
                    session.close();
                } catch (InterruptedException ie) {
                    userLogger.info(ie.getMessage());
                }
            }
        }

        pool.clear();
    }

    @Override
    public synchronized void clean() throws SessionConfigurationException, SessionOpenException {

        int count = pool.size();
        empty();
        for (int i = 0; i < count; i++) {
            try {
                openSession();
            } catch (SessionOpenException e) {
                Throwable cause = e.getCause();
                if (cause instanceof SessionLimitExceededException) {
                    // It is possible that another client application will
                    // close some sessions while this is running, thereby
                    // allowing further sessions to be opened.
                    continue;
                } else {
                    throw e;
                }
            }
        }
    }

    @Override
    public Session getSession() throws SessionConfigurationException, SessionOpenException, InterruptedException {

        return getSessionImpl(null);
    }

    @Override
    public Session getSession(CommandType commandType) throws SessionConfigurationException, SessionOpenException,
            InterruptedException {
        return getSessionImpl(commandType);
    }

    Session getSession(Transaction[] txs) throws SessionConfigurationException, SessionOpenException,
            InterruptedException {
        return getSessionImpl(txs);
    }

    private Session getSessionImpl(Object obj) throws SessionConfigurationException, SessionOpenException,
            InterruptedException {

        debugLogger.finest("enter");

        Session acquiredSession = null;
        int failCount = 0;

        do {
            acquiredSession = getBestAvailableSession(obj);

            if (acquiredSession == null) {
                if (poolCanGrow()) {
                    try {
                        openSession();
                    } catch (SessionOpenException soe) {
                        waitOrMaybeFail(soe, failCount);
                        failCount++;
                    }
                } else {
                    waitForRelease();
                }
            }
        } while (acquiredSession == null);

        debugLogger.finest("exit");

        return acquiredSession;
    }

    private void waitOrMaybeFail(SessionOpenException soe, int failCount) throws SessionOpenException {
        Throwable cause = soe.getCause();
        if (cause instanceof SessionLimitExceededException) {
            userLogger.warning(ErrorPkg.getMessage("epp.session.open.fail.limit_exceeded",
                    SESSION_LIMIT_EXCEDED_ERROR_MSG_ARGS, new int[] {maximumSize, pool.size() }));
            // The server is not allowing me to open any more sessions,
            // therefore the actual maximum allowed concurrently open
            // sessions is probably the current number of open sessions.
            // Hence, I am constraining the pool to the current size.
            //
            constrainPoolToCurrentSize();
            if (pool.isEmpty()) {
                // No sessions are open, but the server is rejecting connections
                // from this client. Therefore, there is a configuration error.
                throw new ConfigurationError(cause);
            } else {
                waitForRelease();
            }
        } else {
            if (isRepeatableFailedLogin(cause, failCount)) {
                userLogger.warning(soe.getMessage());
                userLogger.warning(ErrorPkg.getMessage("epp.server.failure.retry", "<<count>>",
                        String.valueOf(failCount)));
            } else {
                throw soe;
            }
        }
    }

    /**
     * This should be used when the pool has been misconfigured to grow to a greater number of sessions than the EPP
     * server supports. The intention is to prevent an infinite loop in waiting for the release of a session.
     */
    private void constrainPoolToCurrentSize() {
        maximumSize = pool.size();
    }

    private boolean poolCanGrow() {
        return pool.size() < maximumSize;
    }

    private boolean isRepeatableFailedLogin(Throwable throwable, int count) {
        return (throwable instanceof LoginException && throwable.getCause() instanceof CommandFailedException
                && count < MAX_ACCEPTABLE_FAIL_COUNT);
    }

    private synchronized Session getBestAvailableSession(Object obj) throws InterruptedException {

        debugLogger.finest("enter");
        Session bestSession = null;

        Iterator<Session> iter = pool.iterator();
        CommandType type;
        if (obj instanceof Transaction[]) {
            Transaction[] txs = (Transaction[]) obj;

            sessionSearch: while (iter.hasNext()) {
                Session session = iter.next();
                if (session.isAvailable()) {
                    for (Transaction tx : txs) {
                        CommandType t = tx.getCommand().getCommandType();
                        StatsManager m = session.getStatsManager();

                        if (SessionPoolImpl.isCutoff(m.getRecentCommandCount(t), m.getRecentCommandCount(),
                                sessionProperties.getCommandLimit(t), sessionProperties.getCommandLimit())) {
                            continue sessionSearch;
                        }
                    }

                    // Only set this if session command counts are all under
                    // cutoff values
                    bestSession = session;
                }
            }
        } else {
            if (obj instanceof CommandType) {
                type = (CommandType) obj;
            } else {
                type = null;
            }
            bestSession = availableSessionWithMinimumCommandCount(type);
        }

        if (bestSession != null) {
            try {
                bestSession.acquire();
                sessionsInUse++;
            } catch (TimeoutException te) {
                userLogger.info(ErrorPkg.getMessage("epp.session.pool.acquire.timeout"));
                bestSession = null;
            }
        }

        debugLogger.finest("exit");
        return bestSession;
    }

    private Session availableSessionWithMinimumCommandCount(CommandType type) {
        Session bestSession = null;

        int min = Integer.MAX_VALUE;
        final int totCutoff = sessionProperties.getCommandLimit();
        final int cmdCutoff = (type == null ? totCutoff : sessionProperties.getCommandLimit(type));

        boolean anySessionAvailable = false;
        for (Session session : pool) {
            if (session.isAvailable()) {
                anySessionAvailable = true;
                // total command count
                int tc = session.getStatsManager().getRecentCommandCount();
                // command-specific command count
                int cc = (type == null ? tc : session.getStatsManager().getRecentCommandCount(type));

                if (cc < cmdCutoff && cc < min && tc < totCutoff) {
                    // bookmark the minimum count and use it to compare with the next candidate
                    min = cc;
                    bestSession = session;
                    if (debugLogger.isLoggable(Level.FINE)) {
                        debugLogger.fine(ErrorPkg.getMessage("epp.session.rate.limit.notexceeded",
                                CMD_COUNT_LIMIT, new String[] {type == null ? "all commands" : type.toString(),
                                        String.valueOf(cc), String.valueOf(cmdCutoff) }));
                    }
                }
            }
        }

        // If there is any session available but still cannot find a best session,
        // it implies all the available sessions exceeded the command rate limit already.
        if (anySessionAvailable && bestSession == null) {
            userLogger.info(ErrorPkg.getMessage("epp.session.rate.limit.exceeded", CMD_LIMIT_PAIR,
                    new String[] {type.toString(), String.valueOf(cmdCutoff) }));
        }
        return bestSession;
    }

    private void openSession() throws SessionConfigurationException, SessionOpenException {

        Session newSession = SessionFactory.newInstance(sessionProperties);
        newSession.open();
        synchronized (this) {
            lastSession = newSession;
            pool.add(newSession);
            notify();
        }
    }

    private synchronized void waitForRelease() {
        if (sessionsInUse < pool.size()) {
            // There are only inappropriate sessions available.
            try {
                wait(limitExceededWaitTimeout);
            } catch (InterruptedException ie) {
                userLogger.severe("Interrupt in wait: rate limit reset wait");
            }
        }

        while (sessionsInUse >= pool.size() && pool.size() >= maximumSize) {
            try {
                wait(waitTimeout);
            } catch (InterruptedException ie) {
                userLogger.severe("Interrupted waiting for session release");
            }
        }
    }

    @Override
    public void releaseSession(Session session) {
        if (session == null) {
            return;
        }

        if (session.isInvalid()) {
            session.close();
            session.release();
            synchronized (this) {
                pool.remove(session);
                sessionsInUse--;
                debugLogger.fine(ErrorPkg.getMessage("epp.session.pool.release.notify.invalid", "<<thread>>",
                        String.valueOf(Thread.currentThread().getId())));
                notify();
            }
        } else if (!session.isOpen()) {
            session.release();
            synchronized (this) {
                pool.remove(session);
                sessionsInUse--;
                debugLogger.fine(ErrorPkg.getMessage("epp.session.pool.release.notify.closed", "<<thread>>",
                        String.valueOf(Thread.currentThread().getId())));
                notify();
            }
        } else {
            session.release();
            synchronized (this) {
                sessionsInUse--;
                debugLogger.fine(ErrorPkg.getMessage("epp.session.pool.release.notify.normal", "<<thread>>",
                        String.valueOf(Thread.currentThread().getId())));
                notify();
            }
        }
    }

    @Override
    public StatsViewer getStatsViewer() {
        return this;
    }

    @Override
    public int getRecentCommandCount() {
        return getRecentCommandCount(null);
    }

    @Override
    public long getCommandCount() {
        return getCommandCount(null);
    }

    @Override
    public int getRecentCommandCount(CommandType type) {
        int retval = 0;

        for (Session s : pool) {
            if (s == null) {
                continue;
            }
            StatsViewer v = s.getStatsManager();
            if (type == null) {
                retval += v.getRecentCommandCount();
            } else {
                retval += v.getRecentCommandCount(type);
            }
        }

        return retval;
    }

    @Override
    public long getCommandCount(CommandType type) {
        long retval = 0L;

        for (Session s : pool) {
            if (s == null) {
                continue;
            }
            StatsViewer v = s.getStatsManager();
            if (type == null) {
                retval += v.getCommandCount();
            } else {
                retval += v.getCommandCount(type);
            }
        }

        return retval;
    }

    @Override
    public long getAverageResponseTime(CommandType type) {
        long t = 0L;
        long c = 0L;

        for (Session s : pool) {
            if (s == null) {
                continue;
            }

            StatsViewer v = s.getStatsManager();
            if (type == null) {
                c += v.getCommandCount();
                t += v.getCommandCount() * v.getAverageResponseTime();
            } else {
                c += v.getCommandCount(type);
                t += v.getCommandCount(type) * v.getAverageResponseTime(type);
            }
        }

        return t / c;
    }

    @Override
    public long getAverageResponseTime() {
        return getAverageResponseTime(null);
    }

    @Override
    public long getMruInterval() {
        long minInterval = Long.MAX_VALUE;

        for (Session s : pool) {
            if (s == null) {
                continue;
            }
            long mruInterval = s.getStatsManager().getMruInterval();
            minInterval = mruInterval < minInterval ? mruInterval : minInterval;
        }

        return minInterval;
    }

    @Override
    public long getResultCodeCount(int resultCode) {
        long retval = 0L;

        for (Session s : pool) {
            if (s == null) {
                continue;
            }
            retval += s.getStatsManager().getResultCodeCount(resultCode);
        }

        return retval;
    }

    private static boolean isCutoff(int ccount, int tcount, int clim, int tlim) {
        return ccount >= clim || tcount >= tlim;
    }
}
