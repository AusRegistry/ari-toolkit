package com.ausregistry.jtoolkit2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

import javax.xml.bind.DatatypeConverter;

import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Calendar;

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
        int[] vals = new int[] {0, 1};
        String msg = ErrorPkg.getMessage("test.msg.2", args, vals);
        assertEquals("Test message 0; 1", msg);
    }

    @Test
    public void shouldFormatDateCorrectly() {
        Calendar calendar = DatatypeConverter.parseDate("2009-08-16T09:00:00Z");
        String message = ErrorPkg.getMessage("test.msg.2", "<<in1>>", calendar.getTime());

        assertThat(message, is("Test message 2009-08-16T09:00:00Z; <<in2>>"));
    }

    @Test
    public void shouldFormatCertificateCorrectly() {
        Principal mockPrincipal = mock(Principal.class);
        Principal mockPrincipalTwo = mock(Principal.class);
        X509Certificate mockSmdCertificate = mock(X509Certificate.class);

        when(mockSmdCertificate.getSerialNumber()).thenReturn(BigInteger.valueOf(7));
        when(mockSmdCertificate.getSubjectDN()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("EMAILADDRESS=revoked@abc.com.au, CN=revoked,"
                + " OU=Oud, O=ARI, L=Melbourne, ST=Victoria, C=AU");
        when(mockSmdCertificate.getIssuerDN()).thenReturn(mockPrincipalTwo);
        when(mockPrincipalTwo.getName()).thenReturn("EMAILADDRESS=issuer@abc.com.au, " +
                "CN=issuer, OU=Oud, O=ARI, L=Melbourne, ST=Vic, C=AU");

        Calendar notAfterCal = DatatypeConverter.parseDate("2019-08-16T09:00:00Z");
        Calendar notBeforeCal = DatatypeConverter.parseDate("2009-08-16T09:00:00Z");
        when(mockSmdCertificate.getNotAfter()).thenReturn(notAfterCal.getTime());
        when(mockSmdCertificate.getNotBefore()).thenReturn(notBeforeCal.getTime());

        String expectedMessage = "Test message <<in1>>; "
                + "Certificate of serial number '7' and DN 'EMAILADDRESS=revoked@abc.com.au,"
                + " CN=revoked, OU=Oud, O=ARI, L=Melbourne, ST=Victoria, C=AU' and \nsigner DN"
                + " 'EMAILADDRESS=issuer@abc.com.au, CN=issuer, OU=Oud, O=ARI, L=Melbourne,"
                + " ST=Vic, C=AU'\nvalid from '2009-08-16T09:00:00Z' and valid to '2019-08-16T09:00:00Z'";

        String message = ErrorPkg.getMessage("test.msg.2", "<<in2>>", mockSmdCertificate);
        assertThat(message, is(expectedMessage));
    }

}
