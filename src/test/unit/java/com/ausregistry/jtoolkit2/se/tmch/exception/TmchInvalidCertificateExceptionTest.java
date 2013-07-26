package com.ausregistry.jtoolkit2.se.tmch.exception;

import com.ausregistry.jtoolkit2.ErrorPkg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.security.cert.CertPathValidatorException;
import java.security.cert.X509Certificate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorPkg.class})
public class TmchInvalidCertificateExceptionTest {
    @Test
    public void shouldReturnCorrectCertificate() {
        X509Certificate mockCertificate = mock(X509Certificate.class);
        assertThat(new TmchInvalidCertificateException(mockCertificate, null).getCertificate(),
                is(mockCertificate));
    }

    @Test
    public void shouldReturnCorrectMessage() {
        mockStatic(ErrorPkg.class);
        X509Certificate mockCertificate = mock(X509Certificate.class);
        when(ErrorPkg.getMessage("tmch.smd.cert.invalid", "<<cert-detailed-msg>>", mockCertificate))
                .thenReturn("message");
        TmchInvalidCertificateException exception =
                new TmchInvalidCertificateException(mockCertificate, null);
        assertThat(exception.getMessage(), is("message"));

    }

    @Test
    public void shouldEncapsulateItsCause() {
        Throwable mockCause = mock(CertPathValidatorException.class);
        Throwable exception = new TmchInvalidCertificateException(null,
                (CertPathValidatorException) mockCause);
        assertThat(exception.getCause(), is(mockCause));
    }
}
