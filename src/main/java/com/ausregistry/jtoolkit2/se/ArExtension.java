package com.ausregistry.jtoolkit2.se;

/**
 * A bundled set of constants representing the AusRegistry EPP extension
 * schema.  The namespace URI uniquely identifies the extension.
 * @deprecated Should instead use {@link ExtensionImpl#AR}.
 */
class ArExtension implements Extension {
    private static final long serialVersionUID = -4359473950568658023L;

    /**
     * Get the prefix used by the toolkit for evaluating xpath expressions for
     * elements having this extension's namespace.
     */
    public String getPrefix() {
        return "arext";
    }

    /**
     * Get the globally unique namespace URI which identifies this extension.
     */
    public String getURI() {
        return "urn:X-ar:params:xml:ns:arext-1.0";
    }

    /**
     * Get the location hint for the XML schema used to validate EPP service
     * element instances using this extension.
     */
    public String getSchemaLocation() {
        return "urn:X-ar:params:xml:ns:arext-1.0 arext-1.0.xsd";
    }
}

