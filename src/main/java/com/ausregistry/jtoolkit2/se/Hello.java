package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XmlOutputConfig;

/**
 * Use this to request service information in the form of an EPP greeting
 * from an EPP server.  Instances of this class generate via the toXML method
 * hello service elements compliant with the specification of hello in RFC5730.
 */
public final class Hello extends SendSE {
    private static final long serialVersionUID = -121313711512679943L;

    public Hello() {
        super();

        xmlWriter.appendChild(xmlWriter.getRoot(), "hello");
    }

    protected String toXMLImpl(XmlOutputConfig xmlOutputConfig) throws org.xml.sax.SAXException {
        return xmlWriter.toXML(xmlOutputConfig);
    }
}

