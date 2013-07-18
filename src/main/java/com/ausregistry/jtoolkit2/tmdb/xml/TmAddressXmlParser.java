package com.ausregistry.jtoolkit2.tmdb.xml;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.ausregistry.jtoolkit2.tmdb.model.TmAddress;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TmAddressXmlParser {
    public TmAddress parse(Node addressNode) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();

        TmAddress tmAddress = new TmAddress();

        NodeList streetNodes = (NodeList) xPath.evaluate("street", addressNode, XPathConstants.NODESET);
        for (int i = 0; i < streetNodes.getLength(); i++) {
            tmAddress.addStreet(streetNodes.item(i).getFirstChild().getNodeValue());
        }

        tmAddress.setCity(xPath.evaluate("city", addressNode));
        tmAddress.setStateOrProvince(xPath.evaluate("sp", addressNode));
        tmAddress.setPostalCode(xPath.evaluate("pc", addressNode));
        tmAddress.setCountryCode(xPath.evaluate("cc", addressNode));

        return tmAddress;
    }
}
