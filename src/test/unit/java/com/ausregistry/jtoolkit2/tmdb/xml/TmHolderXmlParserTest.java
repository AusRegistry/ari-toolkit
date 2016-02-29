package com.ausregistry.jtoolkit2.tmdb.xml;

import static com.ausregistry.jtoolkit2.test.infrastructure.ToolkitMatchers.isNodeForXml;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

import com.ausregistry.jtoolkit2.tmdb.model.TmAddress;
import com.ausregistry.jtoolkit2.tmdb.model.TmHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Node;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TmHolderXmlParser.class})
public class TmHolderXmlParserTest {

    @Mock
    private TmHolder mockTmHolder;
    private TmHolderXmlParser tmHolderXmlParser;

    @Before
    public void setUp() throws Exception {
        tmHolderXmlParser = new TmHolderXmlParser();

        whenNew(TmHolder.class).withNoArguments().thenReturn(mockTmHolder);
    }

    @Test
    public void shouldParseEntitlementCorrectly() throws Exception {

        String holderXml = "<tmNotice:holder entitlement=\"owner\" />";

        TmHolder tmHolder = tmHolderXmlParser.parse(nodeForString(holderXml));

        assertThat(tmHolder, sameInstance(mockTmHolder));
        verify(mockTmHolder).setEntitlement("owner");
    }

    @Test
    public void shouldParseNameCorrectly() throws Exception {

        String holderXml = "<tmNotice:holder entitlement=\"owner\">" +
                "<tmNotice:name>Example Name One</tmNotice:name>" +
                "</tmNotice:holder>";

        tmHolderXmlParser.parse(nodeForString(holderXml));

        verify(mockTmHolder).setName("Example Name One");
    }

    @Test
    public void shouldParseOrganisationCorrectly() throws Exception {

        String holderXml = "<tmNotice:holder entitlement=\"owner\">" +
                "<tmNotice:org>Example Organisation One</tmNotice:org>" +
                "</tmNotice:holder>";

        tmHolderXmlParser.parse(nodeForString(holderXml));

        verify(mockTmHolder).setOrganisation("Example Organisation One");
    }

    @Test
    public void shouldParseEmailCorrectly() throws Exception {

        String holderXml = "<tmNotice:holder entitlement=\"owner\">" +
                "<tmNotice:email>Example@Email.One</tmNotice:email>" +
                "</tmNotice:holder>";

        tmHolderXmlParser.parse(nodeForString(holderXml));

        verify(mockTmHolder).setEmail("Example@Email.One");
    }

    @Test
    public void shouldParseVoiceCorrectly() throws Exception {

        String holderXml = "<tmNotice:holder entitlement=\"owner\">" +
                "<tmNotice:voice x=\"4321\">+1.7035555555</tmNotice:voice>" +
                "</tmNotice:holder>";

        tmHolderXmlParser.parse(nodeForString(holderXml));

        verify(mockTmHolder).setVoice("+1.7035555555");
        verify(mockTmHolder).setVoiceExtension("4321");
    }

    @Test
    public void shouldParseFaxCorrectly() throws Exception {

        String holderXml = "<tmNotice:holder entitlement=\"owner\">" +
                "<tmNotice:fax x=\"4321\">+1.7035555555</tmNotice:fax>" +
                "</tmNotice:holder>";

        tmHolderXmlParser.parse(nodeForString(holderXml));

        verify(mockTmHolder).setFax("+1.7035555555");
        verify(mockTmHolder).setFaxExtension("4321");
    }

    @Test
    public void shouldParseAddressCorrectly() throws Exception {

        String holderXml = "<tmNotice:holder entitlement=\"owner\">" +
                "<tmNotice:addr />" +
                "</tmNotice:holder>";

        TmAddressXmlParser mockTmAddressXmlParser = mock(TmAddressXmlParser.class);
        whenNew(TmAddressXmlParser.class).withNoArguments().thenReturn(mockTmAddressXmlParser);

        TmAddress mockTmAddress = mock(TmAddress.class);
        when(mockTmAddressXmlParser.parse(isNodeForXml("<tmNotice:addr />"))).thenReturn(mockTmAddress);

        tmHolderXmlParser.parse(nodeForString(holderXml));

        verify(mockTmHolder).setAddress(mockTmAddress);
    }

    private Node nodeForString(String xmlFragment) throws Exception {
        return DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xmlFragment.getBytes()))
                .getDocumentElement();
    }
}
