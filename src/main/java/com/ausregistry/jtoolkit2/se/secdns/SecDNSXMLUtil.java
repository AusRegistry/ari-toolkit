package com.ausregistry.jtoolkit2.se.secdns;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * This defines the operations to facilitate adding child elements to a XML Document.
 */
public final class SecDNSXMLUtil {

    public static final String SEC_DNS_PREFIX = ExtendedObjectType.SEC_DNS.getName();

    private SecDNSXMLUtil() {
        // intentionally do nothing, make checkstyle happy
    }

    public static void appendChildElement(final XMLWriter xmlWriter, final Element parentElement, final String name,
            final int value) {
        appendChildElement(xmlWriter, parentElement, name, "" + value);
    }

    public static void appendChildElement(final XMLWriter xmlWriter, final Element parentElement, final String name,
            final boolean value) {
        appendChildElement(xmlWriter, parentElement, name, value ? "true" : "false");
    }

    public static void appendChildElement(final XMLWriter xmlWriter, final Element parentElement, final String name,
            final String value) {
        xmlWriter.appendChild(parentElement, name, ExtendedObjectType.SEC_DNS.getURI()).setTextContent(value);
    }

    public static Element createElement(final XMLWriter xmlWriter, final Element parentElement, final String name) {
        return xmlWriter.appendChild(parentElement, name, ExtendedObjectType.SEC_DNS.getURI());
    }

}
