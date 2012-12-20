#include "se/AeDomainCreateCommand.hpp"
#include "xml/XMLHelper.hpp"
#include "se/AeExtension.hpp"
#include "common/ErrorPkg.hpp"

namespace {
    Extension& aeExtension() {
        static Extension* aeExtension = new AeExtension();
        return *aeExtension;
    }
}; // anonymous namespace

AeDomainCreateCommand::AeDomainCreateCommand (
        const std::string& name,
        const std::string& pw,
        const std::string* registrantID,
        const std::vector<std::string>* techContacts,
        const std::string &aeEligibilityType,
        int aePolicyReason,
        const std::string &aeRegistrantName) : DomainCreateCommand (
            name, pw, registrantID, techContacts)
{
    setExtension (aeEligibilityType, aePolicyReason, aeRegistrantName);
}

AeDomainCreateCommand::AeDomainCreateCommand (
        const std::string& name,
        const std::string& pw,
        const std::string* registrantID,
        const std::vector<std::string>* techContacts,
        const std::vector<std::string>* adminContacts,
        const std::vector<std::string>* billingContacts,
        const std::vector<std::string>* nameservers,
	const Period* period,
        const std::string &aeEligibilityType,
        int aePolicyReason,
        const std::string &aeRegistrantName,
        const std::string *aeRegistrantID,
        const std::string *aeRegistrantIDType,
        const std::string *aeEligibilityName,
        const std::string *aeEligibilityID,
        const std::string *aeEligibilityIDType) : DomainCreateCommand (
            name, pw, registrantID, techContacts, nameservers,
            adminContacts, billingContacts, period)
{
    setExtension (aeEligibilityType, aePolicyReason,
            aeRegistrantName, aeRegistrantID, aeRegistrantIDType,
            aeEligibilityName, aeEligibilityID, aeEligibilityIDType);
}

void AeDomainCreateCommand::setExtension (const std::string& eligibilityType,
        int policyReason,
        const std::string& registrantName)
{
    setExtension (eligibilityType, policyReason, registrantName,
            NULL, NULL, NULL, NULL, NULL);
}

void AeDomainCreateCommand::setExtension (
        const std::string& eligibilityType,
        int policyReason,
        const std::string &registrantName,
        const std::string* registrantID,
        const std::string &registrantIDType)
{
    setExtension (eligibilityType, policyReason, registrantName,
            registrantID, &registrantIDType, NULL, NULL, NULL);
}

void AeDomainCreateCommand::setExtension (
        const std::string& eligibilityType,
        int policyReason,
        const std::string& registrantName,
        const std::string* registrantID,
        const std::string* registrantIDType,
        const std::string* eligibilityName,
        const std::string* eligibilityID,
        const std::string* eligibilityIDType)
{
    if ((registrantID && registrantIDType == NULL)
            || (registrantID == NULL && registrantIDType)
            || (eligibilityID && eligibilityIDType == NULL)
            || (eligibilityID == NULL && eligibilityIDType))
    {
        throw IllegalArgException(ErrorPkg::getMessage(
                    "se.domaincreate.ae.missing_ar"));
    }

    DOMElement *aeextCreate = xmlWriter->appendChild(
            xmlWriter->appendChild(
                command,
                "extension"),
            "create",
            aeExtension().getURI());

    XMLHelper::setAttribute(aeextCreate,
            "xsi:schemaLocation",
            aeExtension().getSchemaLocation());

    DOMElement* aeProperties = xmlWriter->appendChild(
            aeextCreate, "aeProperties");

    XMLHelper::setTextContent(
            xmlWriter->appendChild(aeProperties, "registrantName"), 
            registrantName);

    if (registrantID && registrantIDType)
        xmlWriter->appendChild (aeProperties,
                "registrantID", *registrantID,
                "type", *registrantIDType);

    XMLHelper::setTextContent(
            xmlWriter->appendChild(aeProperties, "eligibilityType"),
            eligibilityType);

    if (eligibilityName)
    {
        XMLHelper::setTextContent(
                xmlWriter->appendChild(aeProperties, "eligibilityName"),
                *eligibilityName);

        if (eligibilityID && eligibilityIDType)
            xmlWriter->appendChild(aeProperties,
                    "eligibilityID", *eligibilityID,
                    "type", *eligibilityIDType);
    }

    XMLHelper::setTextContent(
            xmlWriter->appendChild(aeProperties, "policyReason"),
            policyReason);
}

