package com.ausregistry.jtoolkit2.tmdb.xml;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

import com.ausregistry.jtoolkit2.tmdb.model.TmAddress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Node;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TmAddressXmlParser.class})
public class TmAddressXmlParserTest {
    @Mock
    private TmAddress mockAddress;
    private TmAddressXmlParser tmAddressXmlParser = new TmAddressXmlParser();

    @Before
    public void setUp() throws Exception {
        whenNew(TmAddress.class).withNoArguments().thenReturn(mockAddress);
    }

    @Test
    public void shouldParseStreetsCorrectly() throws Exception {
        String addressXml = " <tmNotice:addr>" +
                "        <tmNotice:street>123 Example Dr.</tmNotice:street>" +
                "        <tmNotice:street>Suite 100</tmNotice:street>" +
                "</tmNotice:addr>";

        TmAddress address = tmAddressXmlParser.parse(nodeForString(addressXml));

        assertThat(address, sameInstance(mockAddress));
        InOrder inOrder = inOrder(mockAddress);
        inOrder.verify(mockAddress).addStreet("123 Example Dr.");
        inOrder.verify(mockAddress).addStreet("Suite 100");

    }

    @Test
    public void shouldParseCityCorrectly() throws Exception {
        String addressXml = " <tmNotice:addr><tmNotice:city>SFO</tmNotice:city></tmNotice:addr>";

        tmAddressXmlParser.parse(nodeForString(addressXml));

        verify(mockAddress).setCity("SFO");

    }

    @Test
    public void shouldParseStateCorrectly() throws Exception {
        String addressXml = " <tmNotice:addr><tmNotice:sp>VIC</tmNotice:sp></tmNotice:addr>";

        tmAddressXmlParser.parse(nodeForString(addressXml));

        verify(mockAddress).setStateOrProvince("VIC");

    }

    @Test
    public void shouldParsePostalCodeCorrectly() throws Exception {
        String addressXml = " <tmNotice:addr><tmNotice:pc>3141</tmNotice:pc></tmNotice:addr>";

        tmAddressXmlParser.parse(nodeForString(addressXml));

        verify(mockAddress).setPostalCode("3141");
    }

    @Test
    public void shouldParseCountryCodeCorrectly() throws Exception {
        String addressXml = " <tmNotice:addr><tmNotice:cc>AU</tmNotice:cc></tmNotice:addr>";

        tmAddressXmlParser.parse(nodeForString(addressXml));

        verify(mockAddress).setCountryCode("AU");

    }

    private Node nodeForString(String xmlFragment) throws Exception {
        return DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xmlFragment.getBytes()))
                .getDocumentElement();
    }

}
