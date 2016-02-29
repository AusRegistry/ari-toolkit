package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Use this to request that a contact object be provisioned in an EPP Registry.
 * Instances of this class generate RFC5730 and RFC5733 compliant contact
 * create EPP command service elements via the toXML method.
 *
 * @see com.ausregistry.jtoolkit2.se.ContactCreateResponse
 */
public class ContactCreateCommand extends CreateCommand {
    private static final long serialVersionUID = -3827811718418723589L;

    /**
     * Provision a contact with the specified details which constitute the
     * minimum valid parameters according to the EPP specification.
     *
     * @param id The new contact's identifier.
     *
     * @param pw The password to assign to the contact (also known as authInfo
     * or authorisation information).
     *
     * @param postalInfo The postal information for the new contact.  This may be
     * either of type IntPostalInfo or LocalPostalInfo.
     *
     * @param email The contact's email address.
     */
    public ContactCreateCommand(String id, String pw,
            PostalInfo postalInfo, String email) {

        this(id, pw, postalInfo, null, null, null, null, null, email,
                null);
    }

    /**
     * Provision a contact with the specified details.  This constructor allows
     * specification of any and all parameters for a contact create command.
     *
     * @param id The new contact's identifier. Required.
     *
     * @param pw The password to assign to the contact (also known as authInfo
     * or authorisation information). Required.
     *
     * @param postalInfo Postal information for the new contact.  If
     * localPostalInfo is also specified, then this MUST be IntPostalInfo.
     * Required if {@code localPostInfo} is not supplied.
     *
     * @param localPostalInfo Local postal information for the new contact.
     * Required if {@code postalInfo} is not supplied.
     *
     * @param voice The contact's voice telephone number.
     *
     * @param voiceExt The extension for the contact's voice telephone number,
     * if applicable.
     *
     * @param fax The contact's fax telephone number.
     *
     * @param faxExt The extension for the contact's fax telephone number, if
     * applicable.
     *
     * @param email The contact's email address. Required.
     *
     * @param disclose Disclosure request information, which may modify what
     * information is disclosed by the Registry system in response to queries.
     * Note that the server may not accept specification of this parameter, or
     * may ignore any requests described by this parameter.
     * @throws IllegalArgumentException if any required parameter is {@code null}.
     */
    public ContactCreateCommand(String id, String pw,
            PostalInfo postalInfo, LocalPostalInfo localPostalInfo,
            String voice, String voiceExt, String fax, String faxExt,
            String email, Disclose disclose) {

        super(StandardObjectType.CONTACT, id);

        if (id == null || pw == null
                || (postalInfo == null && localPostalInfo == null)
                || email == null) {
            throw new IllegalArgumentException(ErrorPkg.getMessage(
                        "se.contact.create.missing_arg"));
        }
        if (postalInfo instanceof IntPostalInfo) {
            IntPostalInfo ipi = (IntPostalInfo) postalInfo;
            ipi.appendToElement(xmlWriter, objElement);

            if (localPostalInfo != null) {
                localPostalInfo.appendToElement(xmlWriter, objElement);
            }
        } else if (postalInfo instanceof LocalPostalInfo) {
            LocalPostalInfo lpi = (LocalPostalInfo) postalInfo;
            lpi.appendToElement(xmlWriter, objElement);
        }

        if (voice != null) {
            if (voiceExt != null) {
                xmlWriter.appendChild(objElement, "voice", voice, "x", voiceExt);
            } else {
                xmlWriter.appendChild(objElement, "voice").setTextContent(voice);
            }
        }

        if (fax != null) {
            if (faxExt != null) {
                xmlWriter.appendChild(objElement, "fax", fax, "x", faxExt);
            } else {
                xmlWriter.appendChild(objElement, "fax").setTextContent(fax);
            }
        }

        xmlWriter.appendChild(objElement, "email").setTextContent(email);

        xmlWriter.appendChild(
                xmlWriter.appendChild(
                    objElement,
                    "authInfo"),
                "pw").setTextContent(pw);

        if (disclose != null) {
            disclose.appendToElement(xmlWriter, objElement);
        }
    }
}

