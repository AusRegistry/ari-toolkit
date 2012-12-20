#include "se/EnumDomainCreateCommand.hpp"
#include "se/E164Extension.hpp"

namespace {
    Extension& e164Extension() {
        static Extension* extension = new E164Extension();
        return *extension;
    }
}; // anonymous namespace

EnumDomainCreateCommand::EnumDomainCreateCommand (
        const std::string &name,
        const std::string &pw,
        const std::string *registrantID,
        const std::vector<std::string> &techContacts) : DomainCreateCommand (
            name, pw, registrantID, &techContacts)
{ }


EnumDomainCreateCommand::EnumDomainCreateCommand (
        const std::string &name,
        const std::string &pw,
        const std::string *registrantID,
        const std::vector<std::string> &techContacts,
        const std::vector<NAPTR> *naptrs) : DomainCreateCommand (
            name, pw, registrantID, &techContacts)
{
    setExtension (naptrs);
}

EnumDomainCreateCommand::EnumDomainCreateCommand (
        const std::string &name,
        const std::string &pw,
        const std::string *registrantID,
        const std::vector<std::string> &techContacts,
        const std::vector<std::string> &adminContacts,
        const std::vector<std::string> &billingContacts,
        const std::vector<NAPTR> *naptrs,
        const Period &period) : DomainCreateCommand (
            name, pw, registrantID, &techContacts,
            &adminContacts, &billingContacts,
            NULL, &period)
{
    setExtension (naptrs);
}     

EnumDomainCreateCommand::EnumDomainCreateCommand (
        const std::string &name,
        const std::string &pw,
        const std::string *registrantID,
        const std::vector<std::string> &techContacts,
        const std::vector<std::string> &adminContacts,
        const std::vector<std::string> &billingContacts,
        const std::vector<std::string> &nameservers,
        const Period &period) : DomainCreateCommand (
            name, pw, registrantID, &techContacts,
            &adminContacts, &billingContacts, &nameservers,
            &period)
{
}

void EnumDomainCreateCommand::setExtension (const std::vector<NAPTR> *naptrs)
{
    if (naptrs == NULL || naptrs->size() == 0)
        return;

    DOMElement *e164Create = xmlWriter->appendChild(
            xmlWriter->appendChild(
                command,
                "extension"),
            "create",
            e164Extension().getURI());

    std::vector<NAPTR>::const_iterator p = naptrs->begin();

    while (p != naptrs->end())
    {
        p->appendToElement (xmlWriter, e164Create);
        p++;
    }
}

