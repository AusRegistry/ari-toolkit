package com.ausregistry.jtoolkit2.se;

/**
 * A bundled set of constants representing the .ae EPP extension
 * schema.  The namespace URI uniquely identifies the extension.
 *
 * @deprecated Should instead use {@link ExtensionImpl#AE}.
 */
class AeExtension implements Extension {
    private static final long serialVersionUID = -4699631227985495329L;

    /**
     * Get the prefix used by the toolkit for evaluating xpath expressions for
     * elements having this extension's namespace.
     */
    public String getPrefix() {
        return "aeext";
    }

    /**
     * Get the globally unique namespace URI which identifies this extension.
     */
    public String getURI() {
        return "urn:X-ae:params:xml:ns:aeext-1.0";
    }

    /**
     * Get the location hint for the XML schema used to validate EPP service
     * element instances using this extension.
     */
    public String getSchemaLocation() {
        return "urn:X-ae:params:xml:ns:aeext-1.0 aeext-1.0.xsd";
    }
}

