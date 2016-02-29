package com.ausregistry.jtoolkit2.tmdb.xml;

import static com.ausregistry.jtoolkit2.test.infrastructure.ToolkitMatchers.isNodeForXml;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

import com.ausregistry.jtoolkit2.tmdb.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Node;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TmClaimXmlParser.class, TmClaimClassificationDesc.class})
public class TmClaimXmlParserTest {

    @Mock
    private TmClaim mockClaim;

    private TmClaimXmlParser tmClaimXmlParser = new TmClaimXmlParser();

    @Before
    public void setUp() throws Exception {
        whenNew(TmClaim.class).withNoArguments().thenReturn(mockClaim);
    }

    @Test
    public void shouldParseMarkNameCorrectly() throws Exception {
        String claimXml =
                "<tmNotice:claim>" +
                        "    <tmNotice:markName>Example One</tmNotice:markName>" +
                        "</tmNotice:claim>";

        TmClaim claim = tmClaimXmlParser.parse(nodeForString(claimXml));

        assertThat(claim, is(mockClaim));
        verify(mockClaim).setMarkName("Example One");

    }

    @Test
    public void shouldParseJurDescCorrectly() throws Exception {
        String claimXml =
                "<tmNotice:claim>" +
                        "    <tmNotice:jurDesc jurCC=\"US\">UNITED STATES OF AMERICA</tmNotice:jurDesc>" +
                        "</tmNotice:claim>";

        tmClaimXmlParser.parse(nodeForString(claimXml));

        verify(mockClaim).setJurisdiction("UNITED STATES OF AMERICA");
        verify(mockClaim).setJurisdictionCC("US");
    }

    @Test
    public void shouldParseGoodsAndServicesCorrectly() throws Exception {
        String claimXml =
                "<tmNotice:claim>" +
                        "<tmNotice:goodsAndServices>" +
                        "Bardus populorum circumdabit se cum captiosus populum.\n" +
                        "Smert populorum circumdabit se cum captiosus populum qui eis differimus." +
                        "</tmNotice:goodsAndServices>" +
                        "</tmNotice:claim>";

        tmClaimXmlParser.parse(nodeForString(claimXml));

        verify(mockClaim).setGoodsAndServices("Bardus populorum circumdabit se cum captiosus populum.\n" +
                "Smert populorum circumdabit se cum captiosus populum qui eis differimus.");
    }

    @Test
    public void shouldParseClassDescCorrectly() throws Exception {
        String claimXml =
                "<tmNotice:claim>" +
                        "<tmNotice:classDesc classNum=\"35\">" +
                        "Advertising; business management; business administration." +
                        "</tmNotice:classDesc>" +
                        "<tmNotice:classDesc classNum=\"36\">" +
                        "Insurance; financial affairs; monetary affairs; real estate." +
                        "</tmNotice:classDesc>" +
                        "</tmNotice:claim>";


        TmClaimClassificationDesc mockFirstClaimClassDesc = mock(TmClaimClassificationDesc.class);
        whenNew(TmClaimClassificationDesc.class)
        .withArguments(35, "Advertising; business management; business administration.")
        .thenReturn(mockFirstClaimClassDesc);

        TmClaimClassificationDesc mockSecondClaimClassDesc = mock(TmClaimClassificationDesc.class);
        whenNew(TmClaimClassificationDesc.class)
        .withArguments(36, "Insurance; financial affairs; monetary affairs; real estate.")
        .thenReturn(mockSecondClaimClassDesc);

        tmClaimXmlParser.parse(nodeForString(claimXml));

        verify(mockClaim).addClassificationDesc(mockFirstClaimClassDesc);
        verify(mockClaim).addClassificationDesc(mockSecondClaimClassDesc);
    }

    @Test
    public void shouldParseHolderCorrectly() throws Exception {
        String claimXml =
                "<tmNotice:claim>" +
                        "<tmNotice:holder entitlement=\"owner\" />" +
                        "<tmNotice:holder entitlement=\"assignee\" />" +
                "</tmNotice:claim>";

        TmHolderXmlParser mockTmHolderParser = mock(TmHolderXmlParser.class);
        whenNew(TmHolderXmlParser.class).withNoArguments().thenReturn(mockTmHolderParser);

        TmHolder mockHolderOne = mock(TmHolder.class);
        when(mockTmHolderParser.parse(isNodeForXml("<tmNotice:holder entitlement=\"owner\" />")))
                .thenReturn(mockHolderOne);
        TmHolder mockHolderTwo = mock(TmHolder.class);
        when(mockTmHolderParser.parse(isNodeForXml("<tmNotice:holder entitlement=\"assignee\" />")))
                .thenReturn(mockHolderTwo);

        tmClaimXmlParser.parse(nodeForString(claimXml));

        verify(mockClaim).addHolder(mockHolderOne);
        verify(mockClaim).addHolder(mockHolderTwo);
    }


