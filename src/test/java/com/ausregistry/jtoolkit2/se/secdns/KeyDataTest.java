package com.ausregistry.jtoolkit2.se.secdns;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KeyDataTest {

    @Test
    public void testValidation() throws Exception {
        final KeyData keyData = new KeyData();
        boolean invalidData = false;
        invalidData = false;
        try {
            keyData.setAlg(256);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Algorithm is not invalid", invalidData);
        invalidData = false;
        try {
            keyData.setAlg(-1);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Algorithm is not invalid", invalidData);

        invalidData = false;
        try {
            keyData.setFlags(65536);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Flags is not invalid", invalidData);
        invalidData = false;
        try {
            keyData.setFlags(-1);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Flags is not invalid", invalidData);

        invalidData = false;
        try {
            keyData.setProtocol(256);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Protocol is not invalid", invalidData);
        invalidData = false;
        try {
            keyData.setProtocol(-1);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Protocol is not invalid", invalidData);

        invalidData = false;
        try {
            keyData.setPubKey("");
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Public Key is not invalid", invalidData);
    }

}
