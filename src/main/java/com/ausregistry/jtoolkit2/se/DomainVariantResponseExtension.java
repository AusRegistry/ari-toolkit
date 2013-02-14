package com.ausregistry.jtoolkit2.se;

import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Extension of the domain mapping of the EPP create/info response, as defined
 * in RFC5730 and RFC5731, to domain name variants, the specification of which
 * are in the XML schema definition urn:X-ar:params:xml:ns:variant-1.0.
 * 
 * Instances of this class provide an interface to access create and info data
 * for a domain as provided in an EPP domain create response. This relies on the
 * instance first being initialised by a suitable EPP domain create/info
 * response using the method fromXML. Such a service element is sent by a EPP
 * server in response to a valid domain create/info command as implemented by
 * the DomainCreateCommand and DomainInfoCommand classes, optionally with the
 * variant extension as implemented by the DomainInfoVariantsCommandExtension
 * class.
 * 
 * For flexibility, this implementation extracts the data from the response
 * using XPath queries, the expressions for which are defined statically.
 * 
 * @see com.ausregistry.jtoolkit2.se.ResponseExtension
 */
public final class DomainVariantResponseExtension extends
        ResponseExtension {

    private static final long serialVersionUID = -2441248857298156911L;

    private static final String VARIANT_PREFIX = ExtendedObjectType.VARIANT.getName();

    private static final String VARIANT_LIST_EXPR = ResponseExtension.EXTENSION_EXPR
            + "/"
            + VARIANT_PREFIX
            + ":RESPONSE_TYPE/"
            + VARIANT_PREFIX
            + ":variant";
    private static final String VARIANT_COUNT_EXPR = "count("
            + VARIANT_LIST_EXPR + ")";
    private static final String VARIANT_IND_EXPR = VARIANT_LIST_EXPR + "[IDX]";
    private static final String VARIANT_USER_FORM_EXPR = "/@userForm";

    private ArrayList<IdnaDomainVariant> variants;

    private String responseType;
    private boolean initialised;

    public DomainVariantResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    /**
     * @see com.ausregistry.jtoolkit2.se.ResponseExtension#fromXML(XMLDocument)
     */
    public void fromXML(final XMLDocument xmlDoc)
            throws XPathExpressionException {
        variants = null;

        final int variantCount = xmlDoc.getNodeCount(replaceResponseType(
                VARIANT_COUNT_EXPR, responseType));
        final String indexExpression = replaceResponseType(VARIANT_IND_EXPR, responseType);

        variants = new ArrayList<IdnaDomainVariant>();

        for (int i = 0; i < variantCount; i++) {
            final String query = ReceiveSE.replaceIndex(indexExpression, i + 1);
            final String domainName = xmlDoc.getNodeValue(query);
            final String userForm = xmlDoc.getNodeValue(query + VARIANT_USER_FORM_EXPR);
            
            variants.add(new IdnaDomainVariant(domainName, userForm));
        }

        initialised = (variantCount > 0);
    }

    public ArrayList<IdnaDomainVariant> getVariants() {
        return variants;
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }
}
