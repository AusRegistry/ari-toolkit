package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;

/**
 * Use this to retrieve the values of attributes common to all AusRegistry EPP
 * extension response service elements.  Unless there is a specific subclass
 * dedicated to handling a type of response, an instance of this class should
 * be used to handle the response.
 */
public class ArExtensionResponse extends ReceiveSE {
    private static final long serialVersionUID = -2441248857298156911L;

    private static final String RESPONSE_EXPR = "/e:epp/e:extension/arext:response";
    private static final String RESULT_COUNT_EXPR = "count(" + RESPONSE_EXPR + "/arext:result)";
    private static final String RESULT_EXPR = RESPONSE_EXPR + "/arext:result[IDX]";
    private static final String RESULT_CODE_EXPR = "/@code";
    private static final String RESULT_MSG_EXPR = "/arext:msg";
    private static final String RESULT_VALUE_EXPR = "/arext:value";
    private static final String RESULT_XVALUE_EXPR = "/arext:extValue";
    private static final String CLTRID_EXPR = RESPONSE_EXPR + "/arext:trID/arext:clTRID/text()";
    private static final String SVTRID_EXPR = RESPONSE_EXPR + "/arext:trID/arext:svTRID/text()";

    private Result[] resultArray;
    private String clTRID, svTRID;

    public ArExtensionResponse() {
    }

    /**
     * Get as Result instances the /epp/response/results elements contained in
     * the EPP response modelled by this object.
     *
     * @return /epp/response/result/*
     */
    public Result[] getResults() {
        return resultArray;
    }

    /**
     * @return /epp/response/trID/clTRID/text()
     */
    public String getCLTRID() {
        return clTRID;
    }

    /**
     * @return /epp/response/trID/svTRID/text()
     */
    public String getSVTRID() {
        return svTRID;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        debugLogger.finest("enter");

        try {
            int resultCount = xmlDoc.getNodeCount(RESULT_COUNT_EXPR);
            resultArray = new Result[resultCount];

            for (int i = 0; i < resultCount; i++) {
                String qry = ReceiveSE.replaceIndex(RESULT_EXPR, i + 1);
                String code = xmlDoc.getNodeValue(qry + RESULT_CODE_EXPR);
                String msg = xmlDoc.getNodeValue(qry + RESULT_MSG_EXPR);
                NodeList value = xmlDoc.getElements(qry + RESULT_VALUE_EXPR);

                NodeList extValues = xmlDoc.getElements(qry
                        + RESULT_XVALUE_EXPR);
                Node[] valueNodes = null;
                String[] reasons = null;

                if (extValues != null) {
                    int xValCount = extValues.getLength();
                    valueNodes = new Node[xValCount];
                    reasons = new String[xValCount];

                    for (int j = 0; j < xValCount; j++) {
                        Node extValueNode = extValues.item(j);
                        valueNodes[j] = extValueNode.getFirstChild();
                        reasons[j] = extValueNode.getLastChild().getTextContent();
                    }
                }

                resultArray[i] = new Result(Integer.parseInt(code), msg, value,
                        valueNodes, reasons);

                debugLogger.finer(resultArray[i].toString());
            }

            clTRID = xmlDoc.getNodeValue(CLTRID_EXPR);
            svTRID = xmlDoc.getNodeValue(SVTRID_EXPR);
        } catch (XPathExpressionException xpee) {
            String exceptionMessage = xpee.getMessage();
            if (exceptionMessage == null) {
                Throwable cause = xpee.getCause();
                if (cause != null) {
                    exceptionMessage = cause.getMessage();
                } else {
                    exceptionMessage = "Unknown error";
                }
            }
            maintLogger.warning(exceptionMessage);
            userLogger.warning(ErrorPkg.getMessage("Response.fromXML.0", "<<msg>>", exceptionMessage));
        }

        debugLogger.finest("exit");
    }

    @Override
    public String toString() {
        String retval = "(clTRID = " + getCLTRID() + ")(svTRID = "
                + getSVTRID() + ")";
        retval += arrayToString(resultArray, "\n");

        return retval;
    }
}
