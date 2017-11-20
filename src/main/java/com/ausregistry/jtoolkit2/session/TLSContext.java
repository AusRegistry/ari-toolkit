package com.ausregistry.jtoolkit2.session;

import javax.net.ssl.*;
import javax.security.auth.x500.X500Principal;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.util.Enumeration;
import java.util.logging.Logger;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * <p>
 * This defines the operations or actions for establishing a TLS Version 1 based context.
 * </p>
 *
 * <p>
 * Uses the maintenance, debug, support and user level loggers.
 * </p>
 */
public class TLSContext {
    private static final String TLSV1 = "TLSv1";

    private static final String TMF_ALGORITHM = TrustManagerFactory.getDefaultAlgorithm();

    private SSLContext ctx;
    private String commonName;

    private final Logger userLogger;
    private final Logger supportLogger;
    private final Logger maintLogger;
    private final Logger debugLogger;

    /**
     * Instantiates a new TLS context.
     *
     * @param keystore
     *            the filename of the key store to be used for the context
     * @param keypass
     *            the password applied to the key store
     * @param truststore
     *            the filename of the trust store that is to be used for the key store
     * @param trustpass
     *            the password used in the trust store
     * @param type
     *            the key store type
     * @param algorithm
     *            the algorithm
     * @throws KeyStoreNotFoundException
     *             the key store not found exception
     * @throws KeyStoreReadException
     *             the key store read exception
     * @throws KeyStoreException
     *             the key store exception
     * @throws CertificateException
     *             the certificate exception
     * @throws UnrecoverableKeyException
     *             the unrecoverable key exception
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws KeyManagementException
     *             the key management exception
     */
    public TLSContext(String keystore, String keypass, String truststore, String trustpass, String type,
            String algorithm, String protocol) throws KeyStoreNotFoundException, KeyStoreReadException,
            KeyStoreException, CertificateException,
            UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

        String pname = getClass().getPackage().getName();
        userLogger = Logger.getLogger(pname + ".user");
        supportLogger = Logger.getLogger(pname + ".support");
        maintLogger = Logger.getLogger(pname + ".maint");
        debugLogger = Logger.getLogger(pname + ".debug");

