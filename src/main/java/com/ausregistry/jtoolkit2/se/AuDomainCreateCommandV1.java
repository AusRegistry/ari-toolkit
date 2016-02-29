package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;
import org.w3c.dom.Element;

/**
 * Extension of EPP urn:ietf:params:xml:ns:domain-1.0 create command specified
 * in RFC5731 to urn:au:params:xml:ns:auext-1.0.  .au domains must be
 * provisioned using this class rather than {@link
 * com.ausregistry.jtoolkit2.se.DomainCreateCommand}, as the au extension data
 * is mandatory.
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The toXML method in Command serialises this object to
 * XML.
 * The response expected from a server should be handled by a {@link
 * com.ausregistry.jtoolkit2.se.DomainCreateResponse} object.
 */
public final class AuDomainCreateCommandV1 extends DomainCreateCommand {
    private static final long serialVersionUID = -8632740108172182234L;

    /**
     * Minimal constructor for creating a domain:create + auext:create
     * EPP command.  These parameters are the least required for a valid
     * .au domain create command.
     *
     * @throws IllegalArgumentException if any required parameters are {@code null}.
     */
    public AuDomainCreateCommandV1(String name, String pw,
                                   String registrantID, String[] techContacts,
                                   String auEligibilityType, int auPolicyReason,
                                   String auRegistrantName) {
        super(name, pw, registrantID, techContacts);

        setExtension(auEligibilityType, auPolicyReason, auRegistrantName);
    }

    /**
     * Full data specification constructor for a domain:create + auext:create
     * EPP command.  Please refer to the urn:au:params:xml:ns:auext-1.2 schema
     * for specification of the required fields.
     * The mapping of parameter names to au extension fields is given in the
     * parameter documentation.
     *
     * @param name                The name of the new domain.
     * @param pw                  The password to assign to the domain (also known as authInfo
     *                            or authorisation information).
     * @param registrantID        The identifier of an existing contact to assign as
     *                            the registrant contact for this domain.  Failure to ensure the contact
     *                            exists prior to using them in this way will result in an EPP result of
     *                            '2303 "Object does not exist"'.
     * @param techContacts        The identifiers of existing contacts to assign as
     *                            technical contacts for this domain.  Failure to ensure the contacts
     *                            exist prior to using them in this way will result in an EPP result of
     *                            '2303 "Object does not exist"'.
     * @param adminContacts       See techContacts (substitute administrative for
     *                            technical).
     * @param billingContacts     See techContacts (substitute billing for
     *                            technical).
     * @param nameservers         The names of existing hosts to delegate the domain
     *                            being created to.  Failure to ensure the hosts exist prior to using them
     *                            in this way will result in an EPP result of '2303  "Object does not
     *                            exist"'.
     * @param auEligibilityType   auext:eligType. Required.
     * @param auPolicyReason      auext:policyReason.
     * @param auRegistrantName    auext:registrantName. Required.
     * @param auRegistrantID      auext:registrantID.
     * @param auRegistrantIDType  auext:registrantID type attribute.
     * @param auEligibilityName   auext:eligibilityName.
     * @param auEligibilityID     auext:eligibilityID.
     * @param auEligibilityIDType auext:eligibilityID type attribute.
     * @throws IllegalArgumentException if {@code auEligibilityType} or {@code auRegistrantName} is {@code null}.
     */
    public AuDomainCreateCommandV1(String name, String pw,
                                   String registrantID, String[] techContacts, String[] adminContacts,
                                   String[] billingContacts, String[] nameservers,
                                   String auEligibilityType, int auPolicyReason,
                                   String auRegistrantName, String auRegistrantID,
                                   String auRegistrantIDType, String auEligibilityName,
                                   String auEligibilityID, String auEligibilityIDType) {
        super(name, pw, registrantID,
                techContacts, adminContacts, billingContacts,
                nameservers, null);

        setExtension(auEligibilityType, auPolicyReason,
                auRegistrantName, auRegistrantID, auRegistrantIDType,
                auEligibilityName, auEligibilityID, auEligibilityIDType);
    }

    private void setExtension(
            String eligibilityType,
            int policyReason,
            String registrantName) {
        setExtension(eligibilityType, policyReason,
                registrantName, null, null,
                null, null, null);
    }

    /**
     * &lt;extension&gt;
     * &nbsp;&lt;create xmlns="urn:au:params:xml:ns:auext-1.2"
     *     xsi:schemaLocation="urn:au:params:xml:ns:auext-1.2 auext.1.2.xsd"&gt;
     * &nbsp;&nbsp;&lt;registrantName&gt;registrantName&lt;/registrantName&gt;
     * &nbsp;&nbsp;&lt;registrantID type="registrantIDType"&gt;registrantID&lt;/registrantID&gt;
     * &nbsp;&nbsp;&lt;eligibilityType&gt;eligibilityType&lt;/eligibilityType&gt;
     * &nbsp;&nbsp;&lt;eligibilityName&gt;eligibilityName&lt;/eligibilityName&gt;
     * &nbsp;&nbsp;&lt;eligibilityID type="eligibilityIDType"&gt;eligibilityID&lt;/eligibilityID&gt;
     * &nbsp;&nbsp;&lt;policyReason&gt;policyReason&lt;/policyReason&gt;
     * &nbsp;&lt;/create&gt;
     * &lt;/extension&gt;
     */
    private void setExtension(
            String eligibilityType,
            int policyReason,
            String registrantName,
            String registrantID,
            String registrantIDType,
            String eligibilityName,
            String eligibilityID,
            String eligibilityIDType) {

        if (eligibilityType == null || registrantName == null) {
            throw new IllegalArgumentException(ErrorPkg.getMessage("se.domain.create.au.missing_arg"));
        }

        assert (registrantID == null && registrantIDType == null)
                || (registrantID != null && registrantIDType != null);

        assert (eligibilityID == null && eligibilityIDType == null)
                || (eligibilityID != null && eligibilityIDType != null);

        extension = xmlWriter.appendChild(command, "extension");
        Element extensionElement = xmlWriter.appendChild(extension, "extensionAU", ExtensionImpl.AU.getURI());

        extensionElement.setAttribute("xsi:schemaLocation", ExtensionImpl.AU.getSchemaLocation());

        Element auextCreate = xmlWriter.appendChild(extensionElement, "create");

        xmlWriter.appendChild(auextCreate, "registrantName").setTextContent(registrantName);

        if (registrantID != null && registrantIDType != null) {
            xmlWriter.appendChild(auextCreate, "registrantID", registrantID, "type", registrantIDType);
        }

        xmlWriter.appendChild(auextCreate, "eligibilityType").setTextContent(eligibilityType);

        if (eligibilityName != null) {
            xmlWriter.appendChild(auextCreate, "eligibilityName").setTextContent(eligibilityName);

            if (eligibilityID != null && eligibilityIDType != null) {
                xmlWriter.appendChild(auextCreate, "eligibilityID", eligibilityID, "type", eligibilityIDType);
            }
        }

        xmlWriter.appendChild(auextCreate, "policyReason").setTextContent(String.valueOf(policyReason));
    }
}

