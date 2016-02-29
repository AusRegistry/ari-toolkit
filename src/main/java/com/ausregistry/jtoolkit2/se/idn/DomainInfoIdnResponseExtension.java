package com.ausregistry.jtoolkit2.se.idn;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Extension for the EPP Domain Info response, representing the IDN Info aspect of the Internationalized Domain
 * Name extension.</p>
 *
 * <p>Use this to access the language tag for an IDN as provided in an EPP Domain Info response compliant
 * with RFC5730 and RFC5731. Such a service element is sent by a compliant EPP server in response to a valid
 * Domain Info command.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 * @see com.ausregistry.jtoolkit2.se.DomainInfoResponse
 * @see <a href="http://ausregistry.github.io/doc/idn-1.0/idn-1.0.html">Internationalized Domain Name Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public final class DomainInfoIdnResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -2441248857298156911L;

    private static final String IDN_PREFIX = ExtendedObjectType.IDN.getName();

    private static final String LANGUAGE_EXPR = ResponseExtension.EXTENSION_EXPR
            + "/"
            + IDN_PREFIX
            + ":RESPONSE_TYPE/"
            + IDN_PREFIX
            + ":languageTag/text()";

    private String languageTag;
    private boolean initialised;

    /**
     * @see com.ausregistry.jtoolkit2.se.ResponseExtension#fromXML(com.ausregistry.jtoolkit2.xml.XMLDocument)
     */
    public void fromXML(final XMLDocument xmlDoc)
            throws XPathExpressionException {
        languageTag = xmlDoc.getNodeValue(replaceResponseType(
                LANGUAGE_EXPR, ResponseExtension.INFO));

        initialised = (languageTag != null);
    }

    public String getLanguageTag() {
        return languageTag;
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }
}
