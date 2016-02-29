package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;

import org.w3c.dom.Element;

/**
 * Use this to request the update of a contact object provisioned in an EPP
 * Registry.  Instances of this class generate RFC5730 and RFC5733 compliant
 * contact update EPP command service elements via the toXML method.  The
 * response expected from a server should be handled by a Response object.
 *
 * @see com.ausregistry.jtoolkit2.se.Response
 */
public class ContactUpdateCommand extends UpdateCommand {
    private static final long serialVersionUID = -8110771660904052882L;

    /**
     * Minimal constructor for changing the authinfo of a contact.
     */
    public ContactUpdateCommand(String id, String pw) {
        this(id, pw, null, null, null, null, null, null, null, null,
                null, null);
    }

    /**
     * The complete set of attributes of a contact which may be updated as per
     * RFC5733.
     *
     * @throws IllegalArgumentException if {@code id} is {@code null}.
     */
    public ContactUpdateCommand(String id, String pw,
            Status[] addStatuses, String[] remStatuses,
            IntPostalInfo newIntPostalInfo, LocalPostalInfo newLocPostalInfo,
            String newVoice, String newVoiceExt, String newFax,
            String newFaxExt, String newEmail, Disclose disclose) {

        super(StandardObjectType.CONTACT, id);

        if (id == null) {
            throw new IllegalArgumentException(ErrorPkg.getMessage(
                        "se.contact.update.id.missing"));
        }

        if (addStatuses != null) {
            Element add = xmlWriter.appendChild(objElement, "add");

            for (Status status : addStatuses) {
                xmlWriter.appendChild(add, "status", status.getRationale(),
                        "s", status.toString());
            }
        }

        if (remStatuses != null) {
            Element rem = xmlWriter.appendChild(objElement, "rem");

            for (String status : remStatuses) {
                xmlWriter.appendChild(rem, "status", "s", status);
            }
        }

        if (pw == null && newIntPostalInfo == null && newLocPostalInfo == null
                && newVoice == null && newFax == null
                && newEmail == null && disclose == null) {
            return;
        }

        Element chg = xmlWriter.appendChild(objElement, "chg");

        if (newIntPostalInfo != null) {
            newIntPostalInfo.appendToElement(xmlWriter, chg);
        }

        if (newLocPostalInfo != null) {
            newLocPostalInfo.appendToElement(xmlWriter, chg);
        }

        if (newVoice != null) {
            Element voice = xmlWriter.appendChild(chg, "voice");
            if (newVoiceExt != null) {
                voice.setAttribute("x", newVoiceExt);
            }
            voice.setTextContent(newVoice);
        }

        if (newFax != null) {
            Element fax = xmlWriter.appendChild(chg, "fax");
            if (newFaxExt != null) {
                fax.setAttribute("x", newFaxExt);
            }
            fax.setTextContent(newFax);
        }

        if (newEmail != null) {
            xmlWriter.appendChild(chg, "email").setTextContent(newEmail);
        }

        if (pw != null) {
            xmlWriter.appendChild(
                    xmlWriter.appendChild(
                        chg,
                        "authInfo"),
                    "pw").setTextContent(pw);
        }

        if (disclose != null) {
            disclose.appendToElement(xmlWriter, chg);
        }
    }
}

