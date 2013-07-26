package com.ausregistry.jtoolkit2.se.tmch.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class InvalidSignedMarkDataExceptionTest {
    @Test
    public void shouldThrowExceptionWithTheRightMessage() {
        InvalidSignedMarkDataException exception = new InvalidSignedMarkDataException();

        assertThat(exception.getMessage(), is("Invalid SignedMarkData provided."));
    }

    @Test
    public void shouldThrowExceptionWithTheRightMessageAndCause() {
        Throwable mockCause = mock(Throwable.class);
        InvalidSignedMarkDataException exception = new InvalidSignedMarkDataException(mockCause);

        assertThat(exception.getMessage(), is("Invalid SignedMarkData provided."));
        assertThat(exception.getCause(), is(mockCause));
    }

}
