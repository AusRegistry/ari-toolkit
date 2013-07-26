package com.ausregistry.jtoolkit2.se.tmch.exception;

import com.ausregistry.jtoolkit2.ErrorPkg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Date;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorPkg.class})
public class ExpiredSignedMarkDataExceptionTest {
    @Test
    public void shouldReturnCorrectDate() {
        Date mockDate = mock(Date.class);
        assertThat(new ExpiredSignedMarkDataException(mockDate).getNotValidAfterDate(), is(mockDate));
    }

    @Test
    public void shouldReturnCorrectMessage() {
        mockStatic(ErrorPkg.class);
        Date mockDate = mock(Date.class);
        when(ErrorPkg.getMessage("tmch.smd.expired", "<<expiry-date>>", mockDate)).thenReturn("message");
        ExpiredSignedMarkDataException exception = new ExpiredSignedMarkDataException(mockDate);
        assertThat(exception.getMessage(), is("message"));

    }

}
