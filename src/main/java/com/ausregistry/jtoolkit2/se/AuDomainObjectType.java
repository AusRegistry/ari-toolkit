package com.ausregistry.jtoolkit2.se;

/**
 * Represents the audomain-1.0 EPP extension object.
 * @deprecated Should instead use {@link ExtendedObjectType#AU_DOMAIN}.
 */
class AuDomainObjectType implements ObjectType {
    private static final long serialVersionUID = -543331887000261081L;

    public String getName() {
        return "audom";
    }

    public String getURI() {
        return "urn:X-au:params:xml:ns:audomain-1.0";
    }

    public String getSchemaLocation() {
        return "urn:X-au:params:xml:ns:audomain-1.0 audomain-1.0.xsd";
    }

    public String getIdentType() {
        return "name";
    }
}

