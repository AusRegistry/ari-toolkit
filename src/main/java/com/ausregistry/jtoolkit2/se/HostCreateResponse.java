package com.ausregistry.jtoolkit2.se;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Use this to access create data for a host as provided in an EPP host create response compliant with RFC5730 and
 * RFC5732. Such a service element is sent by a compliant EPP server in response to a valid host create command,
 * implemented by the HostCreateCommand.
 *
 * @see com.ausregistry.jtoolkit2.se.HostCreateCommand
 */
public class HostCreateResponse extends CreateResponse {
    private static final long serialVersionUID = -8243656728549488950L;

    private static final String HOS_CR_DATE_EXPR = exprReplace(CR_DATE_EXPR);

    private static final String HOS_NAME_EXPR = exprReplace(CRE_DATA_EXPR) + "/host:name/text()";

    private String name;

    public HostCreateResponse() {
        super(StandardObjectType.HOST);
    }

    @Override
    protected String crDateExpr() {
        return HOS_CR_DATE_EXPR;
    }

    protected static String exprReplace(String expr) {
        return expr.replaceAll(OBJ, StandardObjectType.HOST.getName());
    }

    public String getName() {
        return name;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            name = xmlDoc.getNodeValue(HOS_NAME_EXPR);
        } catch (final XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }

}
