package com.ausregistry.jtoolkit2.tmdb;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TmdbClientProperties.class)
public class TmdbClientPropertiesTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock private InputStream mockInputStream;
    @Mock private Properties mockProperties;

    private String fileName = "fileName";

    @Before
    public void setUp() throws Exception {
        mockStatic(Thread.class);

        mockInputStream = mock(InputStream.class);
        Thread mockCurrentThread = mock(Thread.class);
        when(Thread.currentThread()).thenReturn(mockCurrentThread);
        ClassLoader mockClassLoader = mock(ClassLoader.class);
        when(mockCurrentThread.getContextClassLoader()).thenReturn(mockClassLoader);
        when(mockClassLoader.getResourceAsStream(fileName)).thenReturn(mockInputStream);
        whenNew(Properties.class).withNoArguments().thenReturn(mockProperties);
    }

    @Test
    public void shouldThrowExceptionWhenPropertiesFileCouldNotBeFound() throws IOException {
        thrown.expect(FileNotFoundException.class);
        thrown.expectMessage("wrongFileName");
        new TmdbClientProperties("wrongFileName");
    }

    @Test
    public void shouldFillPropertiesFromInputStream() throws IOException {
        new TmdbClientProperties(fileName);
        verify(mockProperties).load(mockInputStream);
    }

    @Test
    public void shouldLoadCorrectServerUrlFromPropertiesFile() throws Exception {
        when(mockProperties.getProperty("tmdb.server.url")).thenReturn("tmdbServerUrl");

        TmdbClientProperties tmdbClientProperties = new TmdbClientProperties(fileName);
        assertThat(tmdbClientProperties.getTmdbServerUrl(), is("tmdbServerUrl"));
    }

    @Test
    public void shouldLoadCorrectTrustStoreFileNameFromPropertiesFile() throws Exception {
        when(mockProperties.getProperty("tmdb.truststore.location")).thenReturn("trustStoreLocation");

        TmdbClientProperties tmdbClientProperties = new TmdbClientProperties(fileName);
        assertThat(tmdbClientProperties.getTrustStoreFilename(), is("trustStoreLocation"));
    }

    @Test
    public void shouldLoadCorrectTrustStorePassPhraseFromPropertiesFile() throws Exception {
        when(mockProperties.getProperty("tmdb.truststore.pass")).thenReturn("trustStorePassPhrase");

        TmdbClientProperties tmdbClientProperties = new TmdbClientProperties(fileName);
        assertThat(tmdbClientProperties.getTrustStorePassphrase(), is("trustStorePassPhrase"));
    }

    @Test
    public void shouldLoadCorrectSocketTimeoutFromPropertiesFile() throws Exception {
        when(mockProperties.getProperty("tmdb.socket.timeout")).thenReturn("200");

        TmdbClientProperties tmdbClientProperties = new TmdbClientProperties(fileName);
        assertThat(tmdbClientProperties.getTmdbSocketTimeOut(), is(200));
    }
}

