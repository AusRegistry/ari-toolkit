#ifndef __DOMAIN_KV_COMMAND_EXTENSION_HPP
#define __DOMAIN_KV_COMMAND_EXTENSION_HPP

#include <string>
#include <map>

#include "xercesc/dom/DOMElement.hpp"

#include "xml/XMLWriter.hpp"
#include "se/CommandExtension.hpp"
#include "se/CommandType.hpp"

#include "se/KVDefs.hpp"

class Command;

/**
 * This class models the <update> and <create> elements as defined in the
 * AusRegistry kv-1.0 EPP command extension.
 */

class DomainKVCommandExtension : public CommandExtension
{
    public:
      /**
       * Creates a key-value domain extension for specified command type
       *
       * @param commandType
       *            the type of command
       */
        DomainKVCommandExtension(const CommandType *commandType);
        virtual void addToCommand(const Command &command) const;
        void addItem(
              const std::string &listName,
              const std::string &key,
              const std::string &value);
    private:
        void createKVListElements(
              XMLWriter *xmlWriter,
              DOMElement *commandElement) const;
        void addItemsToList(
              XMLWriter *xmlWriter,
              const KeyValueList &keyValueList,
              DOMElement *kvlistElement) const;

        std::string commandName;
        ExtensionList kvLists;
};

inline void DomainKVCommandExtension::addItem(
      const std::string &listName,
      const std::string &key,
      const std::string &value)
{
   kvLists[listName][key] = value;
}

#endif // __DOMAIN_KV_COMMAND_EXTENSION_HPP
