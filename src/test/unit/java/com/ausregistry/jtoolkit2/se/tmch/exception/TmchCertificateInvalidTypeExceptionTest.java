package com.ausregistry.jtoolkit2.se.tmch.exception;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.security.cert.Certificate;

import com.ausregistry.jtoolkit2.ErrorPkg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorPkg.class})
public class TmchCertificateInvalidTypeExceptionTest {
    @Test
    public void shouldReturnCorrectMessage() {
        mockStatic(ErrorPkg.class);
        when(ErrorPkg.getMessage("tmch.cert.invalid.type", "<<provided-type>>", "Certificate")).thenReturn("message");
        TmchCertificateInvalidTypeException exception = new TmchCertificateInvalidTypeException(Certificate.class);
        assertThat(exception.getMessage(), is("message"));
    }
}
