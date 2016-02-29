package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;
import org.w3c.dom.Element;

/**
 * Extension of the domain mapping of the EPP update command, as defined in
 * RFC5730 and RFC5731, to .au domain names, the specification of which is in
 * the XML schema definition urn:X-au:params:xml:ns:auext-1.2.
 * This class should only be used to correct au extension data for .au domain
 * names, and only where the legal registrant has not changed.
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The toXML method in Command serialises this object to
 * XML.
 */
public final class AuDomainModifyRegistrantCommand
    extends DomainUpdateCommand {

    private static final long serialVersionUID = 8196324073107340593L;

    /**
     * @throws IllegalArgumentException if {@code name}, {@code eligibilityType} or {@code registrantName}
     * is {@code null}.
     */
    public AuDomainModifyRegistrantCommand(String name, String eligibilityType,
            int policyReason, String registrantName, String registrantID,
            String registrantIDType, String eligibilityName,
            String eligibilityID, String eligibilityIDType,
            String explanation) {

        super(name);

        if (eligibilityType == null || registrantName == null) {
            throw new IllegalArgumentException(ErrorPkg.getMessage(
                    "se.domain.modify.au.missing_arg"));
        }

        assert (registrantID == null && registrantIDType == null)
            || (registrantID != null && registrantIDType != null);

        assert (eligibilityID == null && eligibilityIDType == null)
            || (eligibilityID != null && eligibilityIDType != null);

        extension = xmlWriter.appendChild(command, "extension");
        Element extensionElement = xmlWriter.appendChild(extension, "update", ExtensionImpl.AU.getURI());

        extensionElement.setAttribute(
                "xsi:schemaLocation",
                ExtensionImpl.AU.getSchemaLocation());

                Element auProperties = xmlWriter.appendChild(extensionElement, "auProperties");
        xmlWriter.appendChild(
            auProperties,
            "registrantName").setTextContent(registrantName);

        if (registrantID != null && registrantIDType != null) {
            xmlWriter.appendChild(auProperties,
                    "registrantID",
                    registrantID,
                    "type", registrantIDType);
        }

        xmlWriter.appendChild(auProperties,
            "eligibilityType").setTextContent(eligibilityType);

        if (eligibilityName != null) {
            xmlWriter.appendChild(
                                auProperties,
                    "eligibilityName").setTextContent(eligibilityName);
        }

        if (eligibilityID != null && eligibilityIDType != null) {
            xmlWriter.appendChild(auProperties,
                    "eligibilityID",
                    eligibilityID,
                    "type", eligibilityIDType);
        }

        xmlWriter.appendChild(
                        auProperties,
                "policyReason").setTextContent(
                    String.valueOf(policyReason));

        xmlWriter.appendChild(extensionElement,
                "explanation").setTextContent(explanation);
    }
}

