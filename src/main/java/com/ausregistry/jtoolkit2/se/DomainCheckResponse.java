package com.ausregistry.jtoolkit2.se;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Use this to access availability data for domains as provided in an EPP
 * domain check response compliant with RFC5730 and RFC5731.  Such a service
 * element is sent by a compliant EPP server in response to a valid domain
 * check command, implemented by the DomainCheckCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCheckCommand
 */
public class DomainCheckResponse extends CheckResponse<String> {

    private static final long serialVersionUID = -7501698464402166104L;
    private static final String DOM_CHKDATA_COUNT_EXPR = exprReplace(CHKDATA_COUNT_EXPR);
    private static final String DOM_CHKDATA_IND_EXPR = exprReplace(CHKDATA_IND_EXPR);
    private static final String DOM_CHKDATA_IDENT_EXPR = exprReplace(CHKDATA_IDENT_EXPR);
    private static final String DOM_CHKDATA_AVAIL_EXPR = exprReplace(CHKDATA_AVAIL_EXPR);
    private static final String DOM_CHKDATA_REASON_EXPR = exprReplace(CHKDATA_REASON_EXPR);

    public DomainCheckResponse() {
        super(StandardObjectType.DOMAIN);
    }

    protected String chkDataCountExpr() {
        return DOM_CHKDATA_COUNT_EXPR;
    }

    protected String chkDataIndexExpr() {
        return DOM_CHKDATA_IND_EXPR;
    }

    protected String chkDataTextExpr() {
        return DOM_CHKDATA_IDENT_EXPR;
    }

    protected String chkDataAvailExpr() {
        return DOM_CHKDATA_AVAIL_EXPR;
    }

    protected String chkDataReasonExpr() {
        return DOM_CHKDATA_REASON_EXPR;
    }

    @Override
    protected String getKey(final XMLDocument xmlDoc, final String qry) throws XPathExpressionException {
        return xmlDoc.getNodeValue(qry + chkDataTextExpr());
    }

    private static String exprReplace(String expr) {
        return expr.replaceAll(
                OBJ, StandardObjectType.DOMAIN.getName()
        ).replaceAll(
                "IDENT", "name");
    }

}

