package com.ausregistry.jtoolkit2.se.tmch;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

import static com.ausregistry.jtoolkit2.se.ReceiveSE.replaceIndex;

/**
 * <p>Representation of the EPP Domain Check response Extension with the Claims key Check aspect of the
 * Domain Name Trademark Clearing extension.</p>
 *
 * <p>Use this to get claims key for domain name during Trademark Clearing House Claims Period as provided
 * in an EPP Domain Check response.
 * Such a service element is sent by a compliant EPP server in response
 * to a valid Domain Check command with the Domain Name Trademark Clearing House extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.tmch.TmchDomainCheckCommandExtension
 * @see <a href="http://ausregistry.github.io/doc/tmch-1.0/tmch-1.0.html">Trademark Clearinghouse Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class TmchDomainCheckResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -1696245785014202518L;

    private static final String CHKDATA_COUNT_EXPR = "count(" + EXTENSION_EXPR
            + "/tmch:chkData/*)";
    private static final String CHKDATA_IND_EXPR = EXTENSION_EXPR
            + "/tmch:chkData/tmch:cd[IDX]";
    private static final String CHKDATA_DOMAIN_NAME_EXPR = "/tmch:name/text()";
    private static final String CHKDATA_EXISTS_VALUE_EXPR = "/tmch:name/@claim";
    private static final String CHKDATA_CLAIMS_KEY_EXPR = "/tmch:key/text()";

    private Map<String, ClaimsInfo> claimsNameMap;
    private Map<Long, ClaimsInfo> claimsIndexMap;

    private boolean isInitialised;

    public TmchDomainCheckResponseExtension() {
        claimsNameMap = new HashMap<String, ClaimsInfo>();
        claimsIndexMap = new HashMap<Long, ClaimsInfo>();
    }

    /**
     * @param xmlDoc the XML to be processed
     */
    @Override
    public final void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        int cdCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);
        for (int i = 0; i < cdCount; i++) {
            processElement(xmlDoc, i);
        }
        if (cdCount > 0) {
            isInitialised = true;
        }
    }

    @Override
    public boolean isInitialised() {
        return isInitialised;
    }

    private void processElement(XMLDocument xmlDoc, int i) throws XPathExpressionException {
        String qry = replaceIndex(CHKDATA_IND_EXPR, i + 1);
        String domainName = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_NAME_EXPR);
        String existsString = xmlDoc.getNodeValue(qry + CHKDATA_EXISTS_VALUE_EXPR);
        boolean exists = (existsString.equals("1") ? true : false);
        String claimsKey = xmlDoc.getNodeValue(qry + CHKDATA_CLAIMS_KEY_EXPR);

        ClaimsInfo claimsInfo = new ClaimsInfo(exists, claimsKey);
        claimsNameMap.put(domainName, claimsInfo);
        claimsIndexMap.put(i + 1L, claimsInfo);
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
}
