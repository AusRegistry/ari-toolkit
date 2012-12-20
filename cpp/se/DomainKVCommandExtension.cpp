#include "se/DomainKVCommandExtension.hpp"
#include "se/Command.hpp"
#include "se/KVExtension.hpp"
#include "xml/XMLHelper.hpp"

namespace {
    KVExtension& kvExtension() {
        static KVExtension* kvExtension = new KVExtension();
        return *kvExtension;
    }
}; // anonymous namespace

DomainKVCommandExtension::DomainKVCommandExtension(
        const CommandType* commandType) :
        commandName(commandType->getCommandName()),
        kvLists()
{
}

void DomainKVCommandExtension::addToCommand(const Command &command) const
{
    XMLWriter* xmlWriter = command.getXmlWriter();
    DOMElement* extensionElement = command.getExtensionElement();
    DOMElement* commandElement = xmlWriter->appendChild(extensionElement,
            commandName, kvExtension().getURI());

    createKVListElements(xmlWriter, commandElement);
}

void DomainKVCommandExtension::createKVListElements(
      XMLWriter *xmlWriter,
      DOMElement *commandElement) const
{
   DOMElement *kvlistElement = NULL;
   ExtensionList::const_iterator kvListsIterator;

   for (kvListsIterator = kvLists.begin();
         kvListsIterator != kvLists.end();
         kvListsIterator++)
   {
      kvlistElement = xmlWriter->appendChild(commandElement, "kvlist",
            kvExtension().getURI());
      kvlistElement->setAttribute(XStr("name").str(), XStr(kvListsIterator->first).str());
      addItemsToList(xmlWriter, kvListsIterator->second, kvlistElement);
   }
}

void DomainKVCommandExtension::addItemsToList(
      XMLWriter *xmlWriter,
      const KeyValueList &keyValueList,
      DOMElement *kvlistElement) const
{
   DOMElement *element = NULL;
   KeyValueList::const_iterator kvItemIterator;

   for (kvItemIterator = keyValueList.begin();
         kvItemIterator != keyValueList.end();
         kvItemIterator++)
   {
      element = xmlWriter->appendChild(kvlistElement, "item");
      element->setAttribute(XStr("key").str(), XStr(kvItemIterator->first).str());
      element->setTextContent(XStr(kvItemIterator->second).str());
   }
}
