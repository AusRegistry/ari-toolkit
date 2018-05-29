package com.ausregistry.jtoolkit2.session;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;

import com.ausregistry.jtoolkit2.xml.XmlOutputConfig;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandType;
import com.ausregistry.jtoolkit2.se.Greeting;
import com.ausregistry.jtoolkit2.se.LoginCommand;
import com.ausregistry.jtoolkit2.se.LogoutCommand;
import com.ausregistry.jtoolkit2.se.PollRequestCommand;
import com.ausregistry.jtoolkit2.se.Response;
import com.ausregistry.jtoolkit2.se.Result;
import com.ausregistry.jtoolkit2.se.ResultCode;
import com.ausregistry.jtoolkit2.xml.ParsingException;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;

/**
 * <p>
 * RFC5734 specifies a transport mapping for EPP over TCP; this class implements that mapping. That specification
 * requires that the session is layered over TLS (Transport Layer Security). This class complies with RFC5734 in
 * implementing the Session interface. It also implements its own StatsManager since it is tightly coupled with the
 * Session implementation.
 * </p>
 *
 * <p>
 * Uses the debug, support and user level loggers.
 * </p>
 */
public class TLSSession implements Session, StatsManager {
    private static final String[] TYPE_INTERVAL_ARR = new String[] {"<<type>>", "<<interval>>" };
    private static final String[] TIME_COUNT_ARR = new String[] {"<<time>>", "<<count>>" };

    private static final int BUF_SIZE = 4096;
    private static String pollXML;
    private static CommandType pollCmdType;

    static {
        PollRequestCommand poll = new PollRequestCommand();
        try {
            pollXML = poll.toXML();
        } catch (SAXException saxe) {
        }
        pollCmdType = poll.getCommandType();
    }

    private java.io.DataInputStream in;
    private java.io.DataOutputStream out;
    private TLSContext ctx;
    private XMLParser parser;
    private SSLSocket socket;

    private boolean inUse;
    private boolean isOpen;
    private boolean isInvalid;
    private CommandCounter commandCounter;
    private ResultCounter resultCounter;
    private long totalTime;
    private Map<CommandType, Long> commandTimeMap;
    private long mruTime;
    private long acquireTimeout;

    private InetAddress inaddr;
    private int port;
    private int soTimeout;
    private String username;
    private String password;
    private String newPW;
    private String eppVersion;
    private String language;
    private String[] objURIs, extURIs;
    private Greeting greeting;

    private String pname;

    private Logger debugLogger;
    private Logger supportLogger;
    private Logger userLogger;
    private boolean needOutputNamespacePrefixInXml;

    {
        pname = TLSSession.class.getPackage().getName();
        debugLogger = Logger.getLogger(pname + ".debug");
        supportLogger = Logger.getLogger(pname + ".support");
        userLogger = Logger.getLogger(pname + ".user");
        parser = new XMLParser();
        resultCounter = new ResultCounter();
        commandTimeMap = new HashMap<CommandType, Long>();
        totalTime = 0L;
        isInvalid = true;
        greeting = null;
    }

    protected TLSSession() {
    }

    protected TLSSession(SessionProperties props) throws SessionConfigurationException {

        configure(props);
    }

    /**
     * Configure the session as described in the Session interface.
     *
     * @throws SessionConfigurationException
     *             Possible causes:
     *             <ul>
     *             <li>KeyStoreNotFoundException</li>
     *             <li>KeyStoreTypeException</li>
     *             <li>NoSuchAlgorithmException</li>
     *             <li>UnrecoverableKeyException</li>
     *             <li>CertificateException</li>
     *             <li>KeyStoreReadException</li>
     *             <li>UnknownHostException</li>
     *             <li>FileNotFoundException</li>
     *             </ul>
     */
    @Override
    public void configure(SessionProperties properties) throws SessionConfigurationException {

        this.port = properties.getPort();
        this.username = properties.getClientID();
        this.password = properties.getClientPW();
        this.newPW = null;
        this.eppVersion = properties.getVersion();
        this.language = properties.getLanguage();
        this.objURIs = properties.getObjURIs();
        this.extURIs = properties.getExtURIs();
        commandCounter = new CommandCounter(properties.getCommandLimitInterval());
        this.acquireTimeout = properties.getAcquireTimeout();
        this.soTimeout = properties.getSocketTimeout();
        this.needOutputNamespacePrefixInXml = properties.needOutputNamespacePrefixInXml();

        try {
            inaddr = InetAddress.getByName(properties.getHostname());

            if (ctx == null) {
                ctx = new TLSContext(properties.getKeyStoreFilename(), properties.getKeyStorePassphrase(),
                        properties.getTrustStoreFilename(), properties.getTrustStorePassphrase(),
                        properties.getKeyStoreType(), properties.getSSLAlgorithm(), properties.getSSLVersion());
            }
        } catch (Exception e) {
            throw new SessionConfigurationException(e);
        }
    }

