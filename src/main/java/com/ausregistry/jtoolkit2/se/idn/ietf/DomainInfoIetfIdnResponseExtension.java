package com.ausregistry.jtoolkit2.se.idn.ietf;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;


public class DomainInfoIetfIdnResponseExtension extends ResponseExtension {

    private static final String IETF_IDN_PREFIX = ExtendedObjectType.IETF_IDN.getName();

    private final String dataExpr = ResponseExtension.EXTENSION_EXPR
            + "/"
            + IETF_IDN_PREFIX
            + ":RESPONSE_TYPE";

    private boolean initialised;
    private String table;
    private String uname;

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        Node dataNode = xmlDoc.getElement(replaceResponseType(dataExpr, ResponseExtension.DATA));
        if (dataNode != null) {
            initialised = true;
            Node tableNode = xmlDoc.getElement(
                    replaceResponseType(dataExpr + "/" + IETF_IDN_PREFIX + ":table", ResponseExtension.DATA));
            Node unameNode = xmlDoc.getElement(
                    replaceResponseType(dataExpr + "/" + IETF_IDN_PREFIX + ":uname", ResponseExtension.DATA));
            table = tableNode.getTextContent();
            if (unameNode != null) {
                uname = unameNode.getTextContent();
            }
        } else {
            initialised = false;
        }
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getUname() {
        return uname;
    }
    public String getTable() {
        return table;
    }
}
