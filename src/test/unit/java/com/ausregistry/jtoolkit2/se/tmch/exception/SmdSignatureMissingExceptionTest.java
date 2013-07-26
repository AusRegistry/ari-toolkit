package com.ausregistry.jtoolkit2.se.tmch.exception;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.ausregistry.jtoolkit2.ErrorPkg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorPkg.class})
public class SmdSignatureMissingExceptionTest {
    @Test
    public void shouldReturnCorrectMessage() {
        mockStatic(ErrorPkg.class);
        when(ErrorPkg.getMessage("tmch.smd.signature.missing")).thenReturn("message");
        SmdSignatureMissingException exception = new SmdSignatureMissingException();
        assertThat(exception.getMessage(), is("message"));
    }
}
