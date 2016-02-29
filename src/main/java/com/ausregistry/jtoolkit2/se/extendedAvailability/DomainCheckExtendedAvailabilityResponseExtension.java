package com.ausregistry.jtoolkit2.se.extendedAvailability;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.se.ReceiveSE;

import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Extended Availability Check response extension for EPP Domain Check command.</p>
 *
 * <p>Use this to access "state", "reason", "date", "phase" and "variantPrimaryDomainName" information for domains as
 * provided in an extension to the EPP Domain Check response. Such a service element is sent by a compliant EPP server
 * in response to a valid Domain Check command with the Extended Availability Check extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.extendedAvailability.DomainCheckExtendedAvailabilityCommandExtension
 * @see <a href="http://ausregistry.github.io/doc/exAvail-1.0/exAvail-1.0.html">Domain Name Check Extended Availability
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckExtendedAvailabilityResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = 5649370730246651621L;

    private static final String CHKDATA_COUNT_EXPR = "count(" + EXTENSION_EXPR + "/exAvail:chkData/*)";
    private static final String CHKDATA_IND_EXPR = EXTENSION_EXPR + "/exAvail:chkData/exAvail:cd[IDX]";
    private static final String CHKDATA_DOMAIN_NAME_EXPR = "/exAvail:name/text()";
    private static final String CHKDATA_DOMAIN_STATE_EXPR = "/exAvail:state/@s";
    private static final String CHKDATA_DOMAIN_STATE_REASON_EXPR = "/exAvail:state/exAvail:reason/text()";
    private static final String CHKDATA_DOMAIN_STATE_PHASE_EXPR = "/exAvail:state/exAvail:phase/text()";
    private static final String CHKDATA_DOMAIN_STATE_DATE_EXPR = "/exAvail:state/exAvail:date/text()";
    private static final String CHKDATA_DOMAIN_STATE_PRIMARY_DOMAIN_NAME_EXPR =
            "/exAvail:state/exAvail:primaryDomainName/text()";

    private Map<String, DomainCheckExtendedAvailabilityDetails> domainExtAvailabilityStateMap =
            new HashMap<String, DomainCheckExtendedAvailabilityDetails>();

    /**
     * @param xmlDoc the XML to be processed
     */
    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        int extAvailStateCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);

        for (int i = 0; i < extAvailStateCount; i++) {
            String qry = ReceiveSE.replaceIndex(CHKDATA_IND_EXPR, i + 1);
            final String domainName = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_NAME_EXPR);
            String domainState = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_STATE_EXPR);
            GregorianCalendar date = EPPDateFormatter
                    .fromXSDateTime(xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_STATE_DATE_EXPR));
            String reason = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_STATE_REASON_EXPR);
            String phase = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_STATE_PHASE_EXPR);
            String variantPrimaryDomainName = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_STATE_PRIMARY_DOMAIN_NAME_EXPR);

            DomainCheckExtendedAvailabilityDetails extAvailabilityDetails =
                    new DomainCheckExtendedAvailabilityDetails(domainState, reason, date, phase,
                            variantPrimaryDomainName);

            domainExtAvailabilityStateMap.put(domainName, extAvailabilityDetails);
        }
    }

    @Override
    public boolean isInitialised() {
        return false;
    }

    /**
     * @return the extended availability details of all the domains checked for.
     */
    public Map<String, DomainCheckExtendedAvailabilityDetails> getDomainExtAvailabilityStateMap() {
        return domainExtAvailabilityStateMap;
    }

    /**
     * @param domainName domain name to be checked
     * @return the extended availability details for the domainName
     */
    public DomainCheckExtendedAvailabilityDetails getStateForDomain(String domainName) {
        return domainExtAvailabilityStateMap.get(domainName);
    }
}
