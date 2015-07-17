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
    E164("e164epp", "urn:ietf:params:xml:ns:e164epp-1.0", "e164epp-1.0.xsd"),
    AR("arext", "urn:X-ar:params:xml:ns:arext-1.0", "arext-1.0.xsd"),
    AE("aeext", "urn:X-ae:params:xml:ns:aeext-1.0", "aeext-1.0.xsd"),
    AU("auext", "urn:X-au:params:xml:ns:auext-1.2", "auext-1.2.xsd"),
    AU_V1("auext1", "urn:au:params:xml:ns:auext-1.0", "auext-1.0.xsd"),
    VIEXT("viext", "urn:X-ar:params:xml:ns:viext-1.0", "viext-1.0.xsd");

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
