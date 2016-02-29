package com.ausregistry.jtoolkit2.se.price;

import static com.ausregistry.jtoolkit2.se.ReceiveSE.replaceIndex;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Representation of the EPP Domain Check response extension for Pricing Check aspect of the Domain Name Check
 * Pricing extension.</p>
 *
 * <p>Use this to access "period", "create" and "renew" fees, and "reason" pricing information for domains as
 * provided in an extension to the EPP Domain Check response. Such a service element is sent by a compliant EPP server
 * in response to a valid Domain Check command with the Pricing Check extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see DomainCheckPricingCommandExtension
 * @see <a href="http://ausregistry.github.io/doc/pricing-1.0/pricing-1.0.html">Domain Name Check Pricing Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckPriceResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = 1616054275103086838L;

    private static final String CHKDATA_COUNT_EXPR = "count(" + EXTENSION_EXPR + "/price:chkData/*)";
    private static final String CHKDATA_IND_EXPR = EXTENSION_EXPR + "/price:chkData/price:cd[IDX]";
    private static final String CHKDATA_DOMAIN_NAME_EXPR = "/price:name/text()";
    private static final String CHKDATA_PREMIUM_VALUE_EXPR = "/price:name/@premium";
    private static final String CHKDATA_PERIOD_UNIT_EXPR = "/price:period/@unit";
    private static final String CHKDATA_PERIOD_VALUE_EXPR = "/price:period/text()";
    private static final String CHKDATA_CREATE_PRICE_EXPR = "/price:price/text()";
    private static final String CHKDATA_RENEW_PRICE_EXPR = "/price:renewalPrice/text()";
    private static final String CHKDATA_REASON_EXPR = "/price:reason/text()";

    private Map<String, PriceInfo> priceNameMap;
    private Map<Long, PriceInfo> priceIndexMap;
    private boolean initialised;


    public DomainCheckPriceResponseExtension() {
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
            Period period = periodValue == null
                    ? null
                    : new Period(PeriodUnit.value(xmlDoc.getNodeValue(qry + CHKDATA_PERIOD_UNIT_EXPR)),
                                    Integer.parseInt(periodValue));
            String createPriceValue = xmlDoc.getNodeValue(qry + CHKDATA_CREATE_PRICE_EXPR);
            BigDecimal createPrice =
                    createPriceValue == null ? null : BigDecimal.valueOf(Double.parseDouble(createPriceValue));
            String renewPriceValue = xmlDoc.getNodeValue(qry
                    + CHKDATA_RENEW_PRICE_EXPR);
            BigDecimal renewPrice =
                    renewPriceValue == null ? null : BigDecimal.valueOf(Double.parseDouble(renewPriceValue));
            String reason = xmlDoc.getNodeValue(qry + CHKDATA_REASON_EXPR);

            PriceInfo priceInfo = new PriceInfo(isPremiumDomain, period, createPrice, renewPrice, reason);
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
     * @return reason message with extra information for domain if exists otherwise null
     */
    public final String getReason(final long index) {
        PriceInfo priceInfo = priceIndexMap.get(index);
        return priceInfo == null ? null : priceInfo.getReason();
    }
}
