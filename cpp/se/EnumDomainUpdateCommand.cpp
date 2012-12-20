#include "se/EnumDomainUpdateCommand.hpp"
#include "se/E164Extension.hpp"

namespace {
    Extension& e164Extension() {
        static Extension* extension = new E164Extension();
        return *extension;
    }
}; // anonymous namespace

EnumDomainUpdateCommand::EnumDomainUpdateCommand (
        const std::string &name,
        const std::vector<NAPTR> &addNaptrs,
        const std::vector<NAPTR> &remNaptrs) : DomainUpdateCommand (name)
{
    setExtension (addNaptrs, remNaptrs);
}

EnumDomainUpdateCommand::EnumDomainUpdateCommand (
        const std::string &name,
        const std::string &pw,
        const DomainAdd *add,
        const DomainRem *rem,
        const std::string &registrantID,
        const std::vector<NAPTR> &addNaptrs,
        const std::vector<NAPTR> &remNaptrs) : DomainUpdateCommand (
            name, &pw, add, rem, &registrantID)
{
    setExtension (addNaptrs, remNaptrs);
}

void EnumDomainUpdateCommand::setExtension (
        const std::vector<NAPTR> &addNaptrs,
        const std::vector<NAPTR> &remNaptrs)
{
    if (addNaptrs.size() == 0 && remNaptrs.size() == 0)
        return;

    DOMElement * e164Update = xmlWriter->appendChild(
            xmlWriter->appendChild(
                command,
                "extension"),
            "update",
            e164Extension().getURI());

    if (addNaptrs.size() > 0)
    {
        DOMElement *e164Add = xmlWriter->appendChild (e164Update, "add");
        for (unsigned int i = 0; i < addNaptrs.size(); i++)
            addNaptrs[i].appendToElement (xmlWriter, e164Add);
    }

    if (remNaptrs.size() > 0)
    {
        DOMElement *e164Add = xmlWriter->appendChild (e164Update, "rem");
        for (unsigned int i = 0; i < remNaptrs.size(); i++)
            remNaptrs[i].appendToElement (xmlWriter, e164Add);
    }
}

