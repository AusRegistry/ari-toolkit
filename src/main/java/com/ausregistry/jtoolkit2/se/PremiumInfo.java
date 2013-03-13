package com.ausregistry.jtoolkit2.se;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Contains the availability of an object returned in a check response.
 */
public class PremiumInfo implements Serializable {

    private static final long serialVersionUID = -3939032852756585030L;
    private boolean isPremium;
    private BigDecimal createPrice;
    private BigDecimal renewPrice;

    public PremiumInfo(final boolean isPremium, final BigDecimal createPrice, final BigDecimal renewPrice) {
        this.isPremium = isPremium;
        this.createPrice = createPrice;
        this.renewPrice = renewPrice;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public BigDecimal getCreatePrice() {
        return createPrice;
    }

    public BigDecimal getRenewPrice() {
        return renewPrice;
    }

}
