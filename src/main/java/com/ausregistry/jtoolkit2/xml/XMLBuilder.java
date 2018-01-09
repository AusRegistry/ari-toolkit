package com.ausregistry.jtoolkit2.xml;

/**
 * Package internal interface defining a standard signature for serializing a
 * DOM tree to an XML String.
 */
public interface XMLBuilder {
    String toXML(org.w3c.dom.Element root, XmlOutputConfig xmlOutputConfig);
}
