package com.ausregistry.jtoolkit2.se;

/**
 * Represent features of EPP extensions of interest to the toolkit library,
 * implemented as an enum class.
 */

/*
 * This class should probably be named Extension, with the Extension interface
 * being renamed or removed. This may not be feasible if the existing interface
 * and classes are already in common use.
 */

public enum ExtensionImpl implements Extension {
    ;
    private final String prefix;
    private final String schemaDefinition;
    private final String uri;

    ExtensionImpl(final String prefix, final String uri, final String schemaDefinition) {
        this.prefix = prefix;
        this.schemaDefinition = schemaDefinition;
        this.uri = uri;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSchemaLocation() {
        return uri + " " + schemaDefinition;
    }

    public String getURI() {
        return uri;
    }

    public String getSchemaDefinition() {
        return schemaDefinition;
    }
}
