package com.ausregistry.jtoolkit2.tmdb.xml;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.ausregistry.jtoolkit2.tmdb.model.TmClaim;
import com.ausregistry.jtoolkit2.tmdb.model.TmClaimClassificationDesc;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TmClaimXmlParser {

    public TmClaim parse(Node claimNode) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        TmClaim tmClaim = new TmClaim();

        tmClaim.setMarkName(xPath.evaluate("markName", claimNode));

        TmHolderXmlParser tmHolderXmlParser = new TmHolderXmlParser();

        NodeList holderNodes = (NodeList) xPath.evaluate("holder", claimNode, XPathConstants.NODESET);

        for (int i = 0; i < holderNodes.getLength(); i++) {
            tmClaim.addHolder(tmHolderXmlParser.parse(holderNodes.item(i)));
        }

        TmContactXmlParser tmContactXmlParser = new TmContactXmlParser();

        NodeList contactNodes = (NodeList) xPath.evaluate("contact", claimNode, XPathConstants.NODESET);

        for (int i = 0; i < contactNodes.getLength(); i++) {
            tmClaim.addContact(tmContactXmlParser.parse(contactNodes.item(i)));
        }

        TmUdrpXmlParser tmUdrpXmlParser = new TmUdrpXmlParser();

        NodeList udrpNodes = (NodeList) xPath.evaluate("notExactMatch/udrp", claimNode, XPathConstants.NODESET);

        for (int i = 0; i < udrpNodes.getLength(); i++) {
            tmClaim.addUdrp(tmUdrpXmlParser.parse(udrpNodes.item(i)));
        }

        TmCourtXmlParser tmCourtXmlParser = new TmCourtXmlParser();

        NodeList courtNodes = (NodeList) xPath.evaluate("notExactMatch/court", claimNode,
                XPathConstants.NODESET);

        for (int i = 0; i < courtNodes.getLength(); i++) {
            tmClaim.addCourt(tmCourtXmlParser.parse(courtNodes.item(i)));
        }

        tmClaim.setJurisdiction(xPath.evaluate("jurDesc/text()", claimNode));
        tmClaim.setJurisdictionCC(xPath.evaluate("jurDesc/@jurCC", claimNode));
        tmClaim.setGoodsAndServices(xPath.evaluate("goodsAndServices/text()", claimNode));

        NodeList classDescNodes = (NodeList) xPath.evaluate("classDesc", claimNode, XPathConstants.NODESET);
        for (int i = 0; i < classDescNodes.getLength(); i++) {
            String classNumber = xPath.evaluate("@classNum", classDescNodes.item(i));
            String description = xPath.evaluate("text()", classDescNodes.item(i));

            tmClaim.addClassificationDesc(new TmClaimClassificationDesc(Integer.parseInt(classNumber),
                    description));
        }

        return tmClaim;
    }
}
