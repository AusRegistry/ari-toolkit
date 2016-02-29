package com.ausregistry.jtoolkit2.se;

/**
 * Disclosure preferences are viewed via an instances of this class.  This
 * class is an interface to the EPP disclose element which is described in
 * RFC5733, where uses of the element are also described.  Contact information
 * disclosure status may be found in the result data of a command info
 * response, implemented in the ContactInfoResponse class.
 *
 * @see com.ausregistry.jtoolkit2.se.ContactInfoCommand
 * @see com.ausregistry.jtoolkit2.se.ContactInfoResponse
 */
public class DiscloseItem implements java.io.Serializable {
    private static final long serialVersionUID = -7503075321824634366L;
    private String name;
    private String type;

    public DiscloseItem(String elementName) {
        this.name = elementName;
    }

    public DiscloseItem(String elementName, String type) {
        this(elementName);
        this.type = type;
    }

    public String getElementName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

