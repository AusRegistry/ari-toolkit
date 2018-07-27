package com.ausregistry.jtoolkit2.se;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ausregistry.jtoolkit2.xml.XMLBuilderSJSXP;
import com.ausregistry.jtoolkit2.xml.XmlOutputConfig;

/**
 * This class models the result element of EPP responses. From RFC5730: <blockquote> One or more {@code <result>}
 * elements [...] document the success or failure of command execution. If the command was processed successfully, only
 * one {@code <result>} element MUST be returned. If the command was not processed successfully, multiple
 * {@code <result>} elements MAY be returned to document failure conditions. Each {@code <result>} element contains the
 * following attribute and child elements. </blockquote> See getX method descriptions for a description of the elements
 * of a Result. Note that any DOM Node fields will not be serialized.
 */
public class Result implements java.io.Serializable {
    private static final long serialVersionUID = 1590539701808318231L;

    private final int resultCode;
    private final String resultMessage;
    private String resultMessageLang;
    private transient NodeList resultValues;
    private transient Node[] resultExtvalueValues;
    private final String[] resultExtvalueReasons;

    Result(int code, String msg, NodeList values, Node[] extValueValues, String[] valueReasons) {

        resultCode = code;
        resultMessage = msg;
        resultValues = values;
        resultExtvalueValues = extValueValues;
        resultExtvalueReasons = valueReasons;
    }

    Result(int code, String msg, String msgLang, NodeList values, Node[] extValueValues, String[] valueReasons) {

        this(code, msg, values, extValueValues, valueReasons);
        resultMessageLang = msgLang;
    }

    /**
     * The code attribute of a result. From RFC5730: <blockquote> A "code" attribute whose value is a four-digit,
     * decimal number that describes the success or failure of the command. </blockquote>
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * The msg element of a result. From RFC5730: <blockquote> A {@code <msg>} element containing a human-readable
     * description of the response code. </blockquote>
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * The lang attribute of the msg element of a result. From RFC5730: <blockquote> The language of the response is
     * identified via an OPTIONAL "lang" attribute. If not specified, the default attribute value MUST be "en"
     * (English). </blockquote>
     */
    public String getResultMessageLanguage() {
        return resultMessageLang;
    }

    /**
     * The value child elements of the extValue element of a result. From RFC5730: <blockquote> Zero or more OPTIONAL
     * {@code &lt;extValue&gt;} elements that can be used to provide additional error diagnostic information, including:
     * <br>
     * - A @{code &lt;value&gt;} element that identifies a client-provided element (including XML tag and value) that
     * caused a server error condition. </blockquote>
     */
    public Node[] getResultExtValueValue() {
        return resultExtvalueValues;
    }

    /**
     * The reason child elements of the extValue element of a result. From RFC5730: <blockquote> Zero or more OPTIONAL
     * {@code &lt;extValue&gt;} elements that can be used to provide additional error diagnostic information, including:
     * <br>
     * - A {@code &lt;reason&gt;} element containing a human-readable message that describes the reason for the error.
     * </blockquote> TODO provide interface to get language of each reason.
     */
    public String[] getResultExtValueReason() {
        return resultExtvalueReasons;
    }

    public String[] getValuesAsText() {
        if (resultExtvalueValues == null && resultValues == null) {
            return null;
        }

        String[] valueText;

        if (resultValues != null) {
            valueText = new String[resultValues.getLength()];
            for (int i = 0; i < valueText.length; i++) {
                valueText[i] = toXML(resultValues.item(i));
            }
        } else {
            valueText = new String[resultExtvalueValues.length];

            for (int i = 0; i < valueText.length; i++) {
                valueText[i] = toXML(resultExtvalueValues[i]);
            }
        }

        return valueText;
    }

    /**
     * Indicate whether the result has any resultValue elements. This should be used to determine whether a call to
     * getResultValue will return any nodes. If this returns false, getResultValue will return null.
     */
    public boolean hasResultValues() {
        return (resultValues != null);
    }

    /**
     * Indicate whether the result has any extValue/reason elements. This should be used to determine whether a call to
     * getResult will return any nodes. If this returns false, getResultValue will return null.
     */
    public boolean hasResultExtReasons() {
        return (resultExtvalueReasons != null);
    }

    private String toXML(Node valueNode) {
        XMLBuilderSJSXP xmlBuilder = new XMLBuilderSJSXP();
        return xmlBuilder.partialToXML(valueNode, XmlOutputConfig.defaultConfig());
    }

    /**
     * The value elements of a result. From RFC5730: <blockquote> Zero or more OPTIONAL {@code <value>} elements that
     * identify a client-provided element (including XML tag and value) that caused a server error condition.
     * </blockquote>
     */
    public NodeList getResultValue() {
        return resultValues;
    }

    /**
     * Return the text content of the resultValue element at the specified index.
     */
    public String getResultExtReason(int nodeIndex) {
        if (resultExtvalueReasons == null || nodeIndex > resultExtvalueReasons.length) {
            return null;
        }

        if (hasResultExtReasons()) {
            return resultExtvalueReasons[nodeIndex];
        } else {
            return null;
        }
    }

    /**
     * Whether the associated command succeeded or not. This can be used to reduce the amount of result checking, since
     * if this returns true, then no further results should be available for the associated response.
     */
    public boolean succeeded() {
        return (resultCode >= 1000 && resultCode < 2000);
    }

    @Override
    public String toString() {
        String reasons = "(extValue/reasons = ";

        if (resultExtvalueReasons != null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < resultExtvalueReasons.length; i++) {
                builder.append("(reason = ");
                builder.append(resultExtvalueReasons[i]);
                builder.append(")");
            }
            reasons += builder.toString();
        }
        reasons += ")";

        return "(result = (code = " + resultCode + ")(msg = " + resultMessage + ")" + reasons + ")";
    }
}
