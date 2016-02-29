package com.ausregistry.jtoolkit2.se;

/**
 * A bundled set of constants representing the .au domain mapping of au
 * protocol extension commands.  The namespace URI uniquely identifies the
 * extension.
 * @deprecated Should instead use {@link ExtendedObjectType#AU_DOMAIN}.
 */
class AuDomainExtension implements Extension {
    private static final long serialVersionUID = 9218811235040104083L;

    /**
     * Get the prefix used by the toolkit for evaluating xpath expressions for
     * elements having this extension's namespace.
     */
    public String getPrefix() {
        return "audom";
    }

    /**
     * Get the globally unique namespace URI which identifies this extension.
     */
    public String getURI() {
        return "urn:X-au:params:xml:ns:audomain-1.0";
    }

    /**
     * Get the location hint for the XML schema used to validate EPP service
     * element instances using this extension.
     */
    public String getSchemaLocation() {
        return "urn:X-au:params:xml:ns:audomain-1.0 audomain-1.0.xsd";
    }
}

