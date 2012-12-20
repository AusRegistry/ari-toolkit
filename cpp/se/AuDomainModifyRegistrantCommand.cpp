#include "se/AuDomainModifyRegistrantCommand.hpp"
#include "common/ErrorPkg.hpp"
#include "se/AuExtension.hpp"
#include "xml/XMLHelper.hpp"

namespace {
    AuExtension& auExtension() {
        static AuExtension* auExtension = new AuExtension();
        return *auExtension;
    }
}; // anonymous namespace

AuDomainModifyRegistrantCommand::AuDomainModifyRegistrantCommand(
        const std::string& name,
        const std::string& registrantName,
        const std::string& explanation,
        const std::string* eligibilityType,
        int policyReason,
        const std::string* registrantID,
        const std::string* registrantIDType,
        const std::string* eligibilityName,
        const std::string* eligibilityID,
        const std::string* eligibilityIDType) : DomainUpdateCommand(name)
{
    if (eligibilityType == NULL
            || (registrantID == NULL && registrantIDType != NULL)
            || (registrantID != NULL && registrantIDType == NULL)
            || (eligibilityID == NULL && eligibilityIDType != NULL)
            || (eligibilityID != NULL && eligibilityIDType == NULL))
    {
        throw IllegalArgException(
                ErrorPkg::getMessage("se.domain.modify.au.missing_arg"));
    }

    DOMElement* auextUpdate = xmlWriter->appendChild(
            xmlWriter->appendChild(command, "extension"),
            "update",
            auExtension().getURI());

    auextUpdate->setAttribute(
            XStr("xsi:schemaLocation").str(),
            XStr(auExtension().getSchemaLocation()).str());

    DOMElement* auProperties = xmlWriter->appendChild(auextUpdate,
            "auProperties");

    XMLHelper::setTextContent(
            xmlWriter->appendChild(auProperties, "registrantName"),
            registrantName);

    if (registrantID != NULL && registrantIDType != NULL)
    {
        xmlWriter->appendChild(
                auProperties,
                "registrantID",
                *registrantID,
                "type",
                *registrantIDType);
    }

    if (eligibilityType != NULL)
    {
        XMLHelper::setTextContent(
                xmlWriter->appendChild(auProperties, "eligibilityType"),
                *eligibilityType);
    }

    if (eligibilityName != NULL)
    {
        XMLHelper::setTextContent(
                xmlWriter->appendChild(auProperties, "eligibilityName"),
                *eligibilityName);
    }

    if (eligibilityID != NULL && eligibilityIDType != NULL)
    {
        xmlWriter->appendChild(
                auProperties,
                "eligibilityID",
                *eligibilityID,
                "type",
                *eligibilityIDType);
    }

    XMLHelper::setTextContent(
            xmlWriter->appendChild(auProperties, "policyReason"),
            policyReason);

    XMLHelper::setTextContent(
            xmlWriter->appendChild(auextUpdate, "explanation"),
            explanation);
}

