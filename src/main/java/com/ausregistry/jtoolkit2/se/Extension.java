package com.ausregistry.jtoolkit2.se;

/**
 * Represent features of EPP extensions of interest to the
 * toolkit library.
 */
public interface Extension extends java.io.Serializable {
    /**
     * Get the prefix used by the toolkit for evaluating xpath expressions for
     * elements having this extension's namespace.
     */
    String getPrefix();

    /**
     * Get the globally unique namespace URI which identifies this extension.
     */
    String getURI();

    /**
     * Get the location hint for the XML schema used to validate EPP service
     * element instances using this extension.
     */
    String getSchemaLocation();
}

