package com.ausregistry.jtoolkit2.se.fee;


import com.ausregistry.jtoolkit2.se.Period;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FeeCheckData implements Serializable {

    private final String name;
    private String currency;
    private final Command command;
    private Period period;
    private List<Fee> fees = new ArrayList<Fee>();
    private String feeClass;

    public FeeCheckData(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Command getCommand() {
        return command;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void addFee(Fee fee) {
        this.fees.add(fee);
    }

    public String getFeeClass() {
        return feeClass;
    }

    public void setFeeClass(String feeClass) {
        this.feeClass = feeClass;
    }

    public static class Fee implements Serializable {
        private final String description;
        private Boolean refundable;
        private final BigDecimal fee;

        public Fee(BigDecimal fee, String description) {
            this.fee = fee;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public Boolean getRefundable() {
            return refundable;
        }

        public void setRefundable(Boolean refundable) {
            this.refundable = refundable;
        }

        public BigDecimal getFee() {
            return fee;
        }
    }

    public static class Command implements Serializable {

        private final String name;
        private String phase;
        private String subphase;

        public Command(String name) {
            this.name = name;
        }


        public String getName() {
            return name;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public String getSubphase() {
            return subphase;
        }

        public void setSubphase(String subphase) {
            this.subphase = subphase;
        }
    }

}
