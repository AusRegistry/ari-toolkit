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
public class TmchSmdRevokedExceptionTest {

    @Test
    public void shouldReturnCorrectId() {
        assertThat(new TmchSmdRevokedException("ID").getSmdId(), is("ID"));
    }

    @Test
    public void shouldReturnCorrectMessage() {
        mockStatic(ErrorPkg.class);

        when(ErrorPkg.getMessage("tmch.smd.revoked", "<<id>>", "ID")).thenReturn("message");
        TmchSmdRevokedException exception = new TmchSmdRevokedException("ID");
        assertThat(exception.getMessage(), is("message"));

    }
}
