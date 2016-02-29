package com.ausregistry.jtoolkit2.se;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Extension for the EPP Domain Create and Domain Info response, representing the Variant Info
 * aspect of the Domain Name Variant extension (v1.0).</p>
 *
 * <p>Use this to access a list of the activated variants for a domain as provided in an EPP Domain Info or
 * Domain Create response compliant with RFC5730 and RFC5731. Such a service element is sent by a compliant
 * EPP server in response to a valid Domain Info or Domain Create command with the Domain Info Variant
 * Command extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.variant.DomainUpdateVariantCommandExtension
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
     * @see ResponseExtension#fromXML(com.ausregistry.jtoolkit2.xml.XMLDocument)
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
