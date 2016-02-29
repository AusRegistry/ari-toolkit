package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;

/**
 * Use this to access host object information as provided in an EPP host
 * info response compliant with RFC5730 and RFC5732.  Such a service element is
 * sent by a compliant EPP server in response to a valid host info command,
 * implemented by the HostInfoCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.HostInfoCommand
 */
public class HostInfoResponse extends InfoResponse {
    private static final long serialVersionUID = 7217980955196329241L;

    private static final String HOS_ROID_EXPR = exprReplace(ROID_EXPR);
    private static final String HOS_CR_ID_EXPR = exprReplace(CR_ID_EXPR);
    private static final String HOS_UP_ID_EXPR = exprReplace(UP_ID_EXPR);
    private static final String HOS_CL_ID_EXPR = exprReplace(CL_ID_EXPR);
    private static final String HOS_CR_DATE_EXPR = exprReplace(CR_DATE_EXPR);
    private static final String HOS_UP_DATE_EXPR = exprReplace(UP_DATE_EXPR);
    private static final String HOS_TR_DATE_EXPR = exprReplace(TR_DATE_EXPR);
    private static final String HOS_STATUS_COUNT_EXPR = exprReplace(STATUS_COUNT_EXPR);
    private static final String HOS_STATUS_EXPR = exprReplace(STATUS_EXPR);

    private static final String HOS_INF_DATA_EXPR = exprReplace(INF_DATA_EXPR);
    private static final String HOS_NAME_EXPR = HOS_INF_DATA_EXPR + "/host:name/text()";
    private static final String HOS_ADDR_EXPR = HOS_INF_DATA_EXPR + "/host:addr[IDX]";
    private static final String HOS_ADDR_COUNT_EXPR = "count(" + HOS_INF_DATA_EXPR + "/host:addr)";
    private static final String HOS_ADDR_TXT_EXPR = "/text()";
    private static final String HOS_ADDR_IP_EXPR = "/@ip";

    private String name;
    private InetAddress[] addresses;

    public HostInfoResponse() {
        super(StandardObjectType.HOST);
    }

    protected String roidExpr() {
        return HOS_ROID_EXPR;
    }

    protected String crIDExpr() {
        return HOS_CR_ID_EXPR;
    }

    protected String upIDExpr() {
        return HOS_UP_ID_EXPR;
    }

    protected String clIDExpr() {
        return HOS_CL_ID_EXPR;
    }

    protected String crDateExpr() {
        return HOS_CR_DATE_EXPR;
    }

    protected String upDateExpr() {
        return HOS_UP_DATE_EXPR;
    }

    protected String trDateExpr() {
        return HOS_TR_DATE_EXPR;
    }

    protected String statusExpr() {
        return HOS_STATUS_EXPR;
    }

    protected String statusCountExpr() {
        return HOS_STATUS_COUNT_EXPR;
    }

    protected static String exprReplace(String expr) {
        return expr.replaceAll(OBJ,
                StandardObjectType.HOST.getName());
    }

    public String getName() {
        return name;
    }

    public InetAddress[] getAddresses() {
        return addresses;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        debugLogger.finest("enter");
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            name = xmlDoc.getNodeValue(HOS_NAME_EXPR);

            int addrCount = xmlDoc.getNodeCount(HOS_ADDR_COUNT_EXPR);
            addresses = new InetAddress[addrCount];
            for (int i = 0; i < addrCount; i++) {
                String qry = ReceiveSE.replaceIndex(HOS_ADDR_EXPR, i + 1);
                String addr = xmlDoc.getNodeValue(qry + HOS_ADDR_TXT_EXPR);
                String version = xmlDoc.getNodeValue(qry + HOS_ADDR_IP_EXPR);
                addresses[i] = new InetAddress(IPVersion.value(version), addr);
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }

        debugLogger.finest("exit");
    }
}

