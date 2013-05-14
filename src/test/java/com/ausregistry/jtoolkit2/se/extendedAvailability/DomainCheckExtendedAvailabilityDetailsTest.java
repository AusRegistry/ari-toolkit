package com.ausregistry.jtoolkit2.se.extendedAvailability;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import org.junit.Test;

import java.util.GregorianCalendar;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

public class DomainCheckExtendedAvailabilityDetailsTest {
    
    @Test
    public void shouldEqualWhenAllFieldsAreEqual() {
        GregorianCalendar cal = EPPDateFormatter.fromXSDateTime("2010-04-25T00:00:00Z");
        DomainCheckExtendedAvailabilityDetails detailOne = new DomainCheckExtendedAvailabilityDetails("state", "reason",
                cal, "phase", "domainName");
        DomainCheckExtendedAvailabilityDetails detailTwo = new DomainCheckExtendedAvailabilityDetails("state", "reason",
                cal, "phase", "domainName");
        assertTrue(detailOne.equals(detailTwo));
        assertTrue(detailTwo.equals(detailOne));
    }

    @Test
    public void shouldEqualWhenNotRequiredFieldsAreNull() {
        DomainCheckExtendedAvailabilityDetails detailOne = new DomainCheckExtendedAvailabilityDetails("state", null,
                null, null, null);
        DomainCheckExtendedAvailabilityDetails detailTwo = new DomainCheckExtendedAvailabilityDetails("state", null,
                null, null, null);
        assertTrue(detailOne.equals(detailTwo));
        assertTrue(detailTwo.equals(detailOne));
    }
    
    @Test
    public void shouldNotEqualWhenStateNotEqual() {
        DomainCheckExtendedAvailabilityDetails detailOne = new DomainCheckExtendedAvailabilityDetails("stateOne", null,
                null, null, null);
        DomainCheckExtendedAvailabilityDetails detailTwo = new DomainCheckExtendedAvailabilityDetails("stateTwo", null,
                null, null, null);
        assertFalse(detailOne.equals(detailTwo));
        assertFalse(detailTwo.equals(detailOne));
    }
    
    @Test
    public void shouldNotEqualWhenReasonNotEqual() {
        DomainCheckExtendedAvailabilityDetails detailOne = new DomainCheckExtendedAvailabilityDetails("state",
                "reason1", null, null, null);
        DomainCheckExtendedAvailabilityDetails detailTwo = new DomainCheckExtendedAvailabilityDetails("state",
                "reason2", null, null, null);
        assertFalse(detailOne.equals(detailTwo));
        assertFalse(detailTwo.equals(detailOne));
    }

    @Test
    public void shouldNotEqualWhenDateNotEqual() {
        GregorianCalendar cal = EPPDateFormatter.fromXSDateTime("2010-04-25T00:00:00Z");
        GregorianCalendar calTwo = EPPDateFormatter.fromXSDateTime("2010-04-23T00:00:00Z");
        DomainCheckExtendedAvailabilityDetails detailOne = new DomainCheckExtendedAvailabilityDetails("state", null,
                cal, null, null);
        DomainCheckExtendedAvailabilityDetails detailTwo = new DomainCheckExtendedAvailabilityDetails("state", null,
                calTwo, null, null);
        assertFalse(detailOne.equals(detailTwo));
        assertFalse(detailTwo.equals(detailOne));
    }

    @Test
    public void shouldNotEqualWhenPhaseNotEqual() {
        DomainCheckExtendedAvailabilityDetails detailOne = new DomainCheckExtendedAvailabilityDetails("state", null,
                null, "phaseOne", null);
        DomainCheckExtendedAvailabilityDetails detailTwo = new DomainCheckExtendedAvailabilityDetails("state", null,
                null, "phaseTwo", null);
        assertFalse(detailOne.equals(detailTwo));
        assertFalse(detailTwo.equals(detailOne));
    }

    @Test
    public void shouldNotEqualWhenPrimaryDomainNameNotEqual() {
        DomainCheckExtendedAvailabilityDetails detailOne = new DomainCheckExtendedAvailabilityDetails("state", null,
                null, null, "domainOne");
        DomainCheckExtendedAvailabilityDetails detailTwo = new DomainCheckExtendedAvailabilityDetails("state", null,
                null, null, "domainTwo");
        assertFalse(detailOne.equals(detailTwo));
        assertFalse(detailTwo.equals(detailOne));
    }
}
