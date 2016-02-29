package com.ausregistry.jtoolkit2.se;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Use this to access availability data for contacts as provided in an EPP
 * contact check response compliant with RFC5730 and RFC5733.  Such a service
 * element is sent by a compliant EPP server in response to a valid contact
 * check command, implemented by the ContactCheckCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.ContactCheckCommand
 */
public class ContactCheckResponse extends CheckResponse<String> {
    private static final long serialVersionUID = -8516482858545087664L;

    private static final String CON_CHKDATA_COUNT_EXPR = exprReplace(CHKDATA_COUNT_EXPR);
    private static final String CON_CHKDATA_IND_EXPR = exprReplace(CHKDATA_IND_EXPR);
    private static final String CON_CHKDATA_IDENT_EXPR = exprReplace(CHKDATA_IDENT_EXPR);
    private static final String CON_CHKDATA_AVAIL_EXPR = exprReplace(CHKDATA_AVAIL_EXPR);
    private static final String CON_CHKDATA_REASON_EXPR = exprReplace(CHKDATA_REASON_EXPR);

    public ContactCheckResponse() {
        super(StandardObjectType.CONTACT);
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);
    }

    @Override
    protected String chkDataCountExpr() {
        return CON_CHKDATA_COUNT_EXPR;
    }

    @Override
    protected String chkDataIndexExpr() {
        return CON_CHKDATA_IND_EXPR;
    }

    @Override
    protected String chkDataTextExpr() {
        return CON_CHKDATA_IDENT_EXPR;
    }

    @Override
    protected String chkDataAvailExpr() {
        return CON_CHKDATA_AVAIL_EXPR;
    }

    @Override
    protected String chkDataReasonExpr() {
        return CON_CHKDATA_REASON_EXPR;
    }

    @Override
    protected String getKey(final XMLDocument xmlDoc, final String qry) throws XPathExpressionException {
        return xmlDoc.getNodeValue(qry + chkDataTextExpr());
    }

    private static String exprReplace(String expr) {
        return expr.replaceAll(
                OBJ, StandardObjectType.CONTACT.getName()
        ).replaceAll(
                "IDENT", "id");
    }
}

