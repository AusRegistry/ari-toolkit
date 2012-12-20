package com.ausregistry.jtoolkit2.se;

/**
 * This defines the enumeration that maps an extension namespace alias to a namespace definition and the identifier tag
 * in the resulting XML.
 */
public enum ExtendedObjectType implements ObjectType {
    AR_DOMAIN("ardom", "urn:X-ar:params:xml:ns:ardomain-1.0", "ardomain-1.0.xsd", "name"),
    AE_DOMAIN("aedom", "urn:X-ae:params:xml:ns:aedomain-1.0", "aedomain-1.0.xsd", "name"),
    AU_DOMAIN("audom", "urn:X-au:params:xml:ns:audomain-1.0", "audomain-1.0.xsd", "name"),
    IDNA_DOMAIN("idnadomain", "urn:X-ar:params:xml:ns:idnadomain-1.0", "idnadomain-1.0.xsd", "name"),
    VARIANT("variant", "urn:X-ar:params:xml:ns:variant-1.0", "variant-1.0.xsd", "name"),
    SEC_DNS("secDNS", "urn:ietf:params:xml:ns:secDNS-1.1", "secDNS-1.1.xsd", "name"),
    SYNC("sync", "urn:X-ar:params:xml:ns:sync-1.0", "sync-1.0.xsd", "name"),
    KV("kv", "urn:X-ar:params:xml:ns:kv-1.0", "kv-1.0.xsd", "name"),
    REGISTRANT("registrant", "urn:X-ar:params:xml:ns:registrant-1.0", "registrant-1.0.xsd", "name"),
    RESTORE("restore", "urn:ietf:params:xml:ns:rgp-1.0", "rgp-1.0.xsd", "name"), 
    LAUNCH("launch", "urn:rbp:params:xml:ns:application-1.0", "launch-1.0.xsd", "name");

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
