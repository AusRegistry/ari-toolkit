package com.ausregistry.jtoolkit2.tmdb.xml;

import static com.ausregistry.jtoolkit2.test.infrastructure.ToolkitMatchers.isNodeForXml;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.GregorianCalendar;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.tmdb.model.TmNotice;
import com.ausregistry.jtoolkit2.tmdb.model.TmClaim;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TmNoticeXmlParser.class, EPPDateFormatter.class, TmClaimXmlParser.class})
public class TmNoticeXmlParserTest {

    private TmNoticeXmlParser tmNoticeXmlParser = new TmNoticeXmlParser();
    private TmNotice mockTmNotice;

    @Mock
    private GregorianCalendar mockCalender;

    @Before
    public void setUp() throws Exception {
        mockTmNotice = mock(TmNotice.class);
        whenNew(TmNotice.class).withNoArguments().thenReturn(mockTmNotice);

        mockStatic(EPPDateFormatter.class);
    }

    @Test
    public void shouldParseIdCorrectly() throws Exception {
        String noticeXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<tmNotice:notice xmlns:tmNotice=\"urn:ietf:params:xml:ns:tmNotice-1.0\">" +
                "  <tmNotice:id>370d0b7c9223372036854775807</tmNotice:id>" +
                "</tmNotice:notice>";

        TmNotice tmNotice = tmNoticeXmlParser.parse(noticeXml);

        assertThat(tmNotice, is(mockTmNotice));
        verify(mockTmNotice).setId("370d0b7c9223372036854775807");
    }

    @Test
    public void shouldParseNotBeforeDateCorrectly() throws Exception {
        String noticeXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<tmNotice:notice xmlns:tmNotice=\"urn:ietf:params:xml:ns:tmNotice-1.0\">" +
                "  <tmNotice:notBefore>2010-08-14T09:00:00.0Z</tmNotice:notBefore>" +
                "</tmNotice:notice>";

        when(EPPDateFormatter.fromXSDateTime("2010-08-14T09:00:00.0Z")).thenReturn(mockCalender);

        tmNoticeXmlParser.parse(noticeXml);

        verify(mockTmNotice).setNotBeforeDateTime(mockCalender);
    }

    @Test
    public void shouldParseNotAfterDateCorrectly() throws Exception {
        String noticeXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<tmNotice:notice xmlns:tmNotice=\"urn:ietf:params:xml:ns:tmNotice-1.0\">" +
                "  <tmNotice:notAfter>2010-08-16T09:00:00.0Z</tmNotice:notAfter>" +
                "</tmNotice:notice>";

        when(EPPDateFormatter.fromXSDateTime("2010-08-16T09:00:00.0Z")).thenReturn(mockCalender);

        tmNoticeXmlParser.parse(noticeXml);

        verify(mockTmNotice).setNotAfterDateTime(mockCalender);
    }

    @Test
    public void shouldParseLabelCorrectly() throws Exception {
        String noticeXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<tmNotice:notice xmlns:tmNotice=\"urn:ietf:params:xml:ns:tmNotice-1.0\">" +
                "  <tmNotice:label>example-one</tmNotice:label>" +
                "</tmNotice:notice>";

        tmNoticeXmlParser.parse(noticeXml);

        verify(mockTmNotice).setLabel("example-one");
    }

    //This test will only work if we pass a JVM argument -XX:-UseSplitVerifier
    @Test
    public void shouldParseAllTheClaimXmlObjects() throws Exception {
        String noticeXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<tmNotice:notice xmlns:tmNotice=\"urn:ietf:params:xml:ns:tmNotice-1.0\">" +
                "  <tmNotice:claim>" +
                "    <tmNotice:markName>Example-One</tmNotice:markName>" +
                "  </tmNotice:claim>" +
                "  <tmNotice:claim>" +
                "    <tmNotice:markName>Example-Two</tmNotice:markName>" +
                "  </tmNotice:claim>" +
                "</tmNotice:notice>";

        String firstClaimXml = "  <tmNotice:claim>" +
                "    <tmNotice:markName>Example-One</tmNotice:markName>" +
                "  </tmNotice:claim>";

        String secondClaimXml = "  <tmNotice:claim>" +
                "    <tmNotice:markName>Example-Two</tmNotice:markName>" +
                "  </tmNotice:claim>";


        TmClaimXmlParser mockClaimParser = mock(TmClaimXmlParser.class);
        whenNew(TmClaimXmlParser.class).withNoArguments().thenReturn(mockClaimParser);

        TmClaim mockFirstTmClaim = mock(TmClaim.class);
        when(mockClaimParser.parse(isNodeForXml(firstClaimXml))).thenReturn(mockFirstTmClaim);
        TmClaim mockSecondTmClaim = mock(TmClaim.class);
        when(mockClaimParser.parse(isNodeForXml(secondClaimXml))).thenReturn(mockSecondTmClaim);

        tmNoticeXmlParser.parse(noticeXml);

        verify(mockTmNotice).addClaim(mockFirstTmClaim);
        verify(mockTmNotice).addClaim(mockSecondTmClaim);
    }

}
