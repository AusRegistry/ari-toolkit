package com.ausregistry.jtoolkit2.se;

/**
 * This defines the enumeration that maps an extension namespace alias to a namespace definition and the identifier tag
 * in the resulting XML.
 */
public enum ExtendedObjectType implements ObjectType {
    VARIANT("variant", "urn:X-ar:params:xml:ns:variant-1.0", "variant-1.0.xsd", "name"),
    SEC_DNS("secDNS", "urn:ietf:params:xml:ns:secDNS-1.1", "secDNS-1.1.xsd", "name"),
    KV("kv", "urn:X-ar:params:xml:ns:kv-1.0", "kv-1.0.xsd", "name");

    private String prefix, uri, schemaDefinition, identType;

    ExtendedObjectType(final String prefix, final String uri, final String schemaDefinition,
            final String identType) {
        this.prefix = prefix;
        this.uri = uri;
        this.schemaDefinition = schemaDefinition;
        this.identType = identType;
    }

    @Override
    public String getName() {
        return prefix;
    }

    @Override
    public String getURI() {
        return uri;
    }

    @Override
    public String getSchemaLocation() {
        return uri + " " + schemaDefinition;
    }

    public String getSchemaDefinition() {
        return schemaDefinition;
    }

    @Override
    public String getIdentType() {
        return identType;
    }
}
