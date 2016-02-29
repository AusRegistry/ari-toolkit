package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;

/**
 * Extension of the domain mapping of the EPP create/info response, as defined
 * in RFC5730 and RFC5731, to IDN domain names, the specification of which are
 * in the XML schema definition urn:X-ar:params:xml:ns:idnadomain-1.0.
 *
 * Instances of this class provide an interface to access create and info data
 * for an IDN domain as provided in an EPP domain create response. This relies
 * on the instance first being initialised by a suitable EPP domain create/info
 * response using the method fromXML. Such a service element is sent by a EPP
 * server in response to a valid domain create/info command as implemented by
 * the DomainCreateCommand and DomainInfoCommand classes, with IDN extensions as
 * implemented by IdnaDomainCreateCommandExtension and
 * IdnaDomainInfoCommandExtension classes.
 *
 * For flexibility, this implementation extracts the data from the response
 * using XPath queries, the expressions for which are defined statically.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateIdnaCommandExtension
 * @see com.ausregistry.jtoolkit2.se.ResponseExtension
 */
public final class DomainIdnaResponseExtension extends
        ResponseExtension {

    private static final long serialVersionUID = -2441248857298156911L;

    private static final String IDNA_DOMAIN_PREFIX = ExtendedObjectType.IDNA_DOMAIN.getName();

    private static final String USER_FORM_EXPR = ResponseExtension.EXTENSION_EXPR
            + "/"
            + IDNA_DOMAIN_PREFIX
            + ":RESPONSE_TYPE/"
            + IDNA_DOMAIN_PREFIX
            + ":userForm/text()";
    private static final String LANGUAGE_EXPR = ResponseExtension.EXTENSION_EXPR
            + "/"
            + IDNA_DOMAIN_PREFIX
            + ":RESPONSE_TYPE/"
            + IDNA_DOMAIN_PREFIX
            + ":userForm/@language";
    private static final String CANONICAL_FORM_EXPR = ResponseExtension.EXTENSION_EXPR
            + "/"
            + IDNA_DOMAIN_PREFIX
            + ":RESPONSE_TYPE/"
            + IDNA_DOMAIN_PREFIX
            + ":canonicalForm/text()";

    private String userFormName;
    private String canonicalForm;
    private String language;
    private String responseType;
    private boolean initialised;

    public DomainIdnaResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    /**
     * @see com.ausregistry.jtoolkit2.se.ResponseExtension#fromXML(com.ausregistry.jtoolkit2.xml.XMLDocument)
     */
    public void fromXML(final XMLDocument xmlDoc)
            throws XPathExpressionException {
        userFormName = xmlDoc.getNodeValue(replaceResponseType(
                USER_FORM_EXPR, responseType));
        language = xmlDoc.getNodeValue(replaceResponseType(
                LANGUAGE_EXPR, responseType));
        canonicalForm = xmlDoc.getNodeValue(replaceResponseType(
                CANONICAL_FORM_EXPR, responseType));

        initialised = (userFormName != null && canonicalForm != null);
    }


    public String getUserFormName() {
        return userFormName;
    }

    public String getLanguage() {
        return language;
    }

    public String getCanonicalForm() {
        return canonicalForm;
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }
}
