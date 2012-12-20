package com.ausregistry.jtoolkit2;

import static org.junit.Assert.*;

import org.junit.Test;

public class ErrorPkgTest {
    @Test
    public void testGetMessageString() {
        String msg = ErrorPkg.getMessage("test.msg.1");
        assertEquals("This is a test message; do not change", msg);
    }

    @Test
    public void testGetMessageStringStringString() {
        String msg = ErrorPkg.getMessage("test.msg.1", "change", "modify");
        assertEquals("This is a test message; do not modify", msg);
    }

    @Test
    public void testGetMessageStringStringArrayStringArray() {
        String[] args = new String[] {"test", "change"};
        String[] vals = new String[] {"weird", "womble"};
        String msg = ErrorPkg.getMessage("test.msg.1", args, vals);
        assertEquals("This is a weird message; do not womble", msg);
    }

    @Test
    public void testGetMessageFromInts() {
        String[] args = new String[] {"<<in1>>", "<<in2>>"};
        int[] vals = new int[] {0,1};
        String msg = ErrorPkg.getMessage("test.msg.2", args, vals);
        assertEquals("Test message 0; 1", msg);
    }
}
