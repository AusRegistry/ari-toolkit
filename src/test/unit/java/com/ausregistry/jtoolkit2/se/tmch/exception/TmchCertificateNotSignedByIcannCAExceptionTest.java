package com.ausregistry.jtoolkit2.se.tmch.exception;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.security.cert.CertPathValidatorException;
import java.security.cert.X509Certificate;

import com.ausregistry.jtoolkit2.ErrorPkg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorPkg.class})
public class TmchCertificateNotSignedByIcannCAExceptionTest {
    @Test
    public void shouldReturnCorrectCertificate() {
        X509Certificate mockCertificate = mock(X509Certificate.class);
        assertThat(new TmchCertificateNotSignedByIcannCAException(mockCertificate, null).getCertificate(),
                is(mockCertificate));
    }

    @Test
    public void shouldReturnCorrectMessage() {
        mockStatic(ErrorPkg.class);
        X509Certificate mockCertificate = mock(X509Certificate.class);
        when(ErrorPkg.getMessage("tmch.smd.cert.notSignedByIcannCA", "<<cert-detailed-msg>>", mockCertificate))
                .thenReturn("message");
        TmchCertificateNotSignedByIcannCAException exception =
                new TmchCertificateNotSignedByIcannCAException(mockCertificate, null);
        assertThat(exception.getMessage(), is("message"));

    }

    @Test
    public void shouldEncapsulateItsCause() {
        Throwable mockCause = mock(CertPathValidatorException.class);
        Throwable exception = new TmchCertificateNotSignedByIcannCAException(null,
                (CertPathValidatorException) mockCause);
        assertThat(exception.getCause(), is(mockCause));
    }
}
