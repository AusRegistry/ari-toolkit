package com.ausregistry.jtoolkit2.se;

/**
 * This defines the enumeration that maps a EPP namespace alias to a namespace definition and the identifier tag in the
 * resulting XML.
 */
public enum StandardObjectType implements ObjectType {
    HOST("host", "urn:ietf:params:xml:ns:host-1.0", "host-1.0.xsd", "name"),
    CONTACT("contact", "urn:ietf:params:xml:ns:contact-1.0", "contact-1.0.xsd", "id"),
    DOMAIN("domain", "urn:ietf:params:xml:ns:domain-1.0", "domain-1.0.xsd", "name");

    private static String[] stdURIs;
    private String name, uri, schemaDefinition, identType;

    static {
        stdURIs = new String[] {
            DOMAIN.getURI(),
            HOST.getURI(),
            CONTACT.getURI()
        };
    }

    StandardObjectType(final String name, final String uri, final String schemaDefinition,
            final String identType) {

        this.name = name;
        this.uri = uri;
        this.schemaDefinition = schemaDefinition;
        this.identType = identType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getURI() {
        return uri;
    }

    @Override
    public String getSchemaLocation() {
        return uri + " " + schemaDefinition;
    }

    public String getSchemaDefintion() {
        return schemaDefinition;
    }

    @Override
    public String getIdentType() {
        return identType;
    }

    public static String[] getStandardURIs() {
        return stdURIs;
    }
}

