package com.ausregistry.jtoolkit2.se;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Use this to access availability data for hosts as provided in an EPP host
 * check response compliant with RFC5730 and RFC5732.  Such a service element is
 * sent by a compliant EPP server in response to a valid host check command,
 * implemented by the HostCheckCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.HostCheckCommand
 */
public class HostCheckResponse extends CheckResponse<String> {

    protected static final String HOS_CHKDATA_COUNT_EXPR = exprReplace(CHKDATA_COUNT_EXPR);
    protected static final String HOS_CHKDATA_IND_EXPR = exprReplace(CHKDATA_IND_EXPR);
    protected static final String HOS_CHKDATA_IDENT_EXPR = exprReplace(CHKDATA_IDENT_EXPR);
    protected static final String HOS_CHKDATA_AVAIL_EXPR = exprReplace(CHKDATA_AVAIL_EXPR);
    protected static final String HOS_CHKDATA_REASON_EXPR = exprReplace(CHKDATA_REASON_EXPR);

    private static final long serialVersionUID = 1708250661983439346L;

    public HostCheckResponse() {
        super(StandardObjectType.HOST);
    }

    protected static String exprReplace(String expr) {
        return expr.replaceAll(OBJ, StandardObjectType.HOST.getName()).replaceAll("IDENT", "name");
    }

    protected String chkDataCountExpr() {
        return HOS_CHKDATA_COUNT_EXPR;
    }

    protected String chkDataIndexExpr() {
        return HOS_CHKDATA_IND_EXPR;
    }

    protected String chkDataTextExpr() {
        return HOS_CHKDATA_IDENT_EXPR;
    }

    protected String chkDataAvailExpr() {
        return HOS_CHKDATA_AVAIL_EXPR;
    }

    protected String chkDataReasonExpr() {
        return HOS_CHKDATA_REASON_EXPR;
    }

    @Override
    protected String getKey(final XMLDocument xmlDoc, final String qry) throws XPathExpressionException {
        return xmlDoc.getNodeValue(qry + chkDataTextExpr());
    }
}

