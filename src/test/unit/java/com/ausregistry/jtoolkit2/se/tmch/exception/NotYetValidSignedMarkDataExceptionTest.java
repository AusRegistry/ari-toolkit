package com.ausregistry.jtoolkit2.se.tmch.exception;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Date;

import com.ausregistry.jtoolkit2.ErrorPkg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorPkg.class})
public class NotYetValidSignedMarkDataExceptionTest {
    @Test
    public void shouldReturnCorrectDate() {
        Date mockDate = mock(Date.class);
        assertThat(new NotYetValidSignedMarkDataException(mockDate).getValidFromDate(), is(mockDate));
    }

    @Test
    public void shouldReturnCorrectMessage() {
        mockStatic(ErrorPkg.class);
        Date mockDate = mock(Date.class);
        when(ErrorPkg.getMessage("tmch.smd.notYetValid", "<<valid-from-date>>", mockDate)).thenReturn("message");
        NotYetValidSignedMarkDataException exception = new NotYetValidSignedMarkDataException(mockDate);
        assertThat(exception.getMessage(), is("message"));

    }
}
