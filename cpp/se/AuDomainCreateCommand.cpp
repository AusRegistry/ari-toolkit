#include "se/AuDomainCreateCommand.hpp"
#include "xml/XMLHelper.hpp"
#include "se/AuExtension.hpp"
#include "common/ErrorPkg.hpp"

namespace {
    Extension& auExtension() {
        static Extension* auExtension = new AuExtension();
        return *auExtension;
    }
}; // anonymous namespace

AuDomainCreateCommand::AuDomainCreateCommand (
        const std::string& name,
        const std::string& pw,
        const std::string* registrantID,
        const std::vector<std::string>* techContacts,
        const std::string &auEligibilityType,
        int auPolicyReason,
        const std::string &auRegistrantName) : DomainCreateCommand (
            name, pw, registrantID, techContacts)
{
    setExtension (auEligibilityType, auPolicyReason, auRegistrantName);
}

AuDomainCreateCommand::AuDomainCreateCommand (
        const std::string& name,
        const std::string& pw,
        const std::string* registrantID,
        const std::vector<std::string>* techContacts,
        const std::vector<std::string>* adminContacts,
        const std::vector<std::string>* billingContacts,
        const std::vector<std::string>* nameservers,
        const std::string &auEligibilityType,
        int auPolicyReason,
        const std::string &auRegistrantName,
        const std::string *auRegistrantID,
        const std::string *auRegistrantIDType,
        const std::string *auEligibilityName,
        const std::string *auEligibilityID,
        const std::string *auEligibilityIDType) : DomainCreateCommand (
            name, pw, registrantID, techContacts, nameservers,
            adminContacts, billingContacts, NULL)
{
    setExtension (auEligibilityType, auPolicyReason,
            auRegistrantName, auRegistrantID, auRegistrantIDType,
            auEligibilityName, auEligibilityID, auEligibilityIDType);
}

void AuDomainCreateCommand::setExtension (const std::string& eligibilityType,
        int policyReason,
        const std::string& registrantName)
{
    setExtension (eligibilityType, policyReason, registrantName,
            NULL, NULL, NULL, NULL, NULL);
}

void AuDomainCreateCommand::setExtension (
        const std::string& eligibilityType,
        int policyReason,
        const std::string &registrantName,
        const std::string* registrantID,
        const std::string &registrantIDType)
{
    setExtension (eligibilityType, policyReason, registrantName,
            registrantID, &registrantIDType, NULL, NULL, NULL);
}

void AuDomainCreateCommand::setExtension (
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
                    "se.domaincreate.au.missing_ar"));
    }

    DOMElement *auextCreate = xmlWriter->appendChild(
            xmlWriter->appendChild(
                command,
                "extension"),
            "create",
            auExtension().getURI());

    XMLHelper::setAttribute(auextCreate,
            "xsi:schemaLocation",
            auExtension().getSchemaLocation());

    DOMElement* auProperties = xmlWriter->appendChild(
            auextCreate, "auProperties");

    XMLHelper::setTextContent(
            xmlWriter->appendChild(auProperties, "registrantName"), 
            registrantName);

    if (registrantID && registrantIDType)
        xmlWriter->appendChild (auProperties,
                "registrantID", *registrantID,
                "type", *registrantIDType);

    XMLHelper::setTextContent(
            xmlWriter->appendChild(auProperties, "eligibilityType"),
            eligibilityType);

    if (eligibilityName)
    {
        XMLHelper::setTextContent(
                xmlWriter->appendChild(auProperties, "eligibilityName"),
                *eligibilityName);

        if (eligibilityID && eligibilityIDType)
            xmlWriter->appendChild(auProperties,
                    "eligibilityID", *eligibilityID,
                    "type", *eligibilityIDType);
    }

    XMLHelper::setTextContent(
            xmlWriter->appendChild(auProperties, "policyReason"),
            policyReason);
}

