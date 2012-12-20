#include "se/DomainUpdateSyncCommandExtension.hpp"
#include "se/Command.hpp"
#include "se/SyncExtension.hpp"
#include "xml/XMLHelper.hpp"

namespace {
    SyncExtension& syncExtension() {
        static SyncExtension* syncExtension = new SyncExtension();
        return *syncExtension;
    }
}; // anonymous namespace

void DomainUpdateSyncCommandExtension::addToCommand(const Command &command) const
{
    XMLWriter* xmlWriter = command.getXmlWriter();
    DOMElement* extensionElement = command.getExtensionElement();
    DOMElement* updateElement = xmlWriter->appendChild(extensionElement,
            "update", syncExtension().getURI());
    DOMElement* exDateElement = xmlWriter->appendChild(updateElement, "exDate");
    XMLHelper::setTextContent(exDateElement, syncExpiryDate);
}