    @Override
    public boolean isInvalid() {
        return isInvalid;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * * An EPP session is opened by first establishing a connection using the server location information and
     * authentication sources provided in <code>configure</code>, then issuing a login command with further
     * authentication data and options provided in <code>configure</code>. Service information provided immediately upon
     * connection establishment may affect options provided in the login command.
     *
     * @throws SessionOpenException
     *             The getCause() method should be invoked on the exception thrown. The cause may be one of:
     *             <dl>
     *             <dt>SSLHandshakeException</dt>
     *             <dd>The SSL handshake failed. The reason is described in the exception message, and is also recorded
     *             in the user logs.</dd>
     *             <dt>IOException</dt>
     *             <dd>See the log record published to the user logs, or check the exception message.</dd>
     *             <dt>GreetingException</dt>
     *             <dd>The service element received from the server upon connection establishment was not a valid EPP
     *             greeting.</dd>
     *             <dt>LoginException</dt>
     *             <dd>The login command failed to establish an EPP session. The cause, available via getCause(),
     *             describes the specific reason for failure.</dd>
     *             </dl>
     */
    @Override
    public void open() throws SessionOpenException {
        isInvalid = true;
        try {
            openSocket();
            processGreeting();
            login();
            isOpen = true;
            isInvalid = false;
        } catch (SSLHandshakeException e) {
            userLogger.severe(ErrorPkg.getMessage("TLSContext.createSocket.0", "<<java.home>>",
                    System.getProperty("java.home", "java.home")));
            userLogger.severe(e.getMessage());
            throw new SessionOpenException(e);
        } catch (ConnectException e) {
            final String errorMessage = ErrorPkg.getMessage("net.socket.open.fail", new String[] {"<<port>>",
                    "<<host>>" }, new String[] {String.valueOf(port), inaddr.getHostAddress() });
            userLogger.severe(errorMessage);
            userLogger.severe(e.getMessage());
            throw new SessionOpenException(errorMessage);
        } catch (IOException e) {
            userLogger.severe(ErrorPkg.getMessage("net.socket.open.fail", new String[] {"<<port>>", "<<host>>" },
                    new String[] {String.valueOf(port), inaddr.getHostAddress() }));
            userLogger.severe(e.getMessage());
            throw new SessionOpenException(ErrorPkg.getMessage("net.socket.open.fail", new String[] {"<<port>>",
                    "<<host>>" }, new String[] {String.valueOf(port), inaddr.getHostAddress() }));
        } catch (SessionLimitExceededException e) {
            e.printStackTrace();
            throw new SessionOpenException(e);
        } catch (GreetingException e) {
            e.printStackTrace();
            throw new SessionOpenException(e);
        } catch (LoginException e) {
            e.printStackTrace();
            throw new SessionOpenException(e);
        }
    }

    @Override
    public void changePassword(String newPassword) throws SessionOpenException {
        newPW = newPassword;
        open();
        close();
    }

    private void openSocket() throws SSLHandshakeException, IOException {
        socket = ctx.createSocket(inaddr.getHostAddress(), port, soTimeout);
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), BUF_SIZE));
    }

    @Override
    public Greeting getGreeting() {
        return greeting;
    }

    private void processGreeting() throws SessionLimitExceededException, GreetingException {

        try {
            if (greeting == null) {
                greeting = new Greeting();
            }

            greeting.fromXML(readToDocument());
        } catch (IOException pe) {
            throw new SessionLimitExceededException();
        } catch (ParsingException pe) {
            pe.printStackTrace();
            throw new GreetingException(pe);
        }
    }

    private void login() throws LoginException, IOException {
        LoginCommand login = new LoginCommand(username, password, newPW, eppVersion, language, objURIs, extURIs);
        CLTRID.setClID(username);
        long startTime = Timer.now();
        try {
            write(login.toXML(), login.getCommandType());
        } catch (SAXException saxe) {
            throw new LoginException(saxe);
        }
        mruTime = Timer.now();
        Response response = new Response();
        try {
            response.fromXML(readToDocument());
            long responseTime = Timer.msDiff(startTime);
            recordResponseTime(login.getCommandType(), responseTime);
        } catch (NumberFormatException nfe) {
            // These two exceptions usually indicate a fatal error.
            throw new LoginException(nfe);
        } catch (ParsingException pe) {
            throw new LoginException(pe);
        }

        Result result = response.getResults()[0];
        assert result != null;
        String msg = result.getResultMessage();
        switch (result.getResultCode()) {
        case ResultCode.SUCCESS:
            return;
        case ResultCode.CMD_USE_ERR:
            userLogger.warning(ErrorPkg.getMessage("epp.login.fail.loggedin"));
            throw new LoginException(msg);
        case ResultCode.AUTHENT_ERROR_CLOSING:
        case ResultCode.AUTHENT_ERR:
            String cn = ctx.getCertificateCommonName();

            if (cn.equals(username)) {
                userLogger.severe(ErrorPkg.getMessage("epp.login.fail.auth.pw",
                        new String[] {"<<clID>>", "<<pw>>" }, new String[] {username, password }));
                throw new UserPassMismatchException(msg);
            } else {
                userLogger.severe(ErrorPkg.getMessage("epp.login.fail.auth.match", new String[] {"<<clID>>",
                        "<<cn>>" }, new String[] {username, cn }));
                throw new CertificateUserMismatchException(username, cn);
            }
        case ResultCode.UNIMPL_OBJ_SVC:
            if (result.getResultValue() != null) {
                msg = result.getResultValue().item(0).getTextContent();
            }

            userLogger.severe(ErrorPkg.getMessage("epp.login.fail.unimpl.objsvc", "<<uri>>", msg));
            throw new LoginException(msg);
        case ResultCode.PARAM_VAL_SYNTAX_ERR:
            throw new LoginException(new ParameterSyntaxException(result.getResultMessage()));
        case ResultCode.SESS_LIM_EXCEEDED_CLOSING:
            throw new SessionLimitExceededException();
        case ResultCode.CMD_FAILED:
            raiseLoginException(result.hasResultExtReasons(), result.getResultExtReason(0));
        case ResultCode.CMD_FAILED_CLOSING:
            raiseLoginException(result.hasResultExtReasons(), result.getResultExtReason(0));
        default: // do nothing
        }
    }

    private void raiseLoginException(boolean hasValues, String msg) throws LoginException {

        if (hasValues) {
            throw new LoginException(new CommandFailedException(msg));
        } else {
            throw new LoginException(new CommandFailedException());
        }
    }

    @Override
    public void close() {
        inUse = true;
        isOpen = false;
        try {
            logout();
        } catch (LogoutException le) {
            debugLogger.info(le.getMessage());
            debugLogger.info(ErrorPkg.getMessage("net.event.socket_closed"));
        }
        closeSocket();
        inUse = false;
    }

    private void logout() throws LogoutException {
        LogoutCommand logout = new LogoutCommand();
        Response response = new Response();

        try {
            long startTime = Timer.now();

            try {
                write(logout.toXML(), logout.getCommandType());
            } catch (SAXException saxe) {
                debugLogger.warning(saxe.getMessage());
                throw new LogoutException(saxe);
            }
            response.fromXML(readToDocument());
            long responseTime = Timer.msDiff(startTime);
            recordResponseTime(logout.getCommandType(), responseTime);

            Result[] results = response.getResults();
            if (ResultCode.CMD_FAILED == results[0].getResultCode()) {
                throw new LogoutException(new CommandFailedException(results[0].getResultMessage()));
            }
        } catch (IOException ioe) {
            throw new LogoutException(ioe);
        } catch (ParsingException pe) {
            throw new LogoutException(pe);
        }
    }

    private void closeSocket() {
        try {
            in.close();
        } catch (IOException ioe) {
            userLogger.warning(ioe.getMessage());
        }

        try {
            out.close();
        } catch (IOException ioe) {
            userLogger.warning(ioe.getMessage());
        }

        try {
            socket.close();
        } catch (IOException ioe) {
            userLogger.warning(ioe.getMessage());
        }
    }

    /**
     * Receive data from the peer. This method is unsynchronised; the caller MUST provide synchronisation against other
     * calls to read.
     *
     * @return the details from the socket
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public String read() throws IOException {
        try {
            int n = readSize();
            debugLogger.finer("PDU size: " + n);
            String data = readData(n);
            supportLogger.info(data);
            return data;
        } catch (SocketTimeoutException ste) {
            userLogger.warning(ste.getMessage());
            userLogger.warning(ErrorPkg.getMessage("epp.session.read.timeout"));
            throw ste;
        } catch (IOException ioe) {
            userLogger.warning(ioe.getMessage());
            throw ioe;
        }
    }

    @Override
    public void read(Response response) throws IOException, ParsingException {
        response.fromXML(readToDocument());
    }

    @Override
    public XMLDocument readToDocument() throws IOException, ParsingException {
        String xml = read();
        assert parser != null;
        return parser.parse(xml);
    }

    /**
     * Send data to peer. This method is unsynchronised; the caller MUST provide synchronisation against other calls to
     * <code>write(String)</code>.
     *
     * @param xml
     *            the XML to be sent to the EPP Server
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Override
    public void write(String xml) throws IOException {
        doWrite(xml);
        mruTime = Timer.now();
    }

    @Override
    public void write(Command command) throws IOException, ParsingException {
        try {
            if (needOutputNamespacePrefixInXml) {
                write(command.toXML(XmlOutputConfig.prefixAllNamespaceConfig()));
            } else {
                write(command.toXML());
            }
        } catch (SAXException saxe) {
            throw new ParsingException(saxe);
        }
    }

    private void doWrite(String xml) throws IOException {
        if (out == null) {
            throw new UninitialisedSessionException();
        }

        try {
            final byte[] xmlBytes = xml.getBytes();
            writeSize(xmlBytes.length);
            writeData(xmlBytes);
        } catch (IOException ioe) {
            isInvalid = true;
            throw ioe;
        }
    }

    private void write(String xml, CommandType cmdType) throws IOException {
        debugLogger.finer("writing command " + cmdType.toString());
        doWrite(xml);
        incCommandCounter(cmdType);
    }

    /**
     * Send a poll command to the EPP server in order to prevent the session timing out. This operation does not affect
     * the most-recently-used statistic.
     */
    @Override
    public void keepAlive() throws IOException {
        try {
            acquire();
            write(pollXML, pollCmdType);
            read(); // not interested in response.
            release();
        } catch (TimeoutException te) {
            userLogger.info(te.getMessage());
        } catch (InterruptedException ie) {
            userLogger.info(ie.getMessage());
        }
    }

    private int readSize() throws IOException {
        return in.readInt() - 4;
    }

    private void writeSize(int size) throws IOException {
        out.writeInt(size + 4);
    }

    private String readData(int length) throws IOException {
        byte[] inputBuffer = new byte[length];
        in.readFully(inputBuffer, 0, length);

        return new String(inputBuffer);
    }

    private void writeData(final byte[] xml) throws IOException {
        out.write(xml);
        out.flush();
    }

    @Override
    public void incCommandCounter(CommandType type) {
        commandCounter.increment(type);
    }

    @Override
    public void incResultCounter(int resultCode) {
        resultCounter.increment(resultCode);
    }

    @Override
    public void recordResponseTime(CommandType type, long responseTime) {
        totalTime += responseTime;
        if (!commandTimeMap.containsKey(type)) {
            commandTimeMap.put(type, responseTime);
            debugLogger.info(ErrorPkg.getMessage("epp.server.response_time.new_cmd", TYPE_INTERVAL_ARR, new String[] {
                    type.getCommandName(), String.valueOf(responseTime) }));
        } else {
            commandTimeMap.put(type, commandTimeMap.get(type) + responseTime);
            debugLogger.info(ErrorPkg.getMessage("epp.server.response_time.previous_cmd", TYPE_INTERVAL_ARR,
                    new String[] {type.getCommandName(), String.valueOf(responseTime) }));
        }
    }

    @Override
    public long getAverageResponseTime() {
        long totalCount = commandCounter.getTotal();
        if (totalCount == 0L) {
            return 0L;
        }
        debugLogger.info(ErrorPkg.getMessage("epp.server.response_time.avg", TIME_COUNT_ARR,
                new String[] {String.valueOf(totalTime), String.valueOf(totalCount) }));

        return totalTime / totalCount;
    }

    @Override
    public long getAverageResponseTime(CommandType type) {
        if (!commandTimeMap.containsKey(type)) {
            return 0L;
        }

        long cmdCount = commandCounter.getCount(type);

        if (cmdCount == 0L) {
            return 0L;
        }

        return commandTimeMap.get(type) / cmdCount;
    }

    @Override
    public long getCommandCount() {
        return commandCounter.getTotal();
    }

    @Override
    public long getCommandCount(CommandType type) {
        return commandCounter.getCount(type);
    }

    @Override
    public int getRecentCommandCount() {
        return commandCounter.getExactRecentTotal();
    }

    @Override
    public int getRecentCommandCount(CommandType type) {
        return commandCounter.getRecentCount(type);
    }

    @Override
    public long getResultCodeCount(int resultCode) {
        return resultCounter.getValue(resultCode);
    }

    /**
     * Get the length of time (in milliseconds) since the most recent use (mru) of the session. The session is
     * considered to be used when the write method is invoked.
     */
    @Override
    public long getMruInterval() {
        return Timer.msDiff(mruTime);
    }

    @Override
    public StatsManager getStatsManager() {
        return this;
    }

    @Override
    public boolean isAvailable() {
        return (isOpen && !inUse);
    }

    @Override
    public void acquire() throws InterruptedException, TimeoutException {
        synchronized (this) {
            while (inUse) {
                wait(acquireTimeout);
                if (inUse) {
                    throw new TimeoutException(ErrorPkg.getMessage("epp.session.acquire.timeout", "<<timeout>>",
                            String.valueOf(acquireTimeout)));
                }
            }
            inUse = true;
        }
    }

    @Override
    public void release() {
        synchronized (this) {
            inUse = false;
            notify();
        }
    }
}