        try {
            TrustManager[] trustManagers = loadTrustManagers(truststore, trustpass);
            KeyManager[] keyManagers = null;
            if (keystore != null) {
                keyManagers = loadKeyManagers(keystore, keypass, type, algorithm);
            }

            if (protocol != null) {
                ctx = SSLContext.getInstance(protocol);
            } else {
                ctx = SSLContext.getInstance(TLSV1);
            }
            ctx.init(keyManagers, trustManagers, null);
        } catch (UnrecoverableKeyException uke) {
            // the given passphrase is incorrect.
            userLogger.severe(uke.getMessage());
            userLogger.severe(ErrorPkg.getMessage("ssl.keystore.unrecoverable_key"));
            // a key in the keystore couldn't be recovered -> useless keystore
            throw uke;
        } catch (KeyStoreException kse) {
            userLogger.severe(kse.getMessage());
            userLogger.severe(ErrorPkg.getMessage("ssl.keystore.initfail"));
            throw kse;
        } catch (NoSuchAlgorithmException nsae) {
            // should not be reachable, unless provider libraries were lost
            // after call to loadKeystore().
            userLogger.severe(nsae.getMessage());
            userLogger.severe(ErrorPkg.getMessage("ssl.context.nsae"));
            throw nsae;
        } catch (KeyManagementException kme) {
            userLogger.severe(kme.getMessage());
            userLogger.severe(ErrorPkg.getMessage("ssl.context.kme"));
            throw kme;
        }
    }

    /**
     * Instantiates a new TLS context.
     *
     * @param truststore
     *            the filename of the trust store that is to be used for the key store
     * @param trustpass
     *            the password used in the trust store
     *
     * @throws KeyStoreNotFoundException
     *             the key store not found exception
     * @throws KeyStoreReadException
     *             the key store read exception
     * @throws KeyStoreException
     *             the key store exception
     * @throws CertificateException
     *             the certificate exception
     * @throws UnrecoverableKeyException
     *             the unrecoverable key exception
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws KeyManagementException
     *             the key management exception
     */
    public TLSContext(String truststore, String trustpass) throws CertificateException,
                                                                  UnrecoverableKeyException,
                                                                  NoSuchAlgorithmException,
                                                                  KeyStoreException,
                                                                  KeyManagementException,
                                                                  KeyStoreReadException,
                                                                  KeyStoreNotFoundException {
        this(null, null, truststore, trustpass, null, null, TLSV1);
    }


    public String getCertificateCommonName() {
        return commonName;
    }

    /**
     * Creates an SSL Socket to be used in a session.
     *
     * @param host
     *            the host
     * @param port
     *            the port
     * @param soTimeout
     *            the socket timeout
     * @return the SSL enabled socket
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws SSLHandshakeException
     *             the SSL handshake exception
     */
    public SSLSocket createSocket(String host, int port, int soTimeout) throws IOException, SSLHandshakeException {

        SSLSocket socket = null;
        Socket tcpSocket = new Socket();
        boolean autoCloseTcpSocket = true;

        tcpSocket.connect(new InetSocketAddress(host, port), soTimeout);

        try {
            socket = (SSLSocket) ctx.getSocketFactory().createSocket(tcpSocket, host, port, autoCloseTcpSocket);
        } catch (IOException ioe) {
            userLogger.severe(ErrorPkg.getMessage("net.socket.open.fail", new String[] {"<<port>>", "<<host>>" },
                    new String[] {String.valueOf(port), host }));
            userLogger.severe(ioe.getMessage());
            throw ioe;
        }
        socket.setSoTimeout(soTimeout);
        try {
            socket.startHandshake();
        } catch (SSLHandshakeException sslhe) {
            userLogger.severe(sslhe.getMessage());
            String msg = ErrorPkg.getMessage("TLSContext.createSocket.0", "<<java.home>>",
                    System.getProperty("java.home", "java.home"));
            userLogger.severe(msg);
            throw sslhe;
        } catch (SocketException se) {
            userLogger.severe(se.getMessage());
            userLogger.severe(ErrorPkg.getMessage("net.socket.open.fail", new String[] {"<<port>>", "<<host>>" },
                    new String[] {String.valueOf(port), host }));
            throw se;
        }

        socket.getSession().invalidate();

        return socket;
    }

    /**
     * Creates a Https connection to the specified URL connection.
     *
     * @param url the URL to establish a HTTPS connection to
     * @param connectionTimeout connection timeout value to be used in milliseconds
     * @return HTTPS URL connection
     * @throws IOException in case of a malformed URL or an IO exception occurring while trying to open a connection
     */
    public HttpsURLConnection createHttpsUrlConnection(String url, Integer connectionTimeout) throws IOException {
        HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        if (hostname.equals("localhost")) {
                            return true;
                        }
                        return false;
                    }
                });
        HttpsURLConnection urlConnection = (HttpsURLConnection) (new URL(url)).openConnection();
        urlConnection.setSSLSocketFactory(ctx.getSocketFactory());
        urlConnection.setConnectTimeout(connectionTimeout);
        return urlConnection;
    }

    private KeyManager[] loadKeyManagers(String filename, String password, String type, String algorithm)
            throws CertificateException, CertificateExpiredException, CertificateNotYetValidException,
            KeyManagementException, KeyStoreException, KeyStoreNotFoundException, KeyStoreReadException,
            NoSuchAlgorithmException, UnrecoverableKeyException {

        char[] passphrase = password.toCharArray();
        KeyStore keyStore = loadKeystore(filename, passphrase, type);
        KeyManagerFactory kmf = null;

        try {
            kmf = KeyManagerFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException nsae) {
            // the algorithm specified for the KeyManagerFactory isn't
            // available
            try {
                kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            } catch (NoSuchAlgorithmException ex) {
                // default algorithm should be available
                ex.printStackTrace();
                throw ex;
            }
        }

        kmf.init(keyStore, passphrase);

        return kmf.getKeyManagers();
    }

    private TrustManager[] loadTrustManagers(String filename, String password) {
        char[] passphrase = password.toCharArray();

        try {
            KeyStore trustStore = loadKeystore(filename, passphrase, KeyStore.getDefaultType());
            TrustManagerFactory tmf = null;
            tmf = TrustManagerFactory.getInstance(TMF_ALGORITHM);
            tmf.init(trustStore);
            return tmf.getTrustManagers();
        } catch (Exception e) {
            debugLogger.warning("Failed to load user trust store");
            return null;
        }
    }

    private KeyStore loadKeystore(String filename, char[] passphrase, String type) throws KeyStoreException,
            CertificateException, KeyStoreNotFoundException, KeyStoreReadException, CertificateExpiredException,
            CertificateNotYetValidException, NoSuchAlgorithmException {
        KeyStore store = null;

        try {
            store = KeyStore.getInstance(type);
        } catch (KeyStoreException kse) {
            if (type.equals(KeyStore.getDefaultType())) {
                kse.printStackTrace();
                throw kse;
            } else {
                try {
                    store = KeyStore.getInstance(KeyStore.getDefaultType());
                } catch (KeyStoreException ksx) {
                    ksx.printStackTrace();
                    throw ksx;
                }
            }
        }

        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream(filename);
            if (in == null) {
                throw new FileNotFoundException(filename);
            }
            store.load(in, passphrase);
        } catch (CertificateException ce) {
            throw ce;
        } catch (FileNotFoundException fnfe) {
            throw new KeyStoreNotFoundException(fnfe);
        } catch (IOException ioe) {
            throw new KeyStoreReadException(ioe);
        } catch (NullPointerException npe) {
            // fatal, keystore instance creation failure even with default type
            maintLogger.severe(npe.getMessage());
            userLogger.severe(npe.getMessage());
            userLogger.severe(ErrorPkg.getMessage("ssl.keystore.npe"));
            npe.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {
                    maintLogger.info(ioe.getMessage());
                }
            }
        }

        try {
            debugLogger.fine("Loaded KeyStore");
            Enumeration<String> aliases = store.aliases();
            while (aliases.hasMoreElements()) {
                Certificate cert = store.getCertificate(aliases.nextElement());
                if (!(cert instanceof X509Certificate)) {
                    continue;
                }

                X509Certificate x509cert = (X509Certificate) cert;
                x509cert.checkValidity();
                X500Principal subjectDN = x509cert.getSubjectX500Principal();
                String dn = subjectDN.getName(X500Principal.RFC2253);
                String[] dnParts = dn.split(",");
                for (String part : dnParts) {
                    String[] pair = part.split("=");
                    if (pair[0].equals("CN")) {
                        commonName = pair[1];
                        debugLogger.fine("Common name: " + commonName);
                    }
                }
            }
        } catch (CertificateExpiredException cee) {
            supportLogger.severe(cee.getMessage());
            userLogger.severe(cee.getMessage());
            userLogger.severe(ErrorPkg.getMessage("ssl.cert.validity.expired"));
            throw cee;
        } catch (CertificateNotYetValidException cnyve) {
            supportLogger.severe(cnyve.getMessage());
            userLogger.severe(cnyve.getMessage());
            userLogger.severe(ErrorPkg.getMessage("ssl.cert.validity.notyet"));
            throw cnyve;
        }

        return store;
    }


}
