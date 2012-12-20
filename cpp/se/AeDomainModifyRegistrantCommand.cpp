#include "se/AeDomainModifyRegistrantCommand.hpp"
#include "common/ErrorPkg.hpp"
#include "se/AeExtension.hpp"
#include "xml/XMLHelper.hpp"

namespace {
    AeExtension& aeExtension() {
        static AeExtension* aeExtension = new AeExtension();
        return *aeExtension;
    }
}; // anonymous namespace

AeDomainModifyRegistrantCommand::AeDomainModifyRegistrantCommand(
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
                ErrorPkg::getMessage("se.domain.modify.ae.missing_arg"));
    }

    DOMElement* aeextUpdate = xmlWriter->appendChild(
            xmlWriter->appendChild(command, "extension"),
            "update",
            aeExtension().getURI());

    aeextUpdate->setAttribute(
            XStr("xsi:schemaLocation").str(),
            XStr(aeExtension().getSchemaLocation()).str());

    DOMElement* aeProperties = xmlWriter->appendChild(aeextUpdate,
            "aeProperties");

    XMLHelper::setTextContent(
            xmlWriter->appendChild(aeProperties, "registrantName"),
            registrantName);

    if (registrantID != NULL && registrantIDType != NULL)
    {
        xmlWriter->appendChild(
                aeProperties,
                "registrantID",
                *registrantID,
                "type",
                *registrantIDType);
    }

    if (eligibilityType != NULL)
    {
        XMLHelper::setTextContent(
                xmlWriter->appendChild(aeProperties, "eligibilityType"),
                *eligibilityType);
    }

    if (eligibilityName != NULL)
    {
        XMLHelper::setTextContent(
                xmlWriter->appendChild(aeProperties, "eligibilityName"),
                *eligibilityName);
    }

    if (eligibilityID != NULL && eligibilityIDType != NULL)
    {
        xmlWriter->appendChild(
                aeProperties,
                "eligibilityID",
                *eligibilityID,
                "type",
                *eligibilityIDType);
    }

    XMLHelper::setTextContent(
            xmlWriter->appendChild(aeProperties, "policyReason"),
            policyReason);

    XMLHelper::setTextContent(
            xmlWriter->appendChild(aeextUpdate, "explanation"),
            explanation);
}

