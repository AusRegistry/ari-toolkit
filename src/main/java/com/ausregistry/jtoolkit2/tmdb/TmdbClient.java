package com.ausregistry.jtoolkit2.tmdb;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import com.ausregistry.jtoolkit2.session.TLSContext;
import com.ausregistry.jtoolkit2.tmdb.model.TmNotice;
import com.ausregistry.jtoolkit2.tmdb.xml.TmNoticeXmlParseException;
import com.ausregistry.jtoolkit2.tmdb.xml.TmNoticeXmlParser;

/**
 * Trade Mark notice for a lookup key from the configured TMDB server can be requested using this class.
 * The properties specified in 'tmdb.properties' file are used to establish a connection with the TMDB server.
 * The connection is opened for each request and is closed after the request is processed.
 *
 * TradeMark notice response is encapsulated in a {@link TmNotice} object.
 */
public class TmdbClient {

    private final TmdbClientProperties tmdbClientProperties;
    private final TmNoticeXmlParser tmNoticeXmlParser = new TmNoticeXmlParser();
    private TLSContext tlsContext;

    public TmdbClient() throws IOException,
                               UnrecoverableKeyException,
                               CertificateException,
                               NoSuchAlgorithmException,
                               KeyStoreException,
                               KeyManagementException {
        tmdbClientProperties = new TmdbClientProperties("tmdb.properties");
        tlsContext = new TLSContext(tmdbClientProperties.getTrustStoreFilename(),
                                    tmdbClientProperties.getTrustStorePassphrase());
    }

    /**
     * Request for a TradeMark notice using a lookup key.
     *
     * @param lookupKey the lookup key to be used in the request for TradeMark notice.
     * @return the TradeMark notice
     * @throws IOException if IOException happens while connecting to the TMDB
     * @throws TmNoticeXmlParseException in case, the trade mark notice response XML could not be parsed.
     */
    public TmNotice requestNotice(String lookupKey) throws IOException, TmNoticeXmlParseException {
        String url = tmdbClientProperties.getTmdbServerUrl() + "/" + lookupKey + ".xml";
        HttpsURLConnection connection = tlsContext.createHttpsUrlConnection(url,
                tmdbClientProperties.getTmdbSocketTimeOut());

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return tmNoticeXmlParser.parse(sb.toString());
        } finally {
            connection.getInputStream().close();
        }
    }
}
