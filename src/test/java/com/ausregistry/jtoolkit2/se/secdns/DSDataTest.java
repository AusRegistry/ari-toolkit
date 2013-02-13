package com.ausregistry.jtoolkit2.se.secdns;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DSDataTest {

    @Test
    public void testValidation() throws Exception {
        final DSData dsData = new DSData();
        boolean invalidData = false;
        invalidData = false;
        try {
            dsData.setAlg(256);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Algorithm is not invalid", invalidData);
        invalidData = false;
        try {
            dsData.setAlg(-1);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Algorithm is not invalid", invalidData);

        invalidData = false;
        try {
            dsData.setDigestType(256);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Digest type is not invalid", invalidData);
        invalidData = false;
        try {
            dsData.setDigestType(-1);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Digest type is not invalid", invalidData);

        invalidData = false;
        try {
            dsData.setKeyTag(65536);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Key Tag is not invalid", invalidData);
        invalidData = false;
        try {
            dsData.setKeyTag(-1);
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Key Tag is not invalid", invalidData);

        invalidData = false;
        try {
            dsData.setDigest("xxx");
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Digest is not invalid", invalidData);
        invalidData = false;
        try {
            dsData.setDigest("");
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Digest is not invalid", invalidData);
        invalidData = false;
        try {
            dsData.setDigest("ABCF3");
        } catch (IllegalArgumentException e) {
            invalidData = true;
        }
        assertTrue("Digest is not invalid", invalidData);
    }

    @Test
    public void shouldVerifyDsDataIsTheSame() {
        DSData dsData = new DSData(1, 1, 1, "ABCD");
        DSData dsDataSame = new DSData(1, 1, 1, "ABCD");

        assertTrue("DS Data was not the same", dsData.equals(dsDataSame));
    }

    @Test
    public void shouldVerifyDsDataWithKeyDataIsTheSame() {
        DSData dsData = new DSData(1, 1, 1, "ABCD");

        KeyData keyData = new KeyData(1, 1, 1, "XXXX");
        dsData.setKeyData(keyData);

        DSData dsDataSame = new DSData(1, 1, 1, "ABCD");
        dsDataSame.setKeyData(keyData);

        assertTrue("DS Data with Key Data was not the same", dsData.equals(dsDataSame));
    }

}
