package com.ausregistry.jtoolkit2.se.premium;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

public class DomainCheckPremiumResponse extends DataResponse {

    private static final long serialVersionUID = 1616054275103086838L;

    private static final String CHKDATA_COUNT_EXPR = "count(" + RES_DATA_EXPR
            + "/premium:chkData/*)";
    private static final String CHKDATA_IND_EXPR = RES_DATA_EXPR
            + "/premium:chkData/premium:cd[IDX]";
    private static final String CHKDATA_DOMAIN_NAME_EXPR = "/premium:name/text()";
    private static final String CHKDATA_PREMIUM_VALUE_EXPR = "/premium:name/@premium";
    private static final String CHKDATA_CREATE_PRICE_EXPR = "/premium:price/text()";
    private static final String CHKDATA_RENEW_PRICE_EXPR = "/premium:renewalPrice/text()";

    private Map<String, PremiumInfo> premiumNameMap;
    private Map<Long, PremiumInfo> premiumIndexMap;


    protected DomainCheckPremiumResponse() {
        super(StandardCommandType.CHECK, StandardObjectType.DOMAIN);
        premiumNameMap = new HashMap<String, PremiumInfo>();
        premiumIndexMap = new HashMap<Long, PremiumInfo>();
    }


    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            int cdCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);

            for (int i = 0; i < cdCount; i++) {
                processElement(xmlDoc, i);
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        } catch (NumberFormatException nfe) {
            maintLogger.warning(nfe.getMessage());
        } catch (NullPointerException npe) {
            maintLogger.warning(npe.getMessage());
        }
    }

    private void processElement(XMLDocument xmlDoc, int i) throws XPathExpressionException {
        String qry = replaceIndex(CHKDATA_IND_EXPR, i + 1);
        final String domainName = getKey(xmlDoc, qry);
        String premiumStr = xmlDoc.getNodeValue(qry + CHKDATA_PREMIUM_VALUE_EXPR);
        boolean isPremiumDomain = (premiumStr.equals("1") ? true : false);
        PremiumInfo premiumInfo;
        if(isPremiumDomain) {
        BigDecimal createPriceValue = BigDecimal.valueOf(Double.parseDouble(xmlDoc.getNodeValue(qry
                + CHKDATA_CREATE_PRICE_EXPR)));
        BigDecimal renewPriceValue = BigDecimal.valueOf(Double.parseDouble(xmlDoc.getNodeValue(qry
                + CHKDATA_RENEW_PRICE_EXPR)));
         premiumInfo = new PremiumInfo(isPremiumDomain, createPriceValue, renewPriceValue);
        } else {
            premiumInfo = new PremiumInfo(isPremiumDomain, null, null);
        }
        premiumIndexMap.put(i + 1L, premiumInfo);
        premiumNameMap.put(domainName, premiumInfo);
    }

    private String getKey(final XMLDocument xmlDoc, final String qry) throws XPathExpressionException {
        return xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_NAME_EXPR);
    }

    public boolean isPremium(String domainName) {
        return premiumNameMap.get(domainName).isPremium();
    }

    public BigDecimal getCreatePrice(String domainName) {
        return premiumNameMap.get(domainName).getCreatePrice();
    }

    public BigDecimal getRenewPrice(String domainName) {
        return premiumNameMap.get(domainName).getRenewPrice();
    }

    public boolean isPremium(long index) {
        return premiumIndexMap.get(index).isPremium();
    }

    public BigDecimal getCreatePrice(long index) {
        return premiumIndexMap.get(index).getCreatePrice();
    }

    public BigDecimal getRenewPrice(long index) {
        return premiumIndexMap.get(index).getRenewPrice();
    }
}
