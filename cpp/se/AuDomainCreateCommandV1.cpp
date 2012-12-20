#include "se/AuDomainCreateCommandV1.hpp"
#include "xml/XMLHelper.hpp"
#include "se/AuExtensionV1.hpp"

namespace {
    Extension& auExtension() {
        static Extension* auExtension = new AuExtensionV1();
        return *auExtension;
    }
}; // anonymous namespace

AuDomainCreateCommandV1::AuDomainCreateCommandV1(
        const std::string& name,
        const std::string& pw,
        const std::string* registrantID,
        const std::vector<std::string>* techContacts,
        const std::string &auEligibilityType,
        int auPolicyReason,
        const std::string &auRegistrantName) : DomainCreateCommand (
            name, pw, registrantID, techContacts)
{
    setExtension(auEligibilityType, auPolicyReason, auRegistrantName);
}

AuDomainCreateCommandV1::AuDomainCreateCommandV1(
        const std::string& name,
        const std::string& pw,
        const std::string* registrantID,
        const std::vector<std::string>* techContacts,
        std::vector<std::string>* adminContacts,
        std::vector<std::string>* billingContacts,
        std::vector<std::string>* nameservers,
        const std::string &auEligibilityType,
        int          auPolicyReason,
        const std::string &auRegistrantName,
        std::string *auRegistrantID,
        std::string *auRegistrantIDType,
        std::string *auEligibilityName,
        std::string *auEligibilityID,
        std::string *auEligibilityIDType) : DomainCreateCommand (
            name, pw, registrantID, techContacts,
            adminContacts, billingContacts, nameservers, NULL)
{
    setExtension(auEligibilityType, auPolicyReason,
            auRegistrantName, auRegistrantID, auRegistrantIDType,
            auEligibilityName, auEligibilityID, auEligibilityIDType);
}



void AuDomainCreateCommandV1::setExtension(
        const std::string& eligibilityType,
        int policyReason,
        const std::string& registrantName)
{
    setExtension(eligibilityType, policyReason, registrantName,
            NULL, NULL, NULL, NULL, NULL);
}

void AuDomainCreateCommandV1::setExtension(
        const std::string& eligibilityType,
        int policyReason,
        const std::string &registrantName,
        const std::string* registrantID,
        const std::string &registrantIDType)
{
    setExtension(eligibilityType, policyReason, registrantName,
            registrantID, &registrantIDType, NULL, NULL, NULL);
}

void AuDomainCreateCommandV1::setExtension(
        const std::string& eligibilityType,
        int policyReason,
        const std::string& registrantName,
        const std::string* registrantID,
        const std::string* registrantIDType,
        const std::string* eligibilityName,
        const std::string* eligibilityID,
        const std::string* eligibilityIDType)
{
    DOMElement *extensionAU = 
        xmlWriter->appendChild
        (xmlWriter->appendChild
         (command,
          "extension"),
         "extensionAU",
         auExtension().getURI());

    XMLHelper::setAttribute (extensionAU,
            "xsi:schemaLocation",
            auExtension().getSchemaLocation());

    DOMElement *auextCreate = xmlWriter->appendChild (extensionAU, "create");

    XMLHelper::setTextContent
        (xmlWriter->appendChild (auextCreate, "registrantName"), 
         registrantName);

    if (registrantID && registrantIDType)
        xmlWriter->appendChild (auextCreate,
                "registrantID", *registrantID,
                "type", *registrantIDType);

    XMLHelper::setTextContent
        (xmlWriter->appendChild (auextCreate, "eligibilityType"),
         eligibilityType);

    if (eligibilityName)
    {
        XMLHelper::setTextContent
            (xmlWriter->appendChild (auextCreate, "eligibilityName"),
             *eligibilityName);

        if (eligibilityID && eligibilityIDType)
            xmlWriter->appendChild (auextCreate,
                    "eligibilityID", *eligibilityID,
                    "type", *eligibilityIDType);
    }

    XMLHelper::setTextContent(
            xmlWriter->appendChild(auextCreate, "policyReason"),
            policyReason);
}

