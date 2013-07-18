package com.ausregistry.jtoolkit2.tmdb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TmdbClientProperties {
    private final Properties properties = new Properties();

    public TmdbClientProperties(String tmdbPropertiesFileName) throws IOException {
        InputStream inputStream = null;

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(tmdbPropertiesFileName);
            if (inputStream == null) {
                throw new FileNotFoundException(tmdbPropertiesFileName);
            }

            properties.load(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public String getTmdbServerUrl() {
        return properties.getProperty("tmdb.server.url");
    }

    public String getTrustStoreFilename() {
        return properties.getProperty("tmdb.truststore.location");
    }

    public String getTrustStorePassphrase() {
        return properties.getProperty("tmdb.truststore.pass");
    }

    public Integer getTmdbSocketTimeOut() {
        return Integer.parseInt(properties.getProperty("tmdb.socket.timeout"));
    }
}
