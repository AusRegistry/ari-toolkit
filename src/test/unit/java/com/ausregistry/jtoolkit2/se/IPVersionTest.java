package com.ausregistry.jtoolkit2.se;

import static org.junit.Assert.*;

import org.junit.Test;

public class IPVersionTest {

    @Test
    public void testToStringV4() {
        assertEquals(IPVersion.IPv4.toString(), "v4");
    }

    @Test
    public void testToStringV6() {
        assertEquals(IPVersion.IPv6.toString(), "v6");
    }

    @Test
    public void testValueOfV4() {
        assertEquals(IPVersion.IPv4, IPVersion.value(null));
    }

    @Test
    public void testValueOfV6() {
        assertNotSame(IPVersion.IPv6, IPVersion.value(null));
    }
}

