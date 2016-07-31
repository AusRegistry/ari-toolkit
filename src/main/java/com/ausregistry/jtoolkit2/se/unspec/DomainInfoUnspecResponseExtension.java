package com.ausregistry.jtoolkit2.se.unspec;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import javax.xml.xpath.XPathExpressionException;

/**
 * <p>Representation of the EPP Contact Info response for the Neulevel Unspec Extension.</p>
 *
 * <p>Use this to get information about a domain.
 * Such a service element is sent by a compliant EPP server in response
 * to a valid Domain Info command with the Neulevel Unspec extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see DomainInfoUnspecResponseExtension
 */
public final class DomainInfoUnspecResponseExtension extends ResponseExtension {

    private static final String UNSPEC_PREFIX = ExtendedObjectType.UNSPEC.getName();
    private static final String NEULEVEL_DATA_EXPRESSION = EXTENSION_EXPR + "/" + UNSPEC_PREFIX + ":extension/";
    private static final String CHKDATA_COUNT_EXPR = "count(" + NEULEVEL_DATA_EXPRESSION + "*)";
    private static final String CHKDATA_UNSPEC_DETAILS_EXPR = NEULEVEL_DATA_EXPRESSION + UNSPEC_PREFIX + ":unspec";
    private static final String CHKDATA_UNSPEC_EXISTS_EXPR = "count(" + CHKDATA_UNSPEC_DETAILS_EXPR + ")";

    private boolean initialised;
    private String unspecDetails;

    public void fromXML(final XMLDocument xmlDoc) throws XPathExpressionException {

        int elementCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);

        if (elementCount > 0) {
            unspecDetails = xmlDoc.getNodeValue(CHKDATA_UNSPEC_DETAILS_EXPR);
            initialised = true;
        }

    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getUnspecDetails() {
        return unspecDetails;
    }
}
