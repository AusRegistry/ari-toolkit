package com.ausregistry.jtoolkit2.se.extendedAvailability;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.se.ReceiveSE;

import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


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

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        int extAvailStateCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);

        for(int i = 0; i < extAvailStateCount; i++) {
            String qry = ReceiveSE.replaceIndex(CHKDATA_IND_EXPR, i + 1);
            final String domainName = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_NAME_EXPR);
            String domainState = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_STATE_EXPR);
            GregorianCalendar date = EPPDateFormatter
                    .fromXSDateTime(xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_STATE_DATE_EXPR));
            String reason = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_STATE_REASON_EXPR);
            String phase = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_STATE_PHASE_EXPR);
            String variantPrimaryDomainName = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_STATE_PRIMARY_DOMAIN_NAME_EXPR);

            DomainCheckExtendedAvailabilityDetails extAvailabilityDetails =
                    new DomainCheckExtendedAvailabilityDetails();
            extAvailabilityDetails.setState(domainState);
            extAvailabilityDetails.setDate(date);
            extAvailabilityDetails.setReason(reason);
            extAvailabilityDetails.setPhase(phase);
            extAvailabilityDetails.setVariantPrimaryDomainName(variantPrimaryDomainName);

            domainExtAvailabilityStateMap.put(domainName, extAvailabilityDetails);
        }
    }

    @Override
    public boolean isInitialised() {
        return false;
    }

    public Map<String, DomainCheckExtendedAvailabilityDetails> getDomainExtAvailabilityStateMap() {
        return domainExtAvailabilityStateMap;
    }

    public DomainCheckExtendedAvailabilityDetails getStateForDomain(String domainName) {
        return domainExtAvailabilityStateMap.get(domainName);
    }
}
