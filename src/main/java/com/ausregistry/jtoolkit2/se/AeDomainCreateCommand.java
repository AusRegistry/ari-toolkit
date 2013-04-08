package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;
import org.w3c.dom.Element;

/**
 * Extension of EPP urn:ietf:params:xml:ns:domain-1.0 create command specified
 * in RFC5731 to urn:X-ae:params:xml:ns:aeext-1.0. .ae domains must be
 * provisioned using this class rather than
 * {@link com.ausregistry.jtoolkit2.se.DomainCreateCommand}, as the ae extension
 * data is mandatory. Use this class to generate a standards-compliant XML
 * document, given simple input parameters. The toXML method in Command
 * serialises this object to XML. The response expected from a server should be
 * handled by a {@link com.ausregistry.jtoolkit2.se.DomainCreateResponse}
 * object.
 */
public final class AeDomainCreateCommand extends DomainCreateCommand {
    private static final long serialVersionUID = -8632740108172182234L;

    /**
     * Minimal constructor for creating a domain:create + aeext:create EPP
     * command. These parameters are the least required for a valid .ae domain
     * create command.
     */
    public AeDomainCreateCommand(String name, String pw, String registrantID,
            String[] techContacts, String aeEligibilityType, int aePolicyReason,
            String aeRegistrantName) {
        super(name, pw, registrantID, techContacts);

        setExtension(aeEligibilityType, aePolicyReason, aeRegistrantName);
    }

    /**
     * Full data specification constructor for a domain:create + aeext:create
     * EPP command. Please refer to the urn:X-ae:params:xml:ns:aeext-1.0 schema
     * for specification of the required fields. The mapping of parameter names
     * to ae extension fields is given in the parameter documentation.
     *
     * @param name
     *            The name of the new domain.
     *
     * @param pw
     *            The password to assign to the domain (also known as authInfo
     *            or authorisation information).
     *
     * @param registrantID
     *            The identifier of an existing contact to assign as the
     *            registrant contact for this domain. Failure to ensure the
     *            contact exists prior to using them in this way will result in
     *            an EPP result of '2303 "Object does not exist"'.
     *
     * @param techContacts
     *            The identifiers of existing contacts to assign as technical
     *            contacts for this domain. Failure to ensure the contacts exist
     *            prior to using them in this way will result in an EPP result
     *            of '2303 "Object does not exist"'.
     *
     * @param adminContacts
     *            See techContacts (substitute administrative for technical).
     *
     * @param billingContacts
     *            See techContacts (substitute billing for technical).
     *
     * @param nameservers
     *            The names of existing hosts to delegate the domain being
     *            created to. Failure to ensure the hosts exist prior to using
     *            them in this way will result in an EPP result of '2303 "Object
     *            does not exist"'.
     *
     * @param period
     *            The initial registration period of the domain object. A server
     *            may define a default initial registration period if not
     *            specified by the client.
     *
     * @param aeEligibilityType
     *            aeext:eligType.
     *
     * @param aePolicyReason
     *            aeext:policyReason.
     *
     * @param aeRegistrantName
     *            aeext:registrantName.
     *
     * @param aeRegistrantID
     *            aeext:registrantID.
     *
     * @param aeRegistrantIDType
     *            aeext:registrantID type attribute.
     *
     * @param aeEligibilityName
     *            aeext:eligibilityName.
     *
     * @param aeEligibilityID
     *            aeext:eligibilityID.
     *
     * @param aeEligibilityIDType
     *            aeext:eligibilityID type attribute.
     * @throws IllegalArgumentException if {@code eligibilityType} or {@code registrantIDType} is {@code null}.
     */
    public AeDomainCreateCommand(String name, String pw, String registrantID,
            String[] techContacts, String[] adminContacts, String[] billingContacts,
            String[] nameservers, Period period, String aeEligibilityType, int aePolicyReason,
            String aeRegistrantName, String aeRegistrantID, String aeRegistrantIDType,
            String aeEligibilityName, String aeEligibilityID, String aeEligibilityIDType) {
        super(name, pw, registrantID, techContacts, adminContacts, billingContacts, nameservers,
                period);

        setExtension(aeEligibilityType, aePolicyReason, aeRegistrantName, aeRegistrantID,
                aeRegistrantIDType, aeEligibilityName, aeEligibilityID, aeEligibilityIDType);
    }

    private void setExtension(String eligibilityType, int policyReason, String registrantName) {
        setExtension(eligibilityType, policyReason, registrantName, null, null, null, null, null);
    }

    /**
     * &lt;extension&gt; &nbsp;&lt;create
     * xmlns="urn:X-ae:params:xml:ns:aeext-1.0"
     * xsi:schemaLocation="urn:X-ae:params:xml:ns:aeext-1.0 aeext.1.0.xsd"&gt;
     * &nbsp;&nbsp;&lt;aeProperties&gt;
     * &nbsp;&nbsp;&nbsp;&lt;registrantName&gt;registrantName
     * &lt;/registrantName&gt; &nbsp;&nbsp;&nbsp;&lt;registrantID
     * type="registrantIDType"&gt;registrantID&lt;/registrantID&gt;
     * &nbsp;&nbsp;&
     * nbsp;&lt;eligibilityType&gt;eligibilityType&lt;/eligibilityType&gt;
     * &nbsp;
     * &nbsp;&nbsp;&lt;eligibilityName&gt;eligibilityName&lt;/eligibilityName
     * &gt; &nbsp;&nbsp;&nbsp;&lt;eligibilityID
     * type="eligibilityIDType"&gt;eligibilityID&lt;/eligibilityID&gt;
     * &nbsp;&nbsp;&nbsp;&lt;policyReason&gt;policyReason&lt;/policyReason&gt;
     * &nbsp;&nbsp;&lt;/aeProperties&gt; &nbsp;&lt;/create&gt;
     * &lt;/extension&gt;
     */
    private void setExtension(String eligibilityType, int policyReason, String registrantName,
            String registrantID, String registrantIDType, String eligibilityName,
            String eligibilityID, String eligibilityIDType) {

        if (eligibilityType == null || registrantName == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.domain.create.ae.missing_arg"));
        }

        assert (registrantID == null && registrantIDType == null)
                || (registrantID != null && registrantIDType != null);

        assert (eligibilityID == null && eligibilityIDType == null)
                || (eligibilityID != null && eligibilityIDType != null);

        extension = xmlWriter.appendChild(command, "extension");
        Element extensionElement = xmlWriter.appendChild(extension, "create",
                ExtensionImpl.AE.getURI());

        extensionElement.setAttribute("xsi:schemaLocation", ExtensionImpl.AE.getSchemaLocation());

        Element aeProperties = xmlWriter.appendChild(extensionElement, "aeProperties");

        xmlWriter.appendChild(aeProperties, "registrantName").setTextContent(registrantName);

        if (registrantID != null && registrantIDType != null) {
            xmlWriter.appendChild(aeProperties, "registrantID", registrantID, "type",
                    registrantIDType);
        }

        xmlWriter.appendChild(aeProperties, "eligibilityType").setTextContent(eligibilityType);

        if (eligibilityName != null) {
            xmlWriter.appendChild(aeProperties, "eligibilityName").setTextContent(eligibilityName);

            if (eligibilityID != null && eligibilityIDType != null) {
                xmlWriter.appendChild(aeProperties, "eligibilityID", eligibilityID, "type",
                        eligibilityIDType);
            }
        }

        xmlWriter.appendChild(aeProperties, "policyReason").setTextContent(
                String.valueOf(policyReason));
    }
}
