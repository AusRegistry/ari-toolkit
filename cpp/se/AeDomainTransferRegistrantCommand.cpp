#include "se/AeDomainTransferRegistrantCommand.hpp"
#include "se/XMLGregorianCalendar.hpp"

#include "se/AeExtension.hpp"
#include "se/AeDomainObjectType.hpp"
#include "se/RegistrantTransferCommandType.hpp"
#include "se/CommandType.hpp"
#include "se/Period.hpp"
#include "common/ErrorPkg.hpp"

#include "se/EPPDateFormatter.hpp"

#include "xml/XMLHelper.hpp"

namespace {
    Extension& aeExtension() {
        static Extension* aeExt = new AeExtension();
        return *aeExt;
    }

    const RegistrantTransferCommandType rtrnType;
    const AeDomainObjectType aedomType;
} // anonymous namespace

AeDomainTransferRegistrantCommand::AeDomainTransferRegistrantCommand (
        const std::string& name,
        const XMLGregorianCalendar& curExpDate,
        const std::string& eligibilityType,
        int policyReason,
        const std::string& registrantName,
        const std::string& explanation,
        const std::string* registrantID,
        const std::string* registrantIDType,
        const std::string* eligibilityName,
        const std::string* eligibilityID,
        const std::string* eligibilityIDType,
        const Period* period) : ProtocolExtensionCommand(
            &rtrnType, &aedomType, name, aeExtension())
{
    if ((registrantID && registrantIDType == NULL)
            || (registrantIDType == NULL && registrantIDType)
            || (eligibilityName && (eligibilityID == NULL || eligibilityIDType == NULL))
            || (eligibilityName == NULL && (eligibilityID || eligibilityIDType)))
    {
        // If provided, a registrantID must have a type.
        // If provided, an eligibilityName must have both an eligibilityID and type.
        throw IllegalArgException(
                ErrorPkg::getMessage("se.domain.registrantTransfer.ae.missing_arg"));
    }

    DOMElement *element;

    std::string curExpDateStr = EPPDateFormatter::toXSDate(curExpDate);
    XMLHelper::setTextContent(
            xmlWriter->appendChild(objElement, "curExpDate"), curExpDateStr);

    if (period)
        period->appendPeriod(xmlWriter, objElement);

    DOMElement *aeProperties = xmlWriter->appendChild(objElement, "aeProperties");

    XMLHelper::setTextContent(
            xmlWriter->appendChild(aeProperties, "registrantName"), registrantName);

    if (registrantID)
    {
        element = xmlWriter->appendChild(aeProperties, "registrantID");
        XMLHelper::setTextContent(element, *registrantID);
        XMLHelper::setAttribute(element, "type", *registrantIDType);
    }
    XMLHelper::setTextContent
        (xmlWriter->appendChild(aeProperties, "eligibilityType"), eligibilityType);

    if (eligibilityName)
    {
        XMLHelper::setTextContent(
                xmlWriter->appendChild(aeProperties, "eligibilityName"), *eligibilityName);

        element = xmlWriter->appendChild(aeProperties, "eligibilityID");
        XMLHelper::setTextContent(element, *eligibilityID);
        XMLHelper::setAttribute(element, "type", *eligibilityIDType);
    }

    XMLHelper::setTextContent(
            xmlWriter->appendChild(aeProperties, "policyReason"),
            policyReason);

    XMLHelper::setTextContent(
            xmlWriter->appendChild(objElement, "explanation"),
            explanation);
}

