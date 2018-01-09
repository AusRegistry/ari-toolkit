package com.ausregistry.jtoolkit2.session;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

import com.ausregistry.jtoolkit2.se.CommandType;

/**
 * A SessionManager is configured based on a SessionManagerProperties instance. This class loads properties from a
 * properties file and presents a guaranteed minimum set of properties to be available to the SessionManager and its
 * SessionPool.
 */
public class SessionManagerPropertiesImpl implements SessionManagerProperties, SessionPoolProperties,
        SessionProperties {

    private static final String DEFAULT_PROPS_FILE = "toolkit.properties";
    private final java.util.Properties properties = new java.util.Properties();

    /**
     * Instantiate a SessionManagerProperties implementation using the default properties file location.
     */
    public SessionManagerPropertiesImpl() throws IOException, FileNotFoundException {
        this(DEFAULT_PROPS_FILE);
    }

    /**
     * Create a SessionManagerPropertiesImpl using the specified properties file.
     */
    public SessionManagerPropertiesImpl(String propertiesFile) throws IOException, FileNotFoundException {

        InputStream inputStream = null;

        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);
            if (inputStream == null) {
                throw new FileNotFoundException(propertiesFile);
            }

            properties.load(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Override
    public SessionPoolProperties getSessionPoolProperties() {
        return this;
    }

    @Override
    public SessionProperties getSessionProperties() {
        return this;
    }

    // There is no default EPP server host.
    @Override
    public String getHostname() {
        return getStringProperty("epp.server.hostname");
    }

    // The standard EPP service port number is 700.
    @Override
    public int getPort() {
        return getIntProperty("epp.server.port", 700);
    }

    // The EPP client identifier property has no default value.
    @Override
    public String getClientID() {
        return getStringProperty("epp.client.clID");
    }

    // The EPP client password property has no default value.
    @Override
    public String getClientPW() {
        return getStringProperty("epp.client.password");
    }

    // The default EPP version is '1.0'.
    @Override
    public String getVersion() {
        return getStringProperty("epp.client.options.version", "1.0");
    }

    // The default EPP service element message language is English ('en').
    @Override
    public String getLanguage() {
        return getStringProperty("epp.client.options.lang", "en");
    }

    @Override
    public String[] getObjURIs() {
        return getStringProperties("xml.uri.obj");
    }

    @Override
    public String[] getExtURIs() {
        return getStringProperties("xml.uri.ext");
    }

    @Override
    public String getKeyStoreFilename() {
        return getStringProperty("ssl.keystore.location", "keystore.jks");
    }

    @Override
    public String getKeyStorePassphrase() {
        return getStringProperty("ssl.keystore.pass");
    }

    @Override
    public String getKeyStoreType() {
        return getStringProperty("ssl.keystore.type", "jks");
    }

    @Override
    public String getTrustStoreFilename() {
        return getStringProperty("ssl.truststore.location", "AR-ca.jks");
    }

    @Override
    public String getTrustStorePassphrase() {
        return getStringProperty("ssl.truststore.pass", "password");
    }

    @Override
    public String getSSLVersion() {
        return getStringProperty("ssl.protocol", "TLSv1");
    }

    @Override
    public String getSSLAlgorithm() {
        return getStringProperty("ssl.keymanager.algorithm");
    }

    // Defaults to the maximum value of an Integer.
    @Override
    public int getCommandLimit() {
        return getIntProperty("epp.server.command.limit", Integer.MAX_VALUE);
    }

    // Defaults to the maximum value of an Integer.
    @Override
    public int getCommandLimit(CommandType type) {
        String propName = "epp.server.command.limit." + type.getCommandName();
        return getIntProperty(propName, Integer.MAX_VALUE);
    }

    // Defaults to 1 second (1000 milliseconds).
    @Override
    public long getCommandLimitInterval() {
        return getLongProperty("epp.server.command.limit.interval", 1000L);
    }

    // Defaults to 5 sessions.
    @Override
    public int getMaximumPoolSize() {
        return getIntProperty("epp.client.session.count.max", 5);
    }

    // Defaults to 2 minutes
    @Override
    public long getWaitTimeout() {
        return getLongProperty("thread.wait.timeout", 120000);
    }

    // Defaults to 10 minutes (600000 milliseconds)
    @Override
    public long getServerTimeout() {
        return getLongProperty("net.server.timeout", 600000);
    }

    // Defaults to 20 minutes (12 million milliseconds)
    @Override
    public long getClientTimeout() {
        return getLongProperty("net.client.timeout", 12000000);
    }

    // Defaults to 20 seconds (20000 ms).
    @Override
    public long getAcquireTimeout() {
        return getLongProperty("session.acquire.timeout", 20000);
    }

    // Defaults to 12 seconds (12000 ms).
    @Override
    public int getSocketTimeout() {
        return getIntProperty("net.socket.timeout", 12000);
    }

    // Defaults to true.
    @Override
    public boolean enforceStrictValidation() {
        return getBooleanProperty("xml.validation.enable", true);
    }

    // Defaults to false, so XML output will not have namespace prefixes
    @Override
    public boolean needOutputNamespacePrefixInXml() {
        return getBooleanProperty("xml.output.namespace.prefixes", false);
    }

    @Override
    public void setClientPW(String password) {
        properties.setProperty("epp.client.password", password);
    }

    @Override
    public void setClientID(String id) {
        properties.setProperty("epp.client.clID", id);
    }

    private String[] getStringProperties(String start) {
        Vector<String> result = new Vector<String>(5);

        Enumeration<?> e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String prop = (String) e.nextElement();
            if (prop.startsWith(start)) {
                result.addElement(getStringProperty(prop));
            }
        }

        String[] retval = result.toArray(new String[result.size()]);

        return retval;
    }

    private String getStringProperty(String name) {
        return properties.getProperty(name);
    }

    private String getStringProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    private boolean getBooleanProperty(String name, boolean defaultValue) {
        String value = properties.getProperty(name, String.valueOf(defaultValue));
        return Boolean.valueOf(value);
    }

    private int getIntProperty(String name, int defaultValue) {
        String value = properties.getProperty(name, String.valueOf(defaultValue));
        return Integer.parseInt(value);
    }

    private long getLongProperty(String name, long defaultValue) {
        String value = properties.getProperty(name, String.valueOf(defaultValue));
        return Long.parseLong(value);
    }
}
