package com.ausregistry.jtoolkit2.tmdb.xml;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.ausregistry.jtoolkit2.tmdb.model.TmCourt;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TmCourtXmlParser {
    public TmCourt parse(Node courtNode) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        TmCourt tmCourt = new TmCourt();

        String referenceNumberString = xPath.evaluate("refNum", courtNode);
        if (referenceNumberString != null && !"".equals(referenceNumberString)) {
            tmCourt.setReferenceNumber(Long.parseLong(referenceNumberString));
        }

        tmCourt.setCountryCode(xPath.evaluate("cc", courtNode));

        NodeList regionNodes = (NodeList) xPath.evaluate("region", courtNode, XPathConstants.NODESET);
        for (int i = 0; i < regionNodes.getLength(); i++) {
            tmCourt.addRegion(regionNodes.item(i).getFirstChild().getNodeValue());
        }

        tmCourt.setCourtName(xPath.evaluate("courtName", courtNode));

        return tmCourt;
    }
}
