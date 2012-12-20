#include "xml/XMLDocument.hpp"
#include "se/Response.hpp"
#include "se/ResponseExtension.hpp"
#include "se/DomainInfoKVResponseExtension.hpp"

/*
 * Have to use static funcion instead of static variable
 * since there is not guarantee about the construct/destruct
 * order of a static instance of any types
 */
const std::string DomainInfoKVResponseExtension::KVLIST_EXPR()
{
	return EXTENSION_EXPR() + "/kv:infData/kv:kvlist";
}

DomainInfoKVResponseExtension::DomainInfoKVResponseExtension() :
   initialised(false),
   kvLists()
{
}

void DomainInfoKVResponseExtension::fromXML(XMLDocument *xmlDoc)
{
   int kvListCount = xmlDoc->getNodeCount("count(" + KVLIST_EXPR() + ")");

   if (kvListCount == 0)
   {
      initialised = false;
   }
   else
   {
      for (int i = 1; i <= kvListCount; i++)
      {
          const std::string currentKVListXPath =
                ReceiveSE::replaceIndex(KVLIST_EXPR() + "[IDX]", i);
          const std::string kvListName =
                xmlDoc->getNodeValue(currentKVListXPath + "/@name");

          kvLists[kvListName] = createKVList(xmlDoc, currentKVListXPath);
      }

      initialised = true;
   }
}

const std::set<std::string> DomainInfoKVResponseExtension::getLists() const
{
   std::set<std::string> kvListNames;

   ExtensionList::const_iterator extensionListIterator;

   for (extensionListIterator = kvLists.begin();
         extensionListIterator != kvLists.end();
         extensionListIterator++)
   {
      kvListNames.insert(extensionListIterator->first);
   }

   return kvListNames;
}

const std::set<std::string> DomainInfoKVResponseExtension::getListItems(
      const std::string &listName) const
{
   std::set<std::string> itemNames;
   ExtensionList::const_iterator extensionListIterator = kvLists.find(listName);

   if (extensionListIterator != kvLists.end())
   {
      KeyValueList::const_iterator keyValueListIterator;

      for (keyValueListIterator = extensionListIterator->second.begin();
            keyValueListIterator != extensionListIterator->second.end();
            keyValueListIterator++)
      {
         itemNames.insert(keyValueListIterator->first);
      }
   }

   return itemNames;
}

const std::string DomainInfoKVResponseExtension::getItem(
      const std::string &listName,
      const std::string &key) const
{
   std::string itemValue;
   ExtensionList::const_iterator extensionListIterator = kvLists.find(listName);

   if (extensionListIterator != kvLists.end())
   {
      KeyValueList::const_iterator keyValueListIterator =
            extensionListIterator->second.find(key);

      if (keyValueListIterator != extensionListIterator->second.end())
      {
         itemValue = keyValueListIterator->second;
      }
   }

   return itemValue;
}

const KeyValueList DomainInfoKVResponseExtension::createKVList(
      XMLDocument *xmlDoc,
      const std::string &kvListXPath)
{
   int kvItemCount = xmlDoc->getNodeCount("count(" + kvListXPath + "/kv:item)");

   KeyValueList kvList;

   for (int i = 1; i <= kvItemCount; i++)
   {
      std::string itemXPath = ReceiveSE::replaceIndex(kvListXPath + "/kv:item[IDX]", i);
      std::string key = xmlDoc->getNodeValue(itemXPath + "/@key");
      std::string value = xmlDoc->getNodeValue(itemXPath);
      kvList[key] = value;
   }

   return kvList;
}
