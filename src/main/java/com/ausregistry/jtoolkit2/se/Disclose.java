package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

import org.w3c.dom.Element;

/**
 * Disclosure preferences are configured via an instance of this class.  This
 * class is an interface to the EPP disclose element which is described in
 * RFC5733 where uses of the element are also described.  Contact information
 * disclosure preferences may be set via contact transform operations,
 * implemented in such classes as ContactCreateCommand and
 * ContactUpdateCommand.
 *
 * @see com.ausregistry.jtoolkit2.se.ContactCreateCommand
 * @see com.ausregistry.jtoolkit2.se.ContactUpdateCommand
 */
public class Disclose implements Appendable {
    private static final long serialVersionUID = -6183960266526804249L;
    private String allow;
    private boolean nameIntSet;
    private boolean nameLocSet;
    private boolean orgIntSet;
    private boolean orgLocSet;
    private boolean addrIntSet;
    private boolean addrLocSet;
    private boolean voiceSet;
    private boolean faxSet;
    private boolean emailSet;
    private boolean noneSet = true;

    /**
     * Construct a Disclose object with all items not yet set.  This is an invalid
     * final state for an EPP disclose element, requiring at least one setX method
     * to be invoked on the instance prior to a transform command using the Disclose
     * object.
     *
     * @param allow Whether or not elements to be set later via setX should be
     * disclosed or not.  This is only a request to the server and may not be
     * honoured.
     */
    public Disclose(boolean allow) {
        this(allow, false, false, false, false, false, false, false, false,
                false);
    }

    /**
     * Construct a Disclose object with the specified items set.  A value of
     * true indicates that the item should be specified in the disclose
     * element, while false simply leaves the disclosure status of the item to
     * server policy.
     */
    public Disclose(boolean allow, boolean nameInt, boolean nameLoc,
            boolean orgInt, boolean orgLoc, boolean addrInt, boolean addrLoc,
            boolean voice, boolean fax, boolean email) {

        this.allow = allow ? "1" : "0";
    }

    public void setVoice() {
        voiceSet = true;
        noneSet = false;
    }

    public void setFax() {
        faxSet = true;
        noneSet = false;
    }

    public void setEmail() {
        emailSet = true;
        noneSet = false;
    }

    public void setNameInt() {
        nameIntSet = true;
        noneSet = false;
    }

    public void setNameLoc() {
        nameLocSet = true;
        noneSet = false;
    }

    public void setOrgInt() {
        orgIntSet = true;
        noneSet = false;
    }

    public void setOrgLoc() {
        orgLocSet = true;
        noneSet = false;
    }

    public void setAddrInt() {
        addrIntSet = true;
        noneSet = false;
    }

    public void setAddrLoc() {
        addrLocSet = true;
        noneSet = false;
    }

    /**
     * This is an internal method - do not use.  Default behaviour if no
     * disclosure types are set is to insert a voice element as a child of the
     * disclose element.
     */
    public Element appendToElement(XMLWriter xmlWriter, Element parent) {
        Element disclose = xmlWriter.appendChild(parent, "disclose",
                "flag", allow);

        @SuppressWarnings("unused")
            Element e;
        if (noneSet) {
            xmlWriter.appendChild(disclose, "voice");
            return disclose;
        }
        e = nameIntSet ? xmlWriter.appendChild(disclose, "name", "type", "int") : null;
        e = nameLocSet ? xmlWriter.appendChild(disclose, "name", "type", "loc") : null;
        e = orgIntSet ? xmlWriter.appendChild(disclose, "org", "type", "int") : null;
        e = orgLocSet ? xmlWriter.appendChild(disclose, "org", "type", "loc") : null;
        e = addrIntSet ? xmlWriter.appendChild(disclose, "addr", "type", "int") : null;
        e = addrLocSet ? xmlWriter.appendChild(disclose, "addr", "type", "loc") : null;
        e = voiceSet ? xmlWriter.appendChild(disclose, "voice") : null;
        e = faxSet ? xmlWriter.appendChild(disclose, "fax") : null;
        e = emailSet ? xmlWriter.appendChild(disclose, "email") : null;

        return disclose;
    }
}

