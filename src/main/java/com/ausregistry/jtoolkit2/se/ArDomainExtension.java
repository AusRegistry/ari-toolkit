package com.ausregistry.jtoolkit2.se;

/**
 * A bundled set of constants representing the mapping of AusRegistry protocol
 * extension commands to domain objects.  The namespace URI uniquely identifies
 * the extension.
 * @deprecated Should instead use {@link ExtendedObjectType#AR_DOMAIN}.
 */
class ArDomainExtension implements Extension {
    private static final long serialVersionUID = 199379922940104083L;

    /**
     * Get the prefix used by the toolkit for evaluating xpath expressions for
     * elements having this extension's namespace.
     */
    public String getPrefix() {
        return "ardom";
    }

    /**
     * Get the globally unique namespace URI which identifies this extension.
     */
    public String getURI() {
        return "urn:X-ar:params:xml:ns:ardomain-1.0";
    }

    /**
     * Get the location hint for the XML schema used to validate EPP service
     * element instances using this extension.
     */
    public String getSchemaLocation() {
        return "urn:X-ar:params:xml:ns:ardomain-1.0 ardomain-1.0.xsd";
    }
}

