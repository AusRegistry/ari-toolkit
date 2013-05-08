package com.ausregistry.jtoolkit2.se.tmch;

/**
 * Represents a contact's type from the Trademark Clearing House extension,
 * represented by the "contactType" element of type "contactTypeType"
 * in the "urn:ietf:params:xml:ns:mark-1.0" namespace, defined in the "mark-1.0.xsd" schema.
 *
 */
public enum MarkContactType {
    owner, agent, thirdparty;
}
