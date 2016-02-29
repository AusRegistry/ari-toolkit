package com.ausregistry.jtoolkit2.se;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Extension for the EPP Domain Create and Domain Info response, representing the Variant Info
 * aspect of the Domain Name Variant extension (v1.1).</p>
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
 * @see <a href="http://ausregistry.github.io/doc/variant-1.1/variant-1.1.html">Domain Name Variant Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public final class DomainVariantResponseExtensionV1_1 extends
        ResponseExtension {

    private static final long serialVersionUID = -2441248857298156911L;

    private static final String VARIANT_PREFIX = ExtendedObjectType.VARIANT_V1_1.getName();

    private static final String VARIANT_LIST_EXPR = ResponseExtension.EXTENSION_EXPR
            + "/"
            + VARIANT_PREFIX
            + ":RESPONSE_TYPE/"
            + VARIANT_PREFIX
            + ":variant";
    private static final String VARIANT_COUNT_EXPR = "count("
            + VARIANT_LIST_EXPR + ")";
    private static final String VARIANT_IND_EXPR = VARIANT_LIST_EXPR + "[IDX]";

    private ArrayList<IdnDomainVariant> variants;

    private String responseType;
    private boolean initialised;

    public DomainVariantResponseExtensionV1_1(String responseType) {
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

        variants = new ArrayList<IdnDomainVariant>();

        for (int i = 0; i < variantCount; i++) {
            final String query = ReceiveSE.replaceIndex(indexExpression, i + 1);
            final String domainName = xmlDoc.getNodeValue(query);

            variants.add(new IdnDomainVariant(domainName));
        }

        initialised = (variantCount > 0);
    }

    public ArrayList<IdnDomainVariant> getVariants() {
        return variants;
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }
}
