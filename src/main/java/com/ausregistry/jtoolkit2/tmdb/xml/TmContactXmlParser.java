package com.ausregistry.jtoolkit2.tmdb.xml;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.ausregistry.jtoolkit2.tmdb.model.TmContact;
import org.w3c.dom.Node;

public class TmContactXmlParser {
    public TmContact parse(Node contactNode) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        TmContact tmContact = new TmContact();

        tmContact.setType(xPath.evaluate("@type", contactNode));

        tmContact.setName(xPath.evaluate("name", contactNode));
        tmContact.setOrganisation(xPath.evaluate("org", contactNode));

        TmAddressXmlParser addressXmlParser = new TmAddressXmlParser();
        Node addressNode = (Node) xPath.evaluate("addr", contactNode, XPathConstants.NODE);
        if (addressNode != null) {
            tmContact.setAddress(addressXmlParser.parse(addressNode));
        }

        tmContact.setVoice(xPath.evaluate("voice", contactNode));
        tmContact.setVoiceExtension(xPath.evaluate("voice/@x", contactNode));

        tmContact.setFax(xPath.evaluate("fax", contactNode));
        tmContact.setFaxExtension(xPath.evaluate("fax/@x", contactNode));

        tmContact.setEmail(xPath.evaluate("email", contactNode));

        return tmContact;
    }
}
