package com.ausregistry.jtoolkit2.se;

import java.util.GregorianCalendar;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.ErrorPkg;
import org.w3c.dom.Element;

/**
 * In cases where the legal registrant of a .ae domain name has changed, this
 * class should be used to request a transfer of registrant. This is a different
 * action to correcting extension data which was originally specified
 * incorrectly, and should only be used in the situation described. This command
 * will result in the validity period of the domain name being updated and the
 * requesting client being charged the usual create fee upon success of this
 * operation. Use this class to generate a standards-compliant XML document,
 * given simple input parameters. The toXML method in Command serialises this
 * object to XML.
 *
 */
public final class AeDomainTransferRegistrantCommand extends ProtocolExtensionCommand {

    private static final long serialVersionUID = -7263014552265126595L;
    private static final CommandType RETURN_TYPE = new AeRegistrantTransferCommandType();

    public AeDomainTransferRegistrantCommand(String name, GregorianCalendar curExpDate,
            String registrantName, String eligibilityType, int policyReason, String explanation) {

        this(name, curExpDate, null, eligibilityType, policyReason, registrantName, null, null,
                null, null, null, explanation);
    }

    /**
     * Request that the named .ae domain name be transferred to the legal entity
     * specified by the given ae extension data.
     *
     * @param name
     *            The domain name to transfer. Required.
     *
     * @param curExpDate
     *            The current expiry of the identified domain name. This is
     *            required in order to prevent repeated transfer of the name due
     *            to protocol transmission failures. Required.
     *
     * @param period
     *            The desired new validity period, starting from the time the
     *            transfer completes successfully. Optional.
     * @param eligibilityType Required.
     * @param policyReason
     * @param registrantName Required.
     * @param registrantID
     * @param registrantIDType
     * @param eligibilityName
     * @param eligibilityID
     * @param eligibilityIDType
     *
     * @param explanation
     *            An explanation of how the transfer was effected.
     * @throws IllegalArgumentException if {@code curExpDate}, {@code eligibilityType} or {@code registrantIDType}
     * is {@code null}.
     */
    public AeDomainTransferRegistrantCommand(String name, GregorianCalendar curExpDate,
            Period period, String eligibilityType, int policyReason, String registrantName,
            String registrantID, String registrantIDType, String eligibilityName,
            String eligibilityID, String eligibilityIDType, String explanation) {

        super(RETURN_TYPE, ExtendedObjectType.AE_DOMAIN, name);

        if (curExpDate == null || eligibilityType == null || registrantName == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.domain.registrantTransfer.ae.missing_arg"));
        }

        // A Registrant ID, if present, must be qualified by a type.
        assert (registrantID == null && registrantIDType == null)
                || (registrantID != null && registrantIDType != null);

        if (eligibilityName != null) {
            assert eligibilityID != null;
            assert eligibilityIDType != null;
        }

        Element element;

        String curExpDateStr = EPPDateFormatter.toXSDate(curExpDate);
        xmlWriter.appendChild(objElement, "curExpDate").setTextContent(curExpDateStr);

        if (period != null) {
            period.appendPeriod(xmlWriter, objElement);
        }

        Element aeProperties = xmlWriter.appendChild(objElement, "aeProperties");

        xmlWriter.appendChild(aeProperties, "registrantName").setTextContent(registrantName);
        if (registrantID != null) {
            element = xmlWriter.appendChild(aeProperties, "registrantID");
            element.setTextContent(registrantID);
            element.setAttribute("type", registrantIDType);
        }
        xmlWriter.appendChild(aeProperties, "eligibilityType").setTextContent(eligibilityType);
        if (eligibilityName != null) {
            // Having an eligibility name implies that the entity must also
            // have an identifier for their eligibility name, specified by an
            // eligibility ID with required type attribute.
            xmlWriter.appendChild(aeProperties, "eligibilityName").setTextContent(eligibilityName);
            element = xmlWriter.appendChild(aeProperties, "eligibilityID");
            element.setTextContent(eligibilityID);
            element.setAttribute("type", eligibilityIDType);
        }
        String policyReasonStr = String.valueOf(policyReason);
        xmlWriter.appendChild(aeProperties, "policyReason").setTextContent(policyReasonStr);
        xmlWriter.appendChild(objElement, "explanation").setTextContent(explanation);
    }

    @Override
    protected Extension getExtension() {
        return ExtensionImpl.AE;
    }
}