    @Test
    public void shouldParseContactCorrectly() throws Exception {
        String claimXml =
                "<tmNotice:claim>" +
                        "<tmNotice:contact type=\"owner\" />" +
                        "<tmNotice:contact type=\"thirdparty\" />" +
                "</tmNotice:claim>";

        TmContactXmlParser mockTmContactParser = mock(TmContactXmlParser.class);
        whenNew(TmContactXmlParser.class).withNoArguments().thenReturn(mockTmContactParser);

        TmContact mockContactOne = mock(TmContact.class);
        when(mockTmContactParser.parse(isNodeForXml("<tmNotice:contact type=\"owner\" />")))
                .thenReturn(mockContactOne);
        TmContact mockContactTwo = mock(TmContact.class);
        when(mockTmContactParser.parse(isNodeForXml("<tmNotice:contact type=\"thirdparty\" />")))
                .thenReturn(mockContactTwo);

        tmClaimXmlParser.parse(nodeForString(claimXml));

        verify(mockClaim).addContact(mockContactOne);
        verify(mockClaim).addContact(mockContactTwo);
    }

    @Test
    public void shouldParseUdrpNotExactMatchCorrectly() throws Exception {
        String claimXml =
                "<tmNotice:claim>" +
                        "<tmNotice:notExactMatch>" +
                        "     <tmNotice:udrp>" +
                        "        <tmNotice:caseNo>CaseNo 1</tmNotice:caseNo>" +
                        "      </tmNotice:udrp>" +
                        "      <tmNotice:court />" +
                        "     <tmNotice:udrp>" +
                        "        <tmNotice:caseNo>CaseNo 2</tmNotice:caseNo>" +
                        "      </tmNotice:udrp>" +
                        "    </tmNotice:notExactMatch>" +
                "</tmNotice:claim>";

        String firstUdrpXml = "<tmNotice:udrp><tmNotice:caseNo>CaseNo 1</tmNotice:caseNo></tmNotice:udrp>";
        String secondUdrpXml = "<tmNotice:udrp><tmNotice:caseNo>CaseNo 2</tmNotice:caseNo></tmNotice:udrp>";

        TmUdrpXmlParser mockTmUdrpXmlParser = mock(TmUdrpXmlParser.class);
        whenNew(TmUdrpXmlParser.class).withNoArguments().thenReturn(mockTmUdrpXmlParser);

        TmUdrp mockUdrpOne = mock(TmUdrp.class);
        when(mockTmUdrpXmlParser.parse(isNodeForXml(firstUdrpXml)))
                .thenReturn(mockUdrpOne);
        TmUdrp mockUdrpTwo = mock(TmUdrp.class);
        when(mockTmUdrpXmlParser.parse(isNodeForXml(secondUdrpXml)))
                .thenReturn(mockUdrpTwo);

        tmClaimXmlParser.parse(nodeForString(claimXml));

        verify(mockClaim).addUdrp(mockUdrpOne);
        verify(mockClaim).addUdrp(mockUdrpTwo);
    }

    @Test
    public void shouldParseCourtNotExactMatchCorrectly() throws Exception {
        String claimXml =
                "<tmNotice:claim>" +
                        "<tmNotice:notExactMatch>" +
                        "   <tmNotice:court><tmNotice:cc>CR</tmNotice:cc></tmNotice:court>" +
                        "   <tmNotice:court><tmNotice:cc>AU</tmNotice:cc></tmNotice:court>" +
                        "</tmNotice:notExactMatch>" +
                "</tmNotice:claim>";

        TmCourtXmlParser mockTmCourtXmlParser = mock(TmCourtXmlParser.class);
        whenNew(TmCourtXmlParser.class).withNoArguments().thenReturn(mockTmCourtXmlParser);

        TmCourt mockCourtOne = mock(TmCourt.class);
        when(mockTmCourtXmlParser.parse(isNodeForXml("<tmNotice:court><tmNotice:cc>CR</tmNotice:cc></tmNotice:court>")))
                .thenReturn(mockCourtOne);
        TmCourt mockCourtTwo = mock(TmCourt.class);
        when(mockTmCourtXmlParser.parse(isNodeForXml("<tmNotice:court><tmNotice:cc>AU</tmNotice:cc></tmNotice:court>")))
                .thenReturn(mockCourtTwo);

        tmClaimXmlParser.parse(nodeForString(claimXml));

        verify(mockClaim).addCourt(mockCourtOne);
        verify(mockClaim).addCourt(mockCourtTwo);
    }

    private Node nodeForString(String xmlFragment) throws Exception {
        return DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xmlFragment.getBytes()))
                .getDocumentElement();
    }
}
