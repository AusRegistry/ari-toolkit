package com.ausregistry.jtoolkit2.se.launch;

import static com.ausregistry.jtoolkit2.se.ReceiveSE.replaceIndex;

import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.se.tmch.ClaimsInfo;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Representation of the EPP Domain Check response Extension with the Claims key Check aspect of the
 * Domain Name Launch extension.</p>
 *
 * <p>Use this to get claims key for domain name during Trademark Clearing House Claims Period as provided
 * in an EPP Domain Check response.
 * Such a service element is sent by a compliant EPP server in response
 * to a valid Domain Check command with the Domain Name Launch extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see DomainCheckLaunchCommandExtension
 * @see <a href="https://tools.ietf.org/html/draft-ietf-eppext-launchphase-07">Domain Name Launch
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckLaunchResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -1696245785014202518L;

    private static final String CHKDATA_COUNT_EXPR = "count(" + EXTENSION_EXPR + "/launch:chkData/*)";
    private static final String CHKDATA_PHASE_EXPR = EXTENSION_EXPR + "/launch:chkData/launch:phase";
    private static final String CHKDATA_PHASE_COUNT_EXPR = "count(" + CHKDATA_PHASE_EXPR + ")";
    private static final String CHKDATA_PHASE_NAME_EXPR = "/@name";
    private static final String CHKDATA_IND_EXPR = EXTENSION_EXPR + "/launch:chkData/launch:cd[IDX]";
    private static final String CHKDATA_DOMAIN_NAME_EXPR = "/launch:name/text()";
    private static final String CHKDATA_EXISTS_VALUE_EXPR = "/launch:name/@exists";
    private static final String CHKDATA_CLAIMS_KEY_EXPR = "/launch:claimKey/text()";

    private Map<String, ClaimsInfo> claimsNameMap;
    private Map<Long, ClaimsInfo> claimsIndexMap;

    private boolean isInitialised;
    private String phaseType;
    private String phaseName;

    public DomainCheckLaunchResponseExtension() {
        claimsNameMap = new HashMap<String, ClaimsInfo>();
        claimsIndexMap = new HashMap<Long, ClaimsInfo>();
    }

    /**
     * @param xmlDoc the XML to be processed
     */
    @Override
    public final void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        int elementCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);
        int phaseCount = xmlDoc.getNodeCount(CHKDATA_PHASE_COUNT_EXPR);
        if (phaseCount > 0) {
            processPhaseElement(xmlDoc);
        }
        for (int i = phaseCount; i < elementCount; i++) {
            processElement(xmlDoc, i + 1 - phaseCount);
        }
        if (elementCount > 0) {
            isInitialised = true;
        }
    }

    @Override
    public boolean isInitialised() {
        return isInitialised;
    }

    /**
     * @param domainName domain name to be checked
     * @return true if the domain is in Domain Name Label List, false otherwise
     */
    public final Boolean exists(String domainName) {
        ClaimsInfo claimsInfo = claimsNameMap.get(domainName);
        return claimsInfo == null ? null : claimsInfo.exists();
    }
    /**
     * @param index the index of domain to be checked
     * @return true if the domain is in Domain Name Label List, false otherwise
     */
    public final Boolean exists(final long index) {
        ClaimsInfo claimsInfo = claimsIndexMap.get(index);
        return claimsInfo == null ? null : claimsInfo.exists();
    }

    /**
     * @param domainName domain name to be checked
     * @return claimsKey if domain with the claims key is in Domain Name Label List
     */
    public final String getClaimsKey(String domainName) {
        ClaimsInfo claimsInfo = claimsNameMap.get(domainName);
        return claimsInfo == null ? null : claimsInfo.getClaimsKey();
    }

    /**
     * @param index the index of domain to be checked
     * @return claimsKey if domain with the claims key is in Domain Name Label List
     */
    public final String getClaimsKey(final long index) {
        ClaimsInfo claimsInfo = claimsIndexMap.get(index);
        return claimsInfo == null ? null : claimsInfo.getClaimsKey();
    }

    /**
     * @return phaseType the phase in the original request
    */
    public String getPhaseType() {
        return phaseType;
    }

    /**
     * @return phaseName the name of the phase in the original request
     */
    public String getPhaseName() {
        return phaseName;
    }

    private void processElement(XMLDocument xmlDoc, int i) throws XPathExpressionException {
        String qry = replaceIndex(CHKDATA_IND_EXPR, i);
        String domainName = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_NAME_EXPR);
        String existsString = xmlDoc.getNodeValue(qry + CHKDATA_EXISTS_VALUE_EXPR);
        String claimsKey = xmlDoc.getNodeValue(qry + CHKDATA_CLAIMS_KEY_EXPR);

        ClaimsInfo claimsInfo = new ClaimsInfo("1".equals(existsString), claimsKey);
        claimsNameMap.put(domainName, claimsInfo);
        claimsIndexMap.put(i + 0L, claimsInfo);
    }

    private void processPhaseElement(XMLDocument xmlDoc) throws XPathExpressionException {
        phaseType = xmlDoc.getNodeValue(CHKDATA_PHASE_EXPR);
        phaseName = xmlDoc.getNodeValue(CHKDATA_PHASE_EXPR + CHKDATA_PHASE_NAME_EXPR);
    }

}
