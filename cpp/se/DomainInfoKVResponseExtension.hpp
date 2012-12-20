#ifndef DOMAIN_INFO_KV_RESPONSE_EXTENSION_H_
#define DOMAIN_INFO_KV_RESPONSE_EXTENSION_H_

#include <set>
#include <string>

#include <se/ResponseExtension.hpp>
#include "se/KVDefs.hpp"

class XMLDocument;

/**
 * Extension of the domain mapping of the EPP info response, as defined in
 * RFC4930 and RFC4931, to generic domain names, the specification of which is
 * in the XML schema definition urn:X-ar:params:xml:ns:kv-1.0. Instances of this
 * class provide an interface to access all of the information available through
 * EPP for a generic domain name. This relies on the instance first being
 * initialised by a suitable EPP domain info response using the method
 * fromXML().
 *
 * For flexibility, this implementation extracts the data from the response
 * using XPath queries.
 */

class DomainInfoKVResponseExtension : public ResponseExtension
{
   public:
      DomainInfoKVResponseExtension();

      virtual void fromXML(XMLDocument *xmlDoc);
      virtual bool isInitialised() const;

      /**
       * Retrieves the names of all key-value lists that have been added to the
       * object.
       *
       * @return the set of list names
       */
      const std::set<std::string> getLists() const;

      /**
       * Retrieves the names of all item keys, for a specified key-value list
       * name.
       *
       * @param listName
       *            the name of the key-value list
       *
       * @return the set of item keys
       */
      const std::set<std::string> getListItems(const std::string &listName) const;

      /**
       * Retrieves the value of a given key-value item.
       *
       * @param listName
       *            the name of the key-value list
       * @param key
       *            the key of the item
       *
       * @return the value of the item
       */
      const std::string getItem(const std::string &listName, const std::string &key) const;
   private:
      const KeyValueList createKVList(
            XMLDocument *xmlDoc,
            const std::string &kvListXPath);

      bool initialised;
      ExtensionList kvLists;

      static const std::string KVLIST_EXPR();
};

inline bool DomainInfoKVResponseExtension::isInitialised() const
{
   return initialised;
}

#endif /* DOMAIN_INFO_KV_RESPONSE_EXTENSION_H_ */
