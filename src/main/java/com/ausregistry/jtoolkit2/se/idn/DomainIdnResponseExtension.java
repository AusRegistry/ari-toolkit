package com.ausregistry.jtoolkit2.se.idn;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;

/**
 * Extension of the domain mapping of the EPP info response, to IDN domain names, 
 * the specification of which are in the XML schema definition urn:rbp:params:xml:ns:idn-1.0.
 * 
 * Instances of this class provide an interface to access info data
 * for an IDN domain. This relies on the instance first being initialised 
 * by a suitable EPP domain info response using the method fromXML. 
 * Such a service element is sent by a EPP server in response to a valid 
 * domain info command as implemented by the DomainInfoCommand.
 * 
 * For flexibility, this implementation extracts the data from the response
 * using XPath queries, the expressions for which are defined statically.
 * 
 * @see com.ausregistry.jtoolkit2.se.ResponseExtension
 */
public final class DomainIdnResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -2441248857298156911L;

    private static final String IDN_PREFIX = ExtendedObjectType.IDN.getName();

    private static final String LANGUAGE_EXPR = ResponseExtension.EXTENSION_EXPR
            + "/"
            + IDN_PREFIX
            + ":RESPONSE_TYPE/"
            + IDN_PREFIX
            + ":languageTag/text()";
    
    private String languageTag;
    private String responseType;
    private boolean initialised;

    public DomainIdnResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    /**
     * @see com.ausregistry.jtoolkit2.se.ResponseExtension#fromXML(com.ausregistry.jtoolkit2.xml.XMLDocument)
     */
    public void fromXML(final XMLDocument xmlDoc)
            throws XPathExpressionException {
        languageTag = xmlDoc.getNodeValue(replaceResponseType(
                LANGUAGE_EXPR, responseType));
        
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
