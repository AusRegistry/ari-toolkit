package com.ausregistry.jtoolkit2.se;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.xml.XMLDocument;


/**
 * Extension of the response mapping of the EPP response. Instances of this
 * class provide an interface to access all of the information available through
 * EPP response extension. This relies on the instance first being initialised
 * by a suitable EPP response using the method fromXML. For flexibility, this
 * implementation extracts the data from the response using XPath queries, the
 * expressions for which are defined statically.
 */
public abstract class ResponseExtension implements java.io.Serializable {

    public static final String CREATE = "creData";
    public static final String INFO = "infData";
    public static final String UPDATE = "upData";
    public static final String RENEW = "renData";
    public static final String DATA = "data";
    public static final String TRANSFER = "trnData";
    public static final String CHK_DATA = "chkData";

    /**
     * XPath expression to locate the extension element from the EPP response.
     */
    protected static final String EXTENSION_EXPR = "/e:epp/e:response/e:extension";

    private static final long serialVersionUID = -9214377057865043563L;

    protected static String replaceResponseType(final String expr, String responseType) {
        return expr.replaceAll("RESPONSE_TYPE", responseType);
    }

    /**
     * Initialises the instance from the given XML document.
     * @param xmlDoc EPP Response XML
     * @throws XPathExpressionException
     */
    public abstract void fromXML(XMLDocument xmlDoc) throws XPathExpressionException;

    /**
     * Indicates whether fromXML() completed successfully and the extension was
     * successfully initialised from the EPP response.
     *
     * @return true if the extension has been initialised, else false.
     */
    public abstract boolean isInitialised();
}
