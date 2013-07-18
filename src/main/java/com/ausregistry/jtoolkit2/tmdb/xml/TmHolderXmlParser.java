package com.ausregistry.jtoolkit2.tmdb.xml;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.ausregistry.jtoolkit2.tmdb.model.TmHolder;
import org.w3c.dom.Node;

public class TmHolderXmlParser {
    public TmHolder parse(Node holderNode) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        TmHolder tmHolder = new TmHolder();

        tmHolder.setEntitlement(xPath.evaluate("@entitlement", holderNode));

        tmHolder.setName(xPath.evaluate("name", holderNode));
        tmHolder.setOrganisation(xPath.evaluate("org", holderNode));

        TmAddressXmlParser addressXmlParser = new TmAddressXmlParser();
        Node addressNode = (Node) xPath.evaluate("addr", holderNode, XPathConstants.NODE);
        if (addressNode != null) {
            tmHolder.setAddress(addressXmlParser.parse(addressNode));
        }

        tmHolder.setVoice(xPath.evaluate("voice", holderNode));
        tmHolder.setVoiceExtension(xPath.evaluate("voice/@x", holderNode));

        tmHolder.setFax(xPath.evaluate("fax", holderNode));
        tmHolder.setFaxExtension(xPath.evaluate("fax/@x", holderNode));

        tmHolder.setEmail(xPath.evaluate("email", holderNode));

        return tmHolder;
    }
}
