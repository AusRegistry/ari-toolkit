package com.ausregistry.jtoolkit2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ausregistry.jtoolkit2.se.CommandType;
import com.ausregistry.jtoolkit2.se.StandardCommandType;
import com.ausregistry.jtoolkit2.session.SessionManagerProperties;
import com.ausregistry.jtoolkit2.session.SessionPoolProperties;
import com.ausregistry.jtoolkit2.session.SessionProperties;
import org.junit.Ignore;

@Ignore
public final class TestEnvironment implements SessionManagerProperties,
    SessionPoolProperties, SessionProperties {

    private static final String DEFAULT_SITE_PROPS = "site.properties";
    private static String[] objURIs = {
        "urn:ietf:params:xml:ns:domain-1.0"
    };

    private static String[] extURIs = {
        "urn:au:params:xml:ns:auext-1.0"
    };

    private String sitePropsFile;
    private Properties siteProps;
    private String password;
    private String clientId;

    public TestEnvironment() throws IOException {
        sitePropsFile = System.getProperty("site.properties.file");
        if (sitePropsFile == null) {
            sitePropsFile = DEFAULT_SITE_PROPS;
        }
        siteProps = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream(sitePropsFile);

        if (in == null) {
            throw new FileNotFoundException(sitePropsFile);
        }

        siteProps.load(in);
        password = siteProps.getProperty("epp.client.password");
        clientId = siteProps.getProperty("epp.client.clID");
    }

    public SessionPoolProperties getSessionPoolProperties() {
        return this;
    }

    public SessionProperties getSessionProperties() {
        return this;
    }

    public int getMaximumPoolSize() {
        return 1;
    }

    public long getServerTimeout() {
        return 600000L;
    }

    public long getClientTimeout() {
        return 1200000L;
    }

    public String getHostname() {
        return siteProps.getProperty("epp.server.hostname");
    }

    public int getPort() {
        return Integer.valueOf(siteProps.getProperty("epp.server.port"));
    }

    public String getClientID() {
        return clientId;
    }

    public String getClientPW() {
        return password;
    }

    public String getVersion() {
        return "1.0";
    }

    public String getLanguage() {
        return "en";
    }

    public String[] getObjURIs() {
        return objURIs;
    }

    public String[] getExtURIs() {
        return extURIs;
    }

    public String getKeyStoreFilename() {
        return siteProps.getProperty("ssl.keystore.location");
    }

    public String getKeyStoreType() {
        return siteProps.getProperty("ssl.keystore.type");
    }

    public String getKeyStorePassphrase() {
        return siteProps.getProperty("ssl.keystore.pass");
    }

    public String getTrustStoreFilename() {
        return siteProps.getProperty("ssl.truststore.location");
    }

    public String getTrustStorePassphrase() {
        return siteProps.getProperty("ssl.truststore.pass");
    }

    public String getSSLVersion() {
        return "TLSv1";
    }

    public String getSSLAlgorithm() {
        return "SunX509";
    }

    public long getWaitTimeout() {
        return 120000L;
    }

    public int getSocketTimeout() {
        return 20000;
    }

    public long getAcquireTimeout() {
        return 20000L;
    }

    public boolean needOutputNamespacePrefixInXml() {
        return false;
    }

    public int getCommandLimit(CommandType type) {
        if (!(type instanceof StandardCommandType)) {
            return 10;
        }

        StandardCommandType sct = (StandardCommandType) type;
        switch (sct) {
        case POLL:
            return 5;
        case CREATE:
            return 1;
        case TRANSFER:
            return 1;
        case UPDATE:
            return 1;
        case DELETE:
            return 1;
        case RENEW:
            return 1;
        case INFO:
            return 1;
        case CHECK:
            return 1;
        default:
            return 1;
        }
    }

    public int getCommandLimit() {
        return 1000;
    }

    public long getCommandLimitInterval() {
        return 5000L;
    }

    public String getStringProperty(String name) {
        return null;
    }

    public String getStringProperty(String name, String defaultValue) {
        return null;
    }

    public String[] getStringProperties(String start) {
        return null;
    }

    public boolean getBooleanProperty(String name) {
        return true;
    }

    public boolean getBooleanProperty(String name, boolean defaultValue) {
        return true;
    }

    public int getIntProperty(String name) {
        return 0;
    }

    public int getIntProperty(String name, int defaultValue) {
        return 0;
    }

    public long getLongProperty(String name) {
        return 0L;
    }

    public long getLongProperty(String name, long defaultValue) {
        return 0L;
    }

    public void store() throws IOException {
    }

    public void load() throws IOException, FileNotFoundException {
    }

    public SessionManagerProperties clone() {
        return this;
    }

    public void setClientPW(String pw) {
        password = pw;
    }

    public void setClientID(String id) {
        clientId = id;
    }

    public boolean enforceStrictValidation() {
        return true;
    }
}
