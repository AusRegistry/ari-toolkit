package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;

/**
 * Use this to access create data for a contact as provided in an EPP contact
 * create response compliant with RFC5730 and RFC5733.  Such a service element
 * is sent by a compliant EPP server in response to a valid contact create
 * command, implemented by the ContactCreateCommand.
 *
 * @see com.ausregistry.jtoolkit2.se.ContactCreateCommand
 */
public class ContactCreateResponse extends CreateResponse {
    private static final long serialVersionUID = -5714257489160996356L;
    private static final String CON_CR_DATE_EXPR = exprReplace(CR_DATE_EXPR);
    private static final String CON_ID_EXPR = exprReplace(CRE_DATA_EXPR) + "/contact:id/text()";

    private String id;

    public ContactCreateResponse() {
        super(StandardObjectType.CONTACT);
    }

    protected String crDateExpr() {
        return CON_CR_DATE_EXPR;
    }

    protected static String exprReplace(String expr) {
        return expr.replaceAll(OBJ,
                StandardObjectType.CONTACT.getName());
    }

    public String getID() {
        return id;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            id = xmlDoc.getNodeValue(CON_ID_EXPR);
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }
}

