package com.ausregistry.jtoolkit2.tmdb.xml;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.ausregistry.jtoolkit2.tmdb.model.TmUdrp;
import org.w3c.dom.Node;

public class TmUdrpXmlParser {

    public TmUdrp parse(Node udrpNode) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        TmUdrp tmUdrp = new TmUdrp();

        tmUdrp.setCaseNumber(xPath.evaluate("caseNo", udrpNode));
        tmUdrp.setUdrpProvider(xPath.evaluate("udrpProvider", udrpNode));

        return tmUdrp;
    }
}
