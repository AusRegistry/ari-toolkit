package com.ausregistry.jtoolkit2.se.price;

import java.io.Serializable;
import java.math.BigDecimal;

import com.ausregistry.jtoolkit2.se.Period;

/**
 * Contains the price information of a domain returned in a check response extension.
 */
public class PriceInfo implements Serializable {

    private static final long serialVersionUID = -3981943930495021516L;
    private Boolean isPremium;
    private Period period;
    private BigDecimal createPrice;
    private BigDecimal renewPrice;
    private BigDecimal restorePrice;
    private BigDecimal transferPrice;
    private String reason;

    /**
     * @param isPremium if the domain is premium
     * @param period period that the prices relate to
     * @param createPrice create price for the domain
     * @param renewPrice renew price for the domain
     * @param reason reason message with extra information
     */
    public PriceInfo(final Boolean isPremium, final Period period, final BigDecimal createPrice,
                     final BigDecimal renewPrice, final String reason) {
        this.isPremium = isPremium;
        this.period = period;
        this.createPrice = createPrice;
        this.renewPrice = renewPrice;
        this.reason = reason;
    }

    /**
     * @param isPremium if the domain is premium
     * @param period period that the prices relate to
     * @param createPrice create price for the domain
     * @param renewPrice renew price for the domain
     * @param restorePrice restore price for the domain
     * @param transferPrice transfer price for the domain
     * @param reason reason message with extra information
     */
    public PriceInfo(final Boolean isPremium, final Period period, final BigDecimal createPrice,
                     final BigDecimal renewPrice, final BigDecimal restorePrice, final BigDecimal transferPrice,
                     final String reason) {
        this.isPremium = isPremium;
        this.period = period;
        this.createPrice = createPrice;
        this.renewPrice = renewPrice;
        this.restorePrice = restorePrice;
        this.transferPrice = transferPrice;
        this.reason = reason;
    }

    /**
     * @return if a domain is premium
     */
    public Boolean isPremium() {
        return isPremium;
    }

    /**
     * @return period that the prices relate to
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * @return create price for premium domain
     */
    public BigDecimal getCreatePrice() {
        return createPrice;
    }

    /**
     * @return renew price for premium domain
     */
    public BigDecimal getRenewPrice() {
        return renewPrice;
    }

    /**
     * @return renew price for premium domain
     */
    public BigDecimal getRestorePrice() {
        return restorePrice;
    }

    /**
     * @return renew price for premium domain
     */
    public BigDecimal getTransferPrice() {
        return transferPrice;
    }

    /**
     * @return reason message with extra information
     */
    public String getReason() {
        return reason;
    }
}
