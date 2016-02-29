package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;

/**
 * Used to access domains as provided in an Domain Variant Info response
 * compliant with the AusRegistry documents 'Variant Extension Domain Mapping
 * for the Extensible Provisioning Protocol' and 'Variant Extension for the
 * Extensible Provisioning Protocol'. Such a service element is sent by an EPP
 * server that supports the variant-1.0 extension, in response to a valid
 * Domain Variant Info command.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainVariantInfoCommand
 */
public class DomainVariantInfoResponse extends DataResponse {

    protected static final String VARIANT_PREFIX = ExtendedObjectType.VARIANT.getName();
    protected static final String VARINFDATA_BASE_EXPR =
            RES_DATA_EXPR + "/" + VARIANT_PREFIX + ":varInfData/" + VARIANT_PREFIX + ":variant";
    protected static final String VARINFDATA_COUNT_EXPR = "count(" + VARINFDATA_BASE_EXPR + ")";
    protected static final String VARINFDATA_IND_EXPR = VARINFDATA_BASE_EXPR + "[IDX]";

    private static final long serialVersionUID = -6265618329673981603L;
    private static final VariantInfoCommandType COMMAND_TYPE = new VariantInfoCommandType();
    private static final String VARIANT_USER_FORM_EXPR = "/@userForm";

    private ArrayList<IdnaDomainVariant> domains;

    public DomainVariantInfoResponse() {
        super(COMMAND_TYPE, ExtendedObjectType.VARIANT);
    }

    public ArrayList<IdnaDomainVariant> getDomains() {
        return domains;
    }

    @Override
    public void fromXML(final XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        domains = new ArrayList<IdnaDomainVariant>();

        try {
            int domainNameCount = xmlDoc.getNodeCount(VARINFDATA_COUNT_EXPR);

            for (int i = 0; i < domainNameCount; i++) {
                final String domainQuery = ReceiveSE.replaceIndex(VARINFDATA_IND_EXPR, i + 1);
                final String domainName = xmlDoc.getNodeValue(domainQuery);
                final String userForm = xmlDoc.getNodeValue(domainQuery + VARIANT_USER_FORM_EXPR);

                domains.add(new IdnaDomainVariant(domainName, userForm));
            }
        } catch (final XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }
}
