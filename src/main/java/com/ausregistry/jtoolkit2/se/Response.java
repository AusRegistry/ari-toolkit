package com.ausregistry.jtoolkit2.se;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Use this to retrieve the values of attributes common to all EPP response service elements. Unless there is a specific
 * subclass dedicated to handling a type of response, an instance of this class should be used to handle the response.
 * The commands which result in a response that should be handled by this class are LoginCommand, LogoutCommand,
 * subclasses of DeleteCommand and subclasses of UpdateCommand.
 *
 * @see com.ausregistry.jtoolkit2.se.LoginCommand
 * @see com.ausregistry.jtoolkit2.se.LogoutCommand
 * @see com.ausregistry.jtoolkit2.se.DeleteCommand
 * @see com.ausregistry.jtoolkit2.se.UpdateCommand
 */
public class Response extends ReceiveSE {

    protected static final String RESPONSE_EXPR = "/e:epp/e:response";

    private static final long serialVersionUID = -2441248857298156911L;

    private static final String DEFAULT_LANGUAGE = "en";

    private static final String RESULT_COUNT_EXPR = "count(" + RESPONSE_EXPR + "/e:result)";

    private static final String RESULT_EXPR = RESPONSE_EXPR + "/e:result[IDX]";

    private static final String RESULT_CODE_EXPR = "/@code";

    private static final String RESULT_MSG_EXPR = "/e:msg";

    private static final String RESULT_VALUE_EXPR = "/e:value";

    private static final String RESULT_XVALUE_EXPR = "/e:extValue";

    private static final String MSGQ_COUNT_EXPR = RESPONSE_EXPR + "/e:msgQ/@count";

    private static final String MSGQ_ID_EXPR = RESPONSE_EXPR + "/e:msgQ/@id";

    private static final String MSGQ_QDATE_EXPR = RESPONSE_EXPR + "/e:msgQ/e:qDate/text()";

    private static final String MSGQ_MSG_EXPR = RESPONSE_EXPR + "/e:msgQ/e:msg/text()";

    private static final String MSGQ_MSG_LANG_EXPR = RESPONSE_EXPR + "/e:msgQ/e:msg/@lang";

    private static final String CLTRID_EXPR = RESPONSE_EXPR + "/e:trID/e:clTRID/text()";

    private static final String SVTRID_EXPR = RESPONSE_EXPR + "/e:trID/e:svTRID/text()";

    protected Result[] resultArray;

    private String clTRID, svTRID;

    private int msgCount;

    private String msgID;

    private GregorianCalendar qDate;

    private String msg;

    private String msgLang = DEFAULT_LANGUAGE;

    private XMLDocument xmlDoc;

    private final List<ResponseExtension> extensions;

    public Response() {
        extensions = new Vector<ResponseExtension>(1);
    }

    /**
     * Get as Result instances the /epp/response/results elements contained in the EPP response modelled by this object.
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

    /**
     * @return /epp/response/msgQ/qDate/text()
     */
    public GregorianCalendar getMessageEnqueueDate() {
        return qDate;
    }

    /**
     * @return /epp/response/msgQ/msg/text()
     */
    public String getMessage() {
        return msg;
    }

    /**
     * @return /epp/response/msgQ/msg/@lang/text()
     */
    public String getMessageLanguage() {
        return msgLang;
    }

    /**
     * @return /epp/response/msgQ/@count/text()
     */
    public int getMsgCount() {
        return msgCount;
    }

    /**
     * @return /epp/response/msgQ/@id/text()
     */
    public String getMsgID() {
        return msgID;
    }

    @Override
    public void fromXML(final XMLDocument xmlDocArg) {
        debugLogger.finest("enter");
        xmlDoc = xmlDocArg;

        try {
            final int resultCount = xmlDoc.getNodeCount(RESULT_COUNT_EXPR);
            resultArray = new Result[resultCount];

            for (int i = 0; i < resultCount; i++) {
                final String qry = ReceiveSE.replaceIndex(RESULT_EXPR, i + 1);
                final String code = xmlDoc.getNodeValue(qry + RESULT_CODE_EXPR);
                final String message = xmlDoc.getNodeValue(qry + RESULT_MSG_EXPR);
                final NodeList value = xmlDoc.getElements(qry + RESULT_VALUE_EXPR);

                final NodeList extValues = xmlDoc.getElements(qry + RESULT_XVALUE_EXPR);
                Node[] valueNodes = null;
                String[] reasons = null;

                if (extValues != null) {
                    final int xValCount = extValues.getLength();
                    valueNodes = new Node[xValCount];
                    reasons = new String[xValCount];

                    for (int j = 0; j < xValCount; j++) {
                        final Node extValueNode = extValues.item(j);
                        valueNodes[j] = extValueNode.getFirstChild();
                        reasons[j] = extValueNode.getLastChild().getTextContent();
                    }
                }

                resultArray[i] = new Result(Integer.parseInt(code), message, value, valueNodes, reasons);

                debugLogger.finer(resultArray[i].toString());
            }

            final String msgQcount = xmlDoc.getNodeValue(MSGQ_COUNT_EXPR);
            if (msgQcount != null && msgQcount.length() > 0) {
                msgCount = Integer.parseInt(msgQcount);
            }

            msgID = xmlDoc.getNodeValue(MSGQ_ID_EXPR);

            final String msgQqDate = xmlDoc.getNodeValue(MSGQ_QDATE_EXPR);
            if (msgQqDate != null && msgQqDate.length() > 0) {
                qDate = EPPDateFormatter.fromXSDateTime(msgQqDate);
            }

            msg = xmlDoc.getNodeValue(MSGQ_MSG_EXPR);
            msgLang = xmlDoc.getNodeValue(MSGQ_MSG_LANG_EXPR);

            clTRID = xmlDoc.getNodeValue(CLTRID_EXPR);
            svTRID = xmlDoc.getNodeValue(SVTRID_EXPR);

            for (ResponseExtension extension : extensions) {
                extension.fromXML(xmlDoc);
            }
        } catch (final XPathExpressionException xpee) {
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

    public void registerExtension(final ResponseExtension extension) {
        extensions.add(extension);
    }

    @Override
    public String toString() {
        String retval = "(msgCount = " + getMsgCount() + ")(msgID = " + getMsgID() + ")(msg = " + msg
        + ")(qDate = " + getMessageEnqueueDate() + ")(clTRID = " + getCLTRID() + ")(svTRID = " + getSVTRID() + ")";
        retval += arrayToString(resultArray, "\n");

        return retval;
    }

    public String getSourceXMLString() {
        return xmlDoc.getSourceXMLString();
    }

}
