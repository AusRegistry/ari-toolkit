package com.ausregistry.jtoolkit2.se.rgp;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RgpStatusTest {

    @Test
    public void shouldEqual() {
        RgpStatus rgpStatus1 = new RgpStatus("status", "language", "message");
        RgpStatus rgpStatus2 = new RgpStatus("status", "language", "message");
        assertTrue(rgpStatus1.equals(rgpStatus2));
        assertTrue(rgpStatus2.equals(rgpStatus1));
    }

    @Test
    public void shouldNotEqualIfStatusDifferent() {
        RgpStatus rgpStatus1 = new RgpStatus("anotherStatus", "language", "message");
        RgpStatus rgpStatus2 = new RgpStatus("status", "language", "message");
        assertFalse(rgpStatus1.equals(rgpStatus2));
        assertFalse(rgpStatus2.equals(rgpStatus1));
    }

    @Test
    public void shouldNotEqualIfLanguageDifferent() {
        RgpStatus rgpStatus1 = new RgpStatus("status", "anotherLanguage", "message");
        RgpStatus rgpStatus2 = new RgpStatus("status", "language", "message");
        assertFalse(rgpStatus1.equals(rgpStatus2));
        assertFalse(rgpStatus2.equals(rgpStatus1));
    }

    @Test
    public void shouldNotEqualIfMessageDifferent() {
        RgpStatus rgpStatus1 = new RgpStatus("status", "language", "anotherMessage");
        RgpStatus rgpStatus2 = new RgpStatus("status", "language", "message");
        assertFalse(rgpStatus1.equals(rgpStatus2));
        assertFalse(rgpStatus2.equals(rgpStatus1));
    }

}
