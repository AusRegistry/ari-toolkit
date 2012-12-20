#include "se/AuDomainTransferRegistrantCommand.hpp"
#include "se/XMLGregorianCalendar.hpp"

#include "se/AuExtension.hpp"
#include "se/AuDomainObjectType.hpp"
#include "se/RegistrantTransferCommandType.hpp"
#include "se/CommandType.hpp"
#include "se/Period.hpp"
#include "common/ErrorPkg.hpp"

#include "se/EPPDateFormatter.hpp"

#include "xml/XMLHelper.hpp"

namespace {
    Extension& auExtension() {
        static Extension* auExt = new AuExtension();
        return *auExt;
    }

    const RegistrantTransferCommandType rtrnType;
    const AuDomainObjectType audomType;
} // anonymous namespace

AuDomainTransferRegistrantCommand::AuDomainTransferRegistrantCommand (
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
            &rtrnType, &audomType, name, auExtension())
{
    if ((registrantID && registrantIDType == NULL)
            || (registrantIDType == NULL && registrantIDType)
            || (eligibilityName && (eligibilityID == NULL || eligibilityIDType == NULL))
            || (eligibilityName == NULL && (eligibilityID || eligibilityIDType)))
    {
        // If provided, a registrantID must have a type.
        // If provided, an eligibilityName must have both an eligibilityID and type.
        throw IllegalArgException(
                ErrorPkg::getMessage("se.domain.registrantTransfer.au.missing_arg"));
    }

    DOMElement *element;

    std::string curExpDateStr = EPPDateFormatter::toXSDate(curExpDate);
    XMLHelper::setTextContent(
            xmlWriter->appendChild(objElement, "curExpDate"), curExpDateStr);

    if (period)
        period->appendPeriod(xmlWriter, objElement);

    DOMElement *auProperties = xmlWriter->appendChild(objElement, "auProperties");

    XMLHelper::setTextContent(
            xmlWriter->appendChild(auProperties, "registrantName"), registrantName);

    if (registrantID)
    {
        element = xmlWriter->appendChild(auProperties, "registrantID");
        XMLHelper::setTextContent(element, *registrantID);
        XMLHelper::setAttribute(element, "type", *registrantIDType);
    }
    XMLHelper::setTextContent
        (xmlWriter->appendChild(auProperties, "eligibilityType"), eligibilityType);

    if (eligibilityName)
    {
        XMLHelper::setTextContent(
                xmlWriter->appendChild(auProperties, "eligibilityName"), *eligibilityName);

        element = xmlWriter->appendChild(auProperties, "eligibilityID");
        XMLHelper::setTextContent(element, *eligibilityID);
        XMLHelper::setAttribute(element, "type", *eligibilityIDType);
    }

    XMLHelper::setTextContent(
            xmlWriter->appendChild(auProperties, "policyReason"),
            policyReason);

    XMLHelper::setTextContent(
            xmlWriter->appendChild(objElement, "explanation"),
            explanation);
}

