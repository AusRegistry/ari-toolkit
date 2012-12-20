package com.ausregistry.jtoolkit2.se;

/**
 * Represents the aedomain-1.0 EPP extension object.
 * @deprecated Should instead use {@link ExtendedObjectType#AE_DOMAIN}.
 */
class AeDomainObjectType implements ObjectType {
    private static final long serialVersionUID = -543331887000261081L;

    public String getName() {
        return "aedom";
    }

    public String getURI() {
        return "urn:X-ae:params:xml:ns:aedomain-1.0";
    }

    public String getSchemaLocation() {
        return "urn:X-ae:params:xml:ns:aedomain-1.0 aedomain-1.0.xsd";
    }

    public String getIdentType() {
        return "name";
    }
}
