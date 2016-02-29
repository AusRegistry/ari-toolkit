package com.ausregistry.jtoolkit2.tmdb;

import static com.harlap.test.http.MockHttpServer.Method;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ari.dnrs.test.infrastructure.RegistryMockSSLHttpServer;
import com.ausregistry.jtoolkit2.tmdb.model.TmClaim;
import com.ausregistry.jtoolkit2.tmdb.model.TmNotice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * This test would only work with Java7 because the test dependency 'ari-mock-http-server' is compiled with Java7
 */
public class TmdbClientComponentTest {

    private RegistryMockSSLHttpServer mockHttpsServer;

    @Before
    public void setUp() throws Exception {
        String keystorePath =
                Thread.currentThread().getContextClassLoader().getResource("keystore.jks").getPath();
        mockHttpsServer = new RegistryMockSSLHttpServer(11678, keystorePath);
        mockHttpsServer.start();
    }

    @Test
    public void shouldGetCorrectTmcNoticeFromTheTmdb() throws Exception {

        String noticeXml = readNoticeXmlFromFile("sample-tmNotice.xml");

        mockHttpsServer.expect(Method.GET, "/lookup/key.xml")
                            .respondWith(200, "text/xml", noticeXml);

        TmdbClient tmdbClient = new TmdbClient();

        TmNotice tmNotice = tmdbClient.requestNotice("lookup/key");

        assertThat(tmNotice, notNullValue());
        assertThat(tmNotice.getId(), is("370d0b7c9223372036854775807"));
        assertThat(tmNotice.getNotBeforeDateTime().getTimeInMillis(),
                is(DatatypeConverter.parseDate("2010-08-14T09:00:00.0Z").getTimeInMillis()));

        assertThat(tmNotice.getLabel(), is("example-one"));
        assertThat(tmNotice.getClaims().size(), is(4));

        TmClaim tmClaimOne = tmNotice.getClaims().get(0);

        assertThat(tmClaimOne.getMarkName(), is("Example One"));

        assertThat(tmClaimOne.getHolders().size(), is(2));
        assertThat(tmClaimOne.getHolders().get(0).getAddress().getCity(), is("Reston"));

        assertThat(tmClaimOne.getContacts().size(), is(2));
        assertThat(tmClaimOne.getContacts().get(0).getAddress().getCountryCode(), is("US"));

        assertThat(tmNotice.getClaims().get(3).getUdrps().size(), is(2));
        assertThat(tmNotice.getClaims().get(3).getUdrps().get(0).getCaseNumber(), is("D2003-0499"));

        assertThat(tmNotice.getClaims().get(3).getCourts().size(), is(1));
        assertThat(tmNotice.getClaims().get(3).getCourts().get(0).getCountryCode(), is("CR"));
    }

    @Test
    public void shouldNotFailWhenQueryingMultipleNotices() throws Exception {
        mockHttpsServer
                .expect(Method.GET, "/lookup/key.xml")
                .respondWith(200, "text/xml",
                        "<tmNotice:notice xmlns:tmNotice=\"urn:ietf:params:xml:ns:tmNotice-1.0\">"
                                + "<tmNotice:id>noticeId1</tmNotice:id>"
                                + "</tmNotice:notice>");

        mockHttpsServer
                .expect(Method.GET, "/anotherLookupKey.xml")
                .respondWith(200, "text/xml",
                        "<tmNotice:notice xmlns:tmNotice=\"urn:ietf:params:xml:ns:tmNotice-1.0\">"
                                + "<tmNotice:id>noticeId2</tmNotice:id>"
                                + "</tmNotice:notice>");

        TmdbClient tmdbClient = new TmdbClient();
        TmNotice firstTmNotice = tmdbClient.requestNotice("lookup/key");
        TmNotice secondTmNotice = tmdbClient.requestNotice("anotherLookupKey");

        assertThat(firstTmNotice.getId(), is("noticeId1"));
        assertThat(secondTmNotice.getId(), is("noticeId2"));
    }


    private String readNoticeXmlFromFile(String fileName) throws IOException {
        InputStream noticeXmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

        BufferedReader br = new BufferedReader(new InputStreamReader(noticeXmlStream));
        String line;
        StringBuffer noticeXml = new StringBuffer();
        while ((line = br.readLine()) != null) {
            noticeXml.append(line);
        }
        return noticeXml.toString();
    }

    @After
    public void tearDown() throws Exception {
        mockHttpsServer.stop();
    }
}
