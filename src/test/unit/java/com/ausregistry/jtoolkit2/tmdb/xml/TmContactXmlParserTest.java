package com.ausregistry.jtoolkit2.tmdb.xml;

import static com.ausregistry.jtoolkit2.test.infrastructure.ToolkitMatchers.isNodeForXml;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

import com.ausregistry.jtoolkit2.tmdb.model.TmAddress;
import com.ausregistry.jtoolkit2.tmdb.model.TmContact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Node;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TmContactXmlParser.class})
public class TmContactXmlParserTest {

    @Mock
    private TmContact mockTmContact;
    private TmContactXmlParser tmContactXmlParser;

    @Before
    public void setUp() throws Exception {
        tmContactXmlParser = new TmContactXmlParser();

        whenNew(TmContact.class).withNoArguments().thenReturn(mockTmContact);
    }

    @Test
    public void shouldParseTypeCorrectly() throws Exception {

        String contactXml = "<tmNotice:contact type=\"agent\" />";

        TmContact tmContact = tmContactXmlParser.parse(nodeForString(contactXml));

        assertThat(tmContact, sameInstance(mockTmContact));
        verify(mockTmContact).setType("agent");
    }

    @Test
    public void shouldParseNameCorrectly() throws Exception {

        String contactXml = "<tmNotice:contact entitlement=\"owner\">" +
                "<tmNotice:name>Example Name One</tmNotice:name>" +
                "</tmNotice:contact>";

        tmContactXmlParser.parse(nodeForString(contactXml));

        verify(mockTmContact).setName("Example Name One");
    }

    @Test
    public void shouldParseOrganisationCorrectly() throws Exception {

        String contactXml = "<tmNotice:contact entitlement=\"owner\">" +
                "<tmNotice:org>Example Organisation One</tmNotice:org>" +
                "</tmNotice:contact>";

        tmContactXmlParser.parse(nodeForString(contactXml));

        verify(mockTmContact).setOrganisation("Example Organisation One");
    }

    @Test
    public void shouldParseEmailCorrectly() throws Exception {

        String contactXml = "<tmNotice:contact entitlement=\"owner\">" +
                "<tmNotice:email>Example@Email.One</tmNotice:email>" +
                "</tmNotice:contact>";

        tmContactXmlParser.parse(nodeForString(contactXml));

        verify(mockTmContact).setEmail("Example@Email.One");
    }

    @Test
    public void shouldParseVoiceCorrectly() throws Exception {

        String contactXml = "<tmNotice:contact entitlement=\"owner\">" +
                "<tmNotice:voice x=\"4321\">+1.7035555555</tmNotice:voice>" +
                "</tmNotice:contact>";

        tmContactXmlParser.parse(nodeForString(contactXml));

        verify(mockTmContact).setVoice("+1.7035555555");
        verify(mockTmContact).setVoiceExtension("4321");
    }

    @Test
    public void shouldParseFaxCorrectly() throws Exception {

        String contactXml = "<tmNotice:contact entitlement=\"owner\">" +
                "<tmNotice:fax x=\"4321\">+1.7035555555</tmNotice:fax>" +
                "</tmNotice:contact>";

        tmContactXmlParser.parse(nodeForString(contactXml));

        verify(mockTmContact).setFax("+1.7035555555");
        verify(mockTmContact).setFaxExtension("4321");
    }

    @Test
    public void shouldParseAddressCorrectly() throws Exception {

        String contactXml = "<tmNotice:contact entitlement=\"owner\">" +
                "<tmNotice:addr />" +
                "</tmNotice:contact>";

        TmAddressXmlParser mockTmAddressXmlParser = mock(TmAddressXmlParser.class);
        whenNew(TmAddressXmlParser.class).withNoArguments().thenReturn(mockTmAddressXmlParser);

        TmAddress mockTmAddress = mock(TmAddress.class);
        when(mockTmAddressXmlParser.parse(isNodeForXml("<tmNotice:addr />"))).thenReturn(mockTmAddress);

        tmContactXmlParser.parse(nodeForString(contactXml));

        verify(mockTmContact).setAddress(mockTmAddress);
    }

    private Node nodeForString(String xmlFragment) throws Exception {
        return DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xmlFragment.getBytes()))
                .getDocumentElement();
    }
}
