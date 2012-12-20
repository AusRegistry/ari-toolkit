#include "se/Period.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include "se/DomainRegistrantTransferCommand.hpp"
#include "se/StandardCommandType.hpp"
#include "se/RegistrantTransferCommandType.hpp"
#include "se/EPPDateFormatter.hpp"
#include "se/Period.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include "se/KVExtension.hpp"
#include "se/RegistrantObjectType.hpp"
#include "se/CLTRID.hpp"
#include "xml/XMLHelper.hpp"

namespace {
RegistrantObjectType& registrantObjectType() {
       static RegistrantObjectType* registrantObjectType = new RegistrantObjectType();
       return *registrantObjectType;
   }
   KVExtension& kvExtension() {
       static KVExtension* kvExtension = new KVExtension();
       return *kvExtension;
   }
   const RegistrantTransferCommandType rtrnType;
} // anonymous namespace

DomainRegistrantTransferCommand::DomainRegistrantTransferCommand(const std::string& name,
                                   const XMLGregorianCalendar& curExpDate,
                                   const std::string& kvListName,
                                   const std::string& explanation,
                                   const Period* period) :
                                   SendSE()
{
   command = xmlWriter->appendChild(xmlWriter->appendChild(
           xmlWriter->getRoot(), "extension"), "command",
           registrantObjectType().getURI());

   DOMElement *cmdElement = xmlWriter->appendChild(command, rtrnType.getCommandName());

   XMLHelper::setTextContent(xmlWriter->appendChild(cmdElement, registrantObjectType().getIdentType()), name);

   std::string curExpDateStr = EPPDateFormatter::toXSDate(curExpDate);
   XMLHelper::setTextContent(
              xmlWriter->appendChild(cmdElement, "curExpDate"), curExpDateStr);

   if (period != NULL) {
       period->appendPeriod(xmlWriter, cmdElement);
   }

   kvListElement = xmlWriter->appendChild(cmdElement, "kvlist", kvExtension().getURI());
   XMLHelper::setAttribute(kvListElement, "name", kvListName);

   XMLHelper::setTextContent(
         xmlWriter->appendChild(cmdElement, "explanation"), explanation);
}

std::string DomainRegistrantTransferCommand::toXMLImpl()
{
   KeyValueList::const_iterator itemIterator;
   for (itemIterator = kvList.begin();
         itemIterator != kvList.end();
         itemIterator++)
   {
      DOMElement *itemElement = xmlWriter->appendChild(kvListElement, "item");
      XMLHelper::setAttribute(itemElement, "key", itemIterator->first);
      XMLHelper::setTextContent(itemElement, itemIterator->second);
   }

   XMLHelper::setTextContent(
       xmlWriter->appendChild(command, "clTRID"),
       CLTRID::nextVal());

   return xmlWriter->toXML();
}
