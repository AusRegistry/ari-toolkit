package com.ausregistry.jtoolkit2.se;

/**
 * A bundled set of constants representing the .au EPP extension
 * schema.  The namespace URI uniquely identifies the extension.
 * @deprecated Should instead use {@link ExtensionImpl#AU_V1}.
 */
class AuExtensionV1 implements Extension {
    private static final long serialVersionUID = 9214786434610077531L;

    /**
     * Get the prefix used by the toolkit for evaluating xpath expressions for
     * elements having this extension's namespace.
     */
    public String getPrefix() {
        return "auext1";
    }

    /**
     * Get the globally unique namespace URI which identifies this extension.
     */
    public String getURI() {
        return "urn:au:params:xml:ns:auext-1.0";
    }

    /**
     * Get the location hint for the XML schema used to validate EPP service
     * element instances using this extension.
     */
    public String getSchemaLocation() {
        return "urn:au:params:xml:ns:auext-1.0 auext-1.0.xsd";
    }
}

