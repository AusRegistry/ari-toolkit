package com.ausregistry.jtoolkit2.se;

/**
 * A bundled set of constants representing the E164 EPP extension
 * schema (ENUM).  The namespace URI uniquely identifies the extension.
 * @deprecated Should instead use {@link ExtensionImpl#E164}.
 */
class E164Extension implements Extension {
    private static final long serialVersionUID = -5095441961150758748L;

    /**
     * Get the prefix used by the toolkit for evaluating xpath expressions for
     * elements having this extension's namespace.
     */
    public String getPrefix() {
        return "e164epp";
    }

    /**
     * Get the globally unique namespace URI which identifies this extension.
     */
    public String getURI() {
        return "urn:ietf:params:xml:ns:e164epp-1.0";
    }

    /**
     * Get the location hint for the XML schema used to validate EPP service
     * element instances using this extension.
     */
    public String getSchemaLocation() {
        return "urn:ietf:params:xml:ns:e164epp-1.0 e164epp-1.0.xsd";
    }
}

