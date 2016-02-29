package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;

/**
 * Use this to access contact object transfer information as provided in an EPP
 * contact transfer response compliant with RFC5730 and RFC5733.  Such a service
 * element is sent by a compliant EPP server in response to a valid contact
 * transfer command, implemented by a subclass of the ContactTransferCommand
 * class.
 *
 * @see com.ausregistry.jtoolkit2.se.ContactTransferCommand
 * @see com.ausregistry.jtoolkit2.se.ContactTransferRequestCommand
 * @see com.ausregistry.jtoolkit2.se.ContactTransferApproveCommand
 * @see com.ausregistry.jtoolkit2.se.ContactTransferCancelCommand
 * @see com.ausregistry.jtoolkit2.se.ContactTransferRejectCommand
 * @see com.ausregistry.jtoolkit2.se.ContactTransferQueryCommand
 */
public class ContactTransferResponse extends TransferResponse {
    private static final long serialVersionUID = -3303074732303130291L;

    private static final String CON_ID_EXPR = RES_DATA_EXPR + "/contact:trnData/contact:id/text()";
    private static final String CON_TR_STATUS_EXPR = exprReplace(TR_STATUS_EXPR);
    private static final String CON_REID_EXPR = exprReplace(REID_EXPR);
    private static final String CON_REDATE_EXPR = exprReplace(REDATE_EXPR);
    private static final String CON_ACID_EXPR = exprReplace(ACID_EXPR);
    private static final String CON_ACDATE_EXPR = exprReplace(ACDATE_EXPR);

    private String id;

    public ContactTransferResponse() {
        super(StandardObjectType.CONTACT);
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

    protected String trStatusExpr() {
        return CON_TR_STATUS_EXPR;
    }

    protected String reIDExpr() {
        return CON_REID_EXPR;
    }

    protected String reDateExpr() {
        return CON_REDATE_EXPR;
    }

    protected String acIDExpr() {
        return CON_ACID_EXPR;
    }

    protected String acDateExpr() {
        return CON_ACDATE_EXPR;
    }
}

