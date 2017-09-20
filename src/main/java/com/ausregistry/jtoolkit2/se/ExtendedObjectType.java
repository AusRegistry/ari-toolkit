package com.ausregistry.jtoolkit2.se;

/**
 * This defines the enumeration that maps an extension namespace alias to a namespace definition and the identifier tag
 * in the resulting XML.
 */
public enum ExtendedObjectType implements ObjectType {
    AR_DOMAIN("ardom", "urn:X-ar:params:xml:ns:ardomain-1.0", "ardomain-1.0.xsd", "name"),
    AE_DOMAIN("aedom", "urn:X-ae:params:xml:ns:aedomain-1.0", "aedomain-1.0.xsd", "name"),
    AU_DOMAIN("audom", "urn:X-au:params:xml:ns:audomain-1.1", "audomain-1.1.xsd", "name"),
    IDNA_DOMAIN("idnadomain", "urn:X-ar:params:xml:ns:idnadomain-1.0", "idnadomain-1.0.xsd", "name"),
    /** References the domain name variants extension (v1.0) XML namespace and schema file */
    VARIANT("variant", "urn:X-ar:params:xml:ns:variant-1.0", "variant-1.0.xsd", "name"),
    /** References the domain name variants extension (v1.1) XML namespace and schema file */
    VARIANT_V1_1("variantV1_1", "urn:ar:params:xml:ns:variant-1.1", "variant-1.1.xsd", "name"),
    /** References the secDNS extension XML namespace and schema file */
    SEC_DNS("secDNS", "urn:ietf:params:xml:ns:secDNS-1.1", "secDNS-1.1.xsd", "name"),
    SYNC("sync", "urn:X-ar:params:xml:ns:sync-1.0", "sync-1.0.xsd", "name"),
    /** References the key-value list extension XML namespace and schema file */
    KV("kv", "urn:X-ar:params:xml:ns:kv-1.0", "kv-1.0.xsd", "name"),
    KVV11("kvV1_1", "urn:X-ar:params:xml:ns:kv-1.1", "kv-1.1.xsd", "name"),
    REGISTRANT("registrant", "urn:X-ar:params:xml:ns:registrant-1.0", "registrant-1.0.xsd", "name"),
    /** References the domain restore (redemption grace period) extension XML namespace and schema file */
    RESTORE("restore", "urn:ietf:params:xml:ns:rgp-1.0", "rgp-1.0.xsd", "name"),
    /** References the domain application extension XML namespace and schema file */
    APP("app", "urn:ar:params:xml:ns:application-1.0", "application-1.0.xsd", "name"),
    /** References the internationalised domain name extension XML namespace and schema file */
    IDN("idn", "urn:ar:params:xml:ns:idn-1.0", "idn-1.0.xsd", "name"),
    TMCH("tmch", "urn:ar:params:xml:ns:tmch-1.0", "tmch-1.0.xsd", "name"),
    /** References the domain name price extension XML namespace and schema file */
    PRICE("price", "urn:ar:params:xml:ns:price-1.0", "price-1.0.xsd", "name"),
    /** References the domain name price extension XML namespace and schema file */
    PRICEV11("priceV1_1", "urn:ar:params:xml:ns:price-1.1", "price-1.1.xsd", "name"),
    /** References the domain name price extension XML namespace and schema file */
    PRICEV12("priceV1_2", "urn:ar:params:xml:ns:price-1.2", "price-1.2.xsd", "name"),
    SIGNED_MARK_DATA("smd", "urn:ietf:params:xml:ns:signedMark-1.0", "signedMark-1.0.xsd", "smd"),
    MARK("mark", "urn:ietf:params:xml:ns:mark-1.0", "mark-1.0.xsd", "mark"),
    XML_DSIG("ds", "http://www.w3.org/2000/09/xmldsig#", "xmldsig-core-schema.xsd", "ds"),
    /** References the extended domain check XML namespace and schema file */
    EX_AVAIL("exAvail", "urn:ar:params:xml:ns:exAvail-1.0", "exAvail-1.0.xsd", "name"),
    /** References the fund info XML namespace and schema file */
    FUND("fund", "urn:ar:params:xml:ns:fund-1.0", "fund-1.0.xsd"),
    /** References the Block XML namespace and schema file */
    BLOCK("block", "urn:ar:params:xml:ns:block-1.0", "block-1.0.xsd", "id"),
    /** References the Unspec XML namespace and schema file */
    UNSPEC("unspec", "urn:ietf:params:xml:ns:neulevel-1.0", "neulevel-1.0.xsd", "neulevel"),
    /** References the Launch XML namespace and schema file */
    LAUNCH("launch", "urn:ietf:params:xml:ns:launch-1.0", "launch-1.0.xsd", "name"),
    /** References the Fee XML namespace and schema file */
    FEE("fee", "urn:ietf:params:xml:ns:fee-0.6", "fee-0.6.xsd", "fee"),
    /** References the IETF IDN XML namespace and schema file */
    IETF_IDN("ietfIdn", "urn:ietf:params:xml:ns:idn-1.0", "ietf-idn-1.0.xsd", "ietfIdn"),
    /** References the Allocation Token XML namespace and schema file */
    ALLOCATION_TOKEN("allocationToken", "urn:ietf:params:xml:ns:allocationToken-1.0", "allocationToken-1.0.xsd",
            "allocationToken");

    private final String prefix, uri, schemaDefinition, identType;

    ExtendedObjectType(final String prefix, final String uri, final String schemaDefinition,
            final String identType) {
        this.prefix = prefix;
        this.uri = uri;
        this.schemaDefinition = schemaDefinition;
        this.identType = identType;
    }

    ExtendedObjectType(final String prefix, final String uri, final String schemaDefinition) {
        this.prefix = prefix;
        this.uri = uri;
        this.schemaDefinition = schemaDefinition;
        this.identType = null;
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
