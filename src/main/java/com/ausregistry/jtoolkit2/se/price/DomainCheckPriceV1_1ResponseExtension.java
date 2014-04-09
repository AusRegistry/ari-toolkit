package com.ausregistry.jtoolkit2.se.price;

import static com.ausregistry.jtoolkit2.se.ReceiveSE.replaceIndex;

import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.ausregistry.jtoolkit2.se.Period;
import com.ausregistry.jtoolkit2.se.PeriodUnit;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Representation of the EPP Domain Check response extension for Pricing Check aspect of the Domain Name Check
 * Pricing extension.</p>
 *
 * <p>Use this to access "period", "create", "renew", "transfer" and "restore" fees, and "reason" pricing information
 * for domains as provided in an extension to the EPP Domain Check response. Such a service element is sent by a
 * compliant EPP server in response to a valid Domain Check command with the Pricing Check extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see DomainCheckPriceV1_1CommandExtension
 * @see <a href="http://ausregistry.github.io/doc/pricing-1.1/pricing-1.1.html">Domain Name Check Pricing Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckPriceV1_1ResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -5006112249663937462L;

    private static final String CHKDATA_COUNT_EXPR = "count(" + EXTENSION_EXPR + "/priceV1_1:chkData/*)";
    private static final String CHKDATA_IND_EXPR = EXTENSION_EXPR + "/priceV1_1:chkData/priceV1_1:cd[IDX]";
    private static final String CHKDATA_DOMAIN_NAME_EXPR = "/priceV1_1:name/text()";
    private static final String CHKDATA_PREMIUM_VALUE_EXPR = "/priceV1_1:name/@premium";
    private static final String CHKDATA_PERIOD_UNIT_EXPR = "/priceV1_1:period/@unit";
    private static final String CHKDATA_PERIOD_VALUE_EXPR = "/priceV1_1:period/text()";
    private static final String CHKDATA_CREATE_PRICE_EXPR = "/priceV1_1:createPrice/text()";
    private static final String CHKDATA_RENEW_PRICE_EXPR = "/priceV1_1:renewPrice/text()";
    private static final String CHKDATA_RESTORE_PRICE_EXPR = "/priceV1_1:restorePrice/text()";
    private static final String CHKDATA_TRANSFER_PRICE_EXPR = "/priceV1_1:transferPrice/text()";
    private static final String CHKDATA_REASON_EXPR = "/priceV1_1:reason/text()";

    private Map<String, PriceInfo> priceNameMap;
    private Map<Long, PriceInfo> priceIndexMap;
    private boolean initialised;


    public DomainCheckPriceV1_1ResponseExtension() {
        priceNameMap = new HashMap<String, PriceInfo>();
        priceIndexMap = new HashMap<Long, PriceInfo>();
    }

    /**
     * @param xmlDoc the XML to be processed
     */
    @Override
    public final void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        int priceCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);
        for (int i = 0; i < priceCount; i++) {
            String qry = replaceIndex(CHKDATA_IND_EXPR, i + 1);

            final String domainName = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_NAME_EXPR);
            String isPremiumDomainValue = xmlDoc.getNodeValue(qry + CHKDATA_PREMIUM_VALUE_EXPR);
            Boolean isPremiumDomain = isPremiumDomainValue == null ? null : isPremiumDomainValue.equals("1");
            String periodValue = xmlDoc.getNodeValue(qry + CHKDATA_PERIOD_VALUE_EXPR);
            Period period = periodValue == null ? null
                    : new Period(PeriodUnit.value(xmlDoc.getNodeValue(qry + CHKDATA_PERIOD_UNIT_EXPR)),
                                    Integer.parseInt(periodValue));
            String createPriceValue = xmlDoc.getNodeValue(qry + CHKDATA_CREATE_PRICE_EXPR);
            BigDecimal createPrice =
                    createPriceValue == null ? null : BigDecimal.valueOf(Double.parseDouble(createPriceValue));
            String renewPriceValue = xmlDoc.getNodeValue(qry
                    + CHKDATA_RENEW_PRICE_EXPR);
            BigDecimal renewPrice =
                    renewPriceValue == null ? null : BigDecimal.valueOf(Double.parseDouble(renewPriceValue));
            String restorePriceValue = xmlDoc.getNodeValue(qry
                    + CHKDATA_RESTORE_PRICE_EXPR);
            BigDecimal restorePrice =
                    restorePriceValue == null ? null : BigDecimal.valueOf(Double.parseDouble(restorePriceValue));
            String transferPriceValue = xmlDoc.getNodeValue(qry
                    + CHKDATA_TRANSFER_PRICE_EXPR);
            BigDecimal transferPrice =
                    transferPriceValue == null ? null : BigDecimal.valueOf(Double.parseDouble(transferPriceValue));
            String reason = xmlDoc.getNodeValue(qry + CHKDATA_REASON_EXPR);

            PriceInfo priceInfo = new PriceInfo(isPremiumDomain, period, createPrice, renewPrice, restorePrice,
                    transferPrice, reason);
            priceIndexMap.put(i + 1L, priceInfo);
            priceNameMap.put(domainName, priceInfo);
        }
        initialised = (priceCount > 0);
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    /**
     * @param domainName domain name to be checked
     * @return true if premium, false if not premium for domain if exists otherwise null
     */
    public final Boolean isPremium(final String domainName) {
        PriceInfo priceInfo = priceNameMap.get(domainName);
        return priceInfo == null ? null : priceInfo.isPremium();
    }

    /**
     * @param domainName domain name to be checked
     * @return period that the prices relate to for domain if exists otherwise null
     */
    public final Period getPeriod(final String domainName) {
        PriceInfo priceInfo = priceNameMap.get(domainName);
        return priceInfo == null ? null : priceInfo.getPeriod();
    }

    /**
     * @param domainName domain name to be checked
     * @return create price for domain if exists otherwise null
     */
    public final BigDecimal getCreatePrice(final String domainName) {
        PriceInfo priceInfo = priceNameMap.get(domainName);
        return priceInfo == null ? null : priceInfo.getCreatePrice();
    }

    /**
     * @param domainName domain name to be checked
     * @return renew price for domain if exists otherwise null
     */
    public final BigDecimal getRenewPrice(final String domainName) {
        PriceInfo priceInfo = priceNameMap.get(domainName);
        return priceInfo == null ? null : priceInfo.getRenewPrice();
    }

    /**
     * @param domainName domain name to be checked
     * @return restore price for domain if exists otherwise null
     */
    public final BigDecimal getRestorePrice(final String domainName) {
        PriceInfo priceInfo = priceNameMap.get(domainName);
        return priceInfo == null ? null : priceInfo.getRestorePrice();
    }

    /**
     * @param domainName domain name to be checked
     * @return transfer price for domain if exists otherwise null
     */
    public final BigDecimal getTransferPrice(final String domainName) {
        PriceInfo priceInfo = priceNameMap.get(domainName);
        return priceInfo == null ? null : priceInfo.getTransferPrice();
    }

    /**
     * @param domainName domain name to be checked
     * @return reason message with extra information for domain if exists otherwise null
     */
    public final String getReason(final String domainName) {
        PriceInfo priceInfo = priceNameMap.get(domainName);
        return priceInfo == null ? null : priceInfo.getReason();
    }

    /**
     * @param index the index of domain to be checked
     * @return true if premium, false if not premium for domain if exists otherwise null
     */
    public final Boolean isPremium(final long index) {
        PriceInfo priceInfo = priceIndexMap.get(index);
        return priceInfo == null ? null : priceInfo.isPremium();
    }

    /**
     * @param index the index of domain to be checked
     * @return period that the prices relate to for domain if exists otherwise null
     */
    public final Period getPeriod(final long index) {
        PriceInfo priceInfo = priceIndexMap.get(index);
        return priceInfo == null ? null : priceInfo.getPeriod();
    }

    /**
     * @param index the index of domain to be checked
     * @return create price for domain if exists otherwise null
     */
    public final BigDecimal getCreatePrice(final Long index) {
        PriceInfo priceInfo = priceIndexMap.get(index);
        return priceInfo == null ? null : priceInfo.getCreatePrice();
    }

    /**
     * @param index the index of domain to be checked
     * @return renew price for domain if exists otherwise null
     */
    public final BigDecimal getRenewPrice(final Long index) {
        PriceInfo priceInfo = priceIndexMap.get(index);
        return priceInfo == null ? null : priceInfo.getRenewPrice();
    }

    /**
     * @param index the index of domain to be checked
     * @return restore price for domain if exists otherwise null
     */
    public final BigDecimal getRestorePrice(final Long index) {
        PriceInfo priceInfo = priceIndexMap.get(index);
        return priceInfo == null ? null : priceInfo.getRestorePrice();
    }

    /**
     * @param index the index of domain to be checked
     * @return transfer price for domain if exists otherwise null
     */
    public final BigDecimal getTransferPrice(final Long index) {
        PriceInfo priceInfo = priceIndexMap.get(index);
        return priceInfo == null ? null : priceInfo.getTransferPrice();
    }

    /**
     * @param index the index of domain to be checked
     * @return reason message with extra information for domain if exists otherwise null
     */
    public final String getReason(final long index) {
        PriceInfo priceInfo = priceIndexMap.get(index);
        return priceInfo == null ? null : priceInfo.getReason();
    }
}
