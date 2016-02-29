package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

import org.w3c.dom.Element;

/**
 * This class models postal information of contact objects.  Instances may be
 * used to either transform postal information or access attributes of postal
 * information obtained by querying a contact object via a contact info EPP
 * command, the response to which is implemented in the class
 * ContactInfoResponse.
 */
public abstract class PostalInfo implements Appendable {
    private static final long serialVersionUID = -8790808568589212577L;

    private String type;
    private String name;
    private String org;
    private String[] street;
    private String city;
    private String sp;
    private String pc;
    private String cc;

    /**
     * Minimal information required as per RFC5733 for creation of a contact.
     */
    protected PostalInfo(PostalInfoType type, String name, String city, String countryCode) {
        this(type, name, null, null, city, null, null, countryCode);
    }

    /**
     * All fields defined in RFC5733 for postalInfoType.
     */
    protected PostalInfo(PostalInfoType type, String name, String org,
            String[] street, String city, String stateProv, String postcode,
            String countryCode) {

        assert type != null;
        assert name != null;
        assert city != null;
        assert countryCode != null;
        this.type = type.toString();
        this.name = name;
        this.org = org;
        if (street != null) {
            this.street = street.clone();
        }
        this.city = city;
        this.sp = stateProv;
        this.pc = postcode;
        this.cc = countryCode;
    }

    public Element appendToElement(XMLWriter xmlWriter, Element parent) {
        Element postalInfo = xmlWriter.appendChild(parent, "postalInfo", "type", type);
        xmlWriter.appendChild(postalInfo, "name").setTextContent(name);
        if (org != null) {
            xmlWriter.appendChild(postalInfo, "org").setTextContent(org);
        }

        Element addr = xmlWriter.appendChild(postalInfo, "addr");
        if (street != null) {
            for (String s : street) {
                xmlWriter.appendChild(addr, "street").setTextContent(s);
            }
        }
        xmlWriter.appendChild(addr, "city").setTextContent(city);
        if (sp != null) {
            xmlWriter.appendChild(addr, "sp").setTextContent(sp);
        }
        if (pc != null) {
            xmlWriter.appendChild(addr, "pc").setTextContent(pc);
        }
        xmlWriter.appendChild(addr, "cc").setTextContent(cc);

        return postalInfo;
    }

    public String getCountryCode() {
        return cc;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getOrganisation() {
        return org;
    }

    public String getPostcode() {
        return pc;
    }

    public String getSp() {
        return sp;
    }

    public String[] getStreet() {
        return street;
    }

    public String getType() {
        return type;
    }
}

