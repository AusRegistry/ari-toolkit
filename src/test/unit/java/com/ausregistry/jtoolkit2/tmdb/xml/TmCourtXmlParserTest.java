package com.ausregistry.jtoolkit2.tmdb.xml;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

import com.ausregistry.jtoolkit2.tmdb.model.TmCourt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Node;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TmCourtXmlParser.class})
public class TmCourtXmlParserTest {

    @Mock
    private TmCourt mockCourt;

    private TmCourtXmlParser tmCourtXmlParser = new TmCourtXmlParser();

    @Before
    public void setUp() throws Exception {
        whenNew(TmCourt.class).withNoArguments().thenReturn(mockCourt);
    }

    @Test
    public void shouldParseReferenceNumberCorrectly() throws Exception {
        String courtXml = "<tmNotice:court><tmNotice:refNum>234235</tmNotice:refNum></tmNotice:court>";

        TmCourt court = tmCourtXmlParser.parse(nodeForString(courtXml));

        assertThat(court, sameInstance(mockCourt));
        verify(mockCourt).setReferenceNumber(234235L);
    }

    @Test
    public void shouldParseCountryCodeCorrectly() throws Exception {
        String courtXml = "<tmNotice:court><tmNotice:cc>CR</tmNotice:cc></tmNotice:court>";

        tmCourtXmlParser.parse(nodeForString(courtXml));

        verify(mockCourt).setCountryCode("CR");
    }

    @Test
    public void shouldParseRegionsCorrectly() throws Exception {
        String courtXml = "<tmNotice:court>" +
                "<tmNotice:region>region 1</tmNotice:region>" +
                "<tmNotice:region>region 2</tmNotice:region>" +
                "</tmNotice:court>";

        tmCourtXmlParser.parse(nodeForString(courtXml));

        verify(mockCourt).addRegion("region 1");
        verify(mockCourt).addRegion("region 2");
    }

    @Test
    public void shouldParseCourtNameCorrectly() throws Exception {
        String courtXml = "<tmNotice:court>" +
                "<tmNotice:courtName>Supreme Court of Justice of Costa Rica</tmNotice:courtName>" +
                "</tmNotice:court>";

        tmCourtXmlParser.parse(nodeForString(courtXml));

        verify(mockCourt).setCourtName("Supreme Court of Justice of Costa Rica");
    }

    private Node nodeForString(String xmlFragment) throws Exception {
        return DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xmlFragment.getBytes()))
                .getDocumentElement();
    }
}
