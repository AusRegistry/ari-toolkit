package com.ausregistry.jtoolkit2.se;

/**
 * A bundled set of constants representing the .au EPP extension
 * schema.  The namespace URI uniquely identifies the extension.
 * @deprecated Should instead use {@link ExtensionImpl#AU}.
 */
class AuExtension implements Extension {
    private static final long serialVersionUID = 9214781234610077483L;

    /**
     * Get the prefix used by the toolkit for evaluating xpath expressions for
     * elements having this extension's namespace.
     */
    public String getPrefix() {
        return "auext";
    }

    /**
     * Get the globally unique namespace URI which identifies this extension.
     */
    public String getURI() {
        return "urn:X-au:params:xml:ns:auext-1.2";
    }

    /**
     * Get the location hint for the XML schema used to validate EPP service
     * element instances using this extension.
     */
    public String getSchemaLocation() {
        return "urn:X-au:params:xml:ns:auext-1.2 auext-1.2.xsd";
    }
}

