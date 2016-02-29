package com.ausregistry.jtoolkit2.se;

/**
 * Represents the ardomain-1.0 EPP extension object.
 * @deprecated Should instead use {@link ExtendedObjectType#AR_DOMAIN}.
 */
class ArDomainObjectType implements ObjectType {
    private static final long serialVersionUID = 992842005698675196L;

    public String getName() {
        return "ardom";
    }

    public String getURI() {
        return "urn:X-ar:params:xml:ns:ardomain-1.0";
    }

    public String getSchemaLocation() {
        return "urn:X-ar:params:xml:ns:ardomain-1.0 ardomain-1.0.xsd";
    }

    public String getIdentType() {
        return "name";
    }
}

