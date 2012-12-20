#ifndef DOMAIN_REGISTRANT_TRANSFER_COMMAND_HPP_
#define DOMAIN_REGISTRANT_TRANSFER_COMMAND_HPP_

#include <string>

#include "se/Command.hpp"
#include "se/KVDefs.hpp"

class XMLGregorianCalendar;
class Period;

/**
 * In cases where the legal registrant of a domain name has changed, this
 * class should be used to request a transfer of registrant.  This is a
 * different action to correcting extension data which was originally specified
 * incorrectly, and should only be used in the situation described.
 *
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The \c toXML() method in Command serialises this object to
 * XML.
 */
class DomainRegistrantTransferCommand : public SendSE
{
   public:
      /**
       * Request that the domain name be transferred to the legal entity
       * specified by the extension data that is provided in the key-value list.
       *
       * @param name
       *            The domain name to transfer.
       *
       * @param curExpDate
       *            The current expiry of the identified domain name. This is
       *            required in order to prevent repeated transfer of the name due
       *            to protocol transmission failures.
       *
       * @param period
       *            The desired new validity period, starting from the time the
       *            transfer completes successfully.
       *
       * @param kvListName
       *            The name under which the list of key-value items are aggregated.
       *
       * @param explanation
       *            An explanation of how the transfer was effected.
       */
      DomainRegistrantTransferCommand (const std::string& name,
                                         const XMLGregorianCalendar& curExpDate,
                                         const std::string& kvListName,
                                         const std::string& explanation,
                                         const Period* period = NULL);

      /**
       * Adds a key-value item into the list, to be included in the command when
       * the XML is generated.
       */
      void addItem(const std::string &key, const std::string &value)
      {
         kvList[key] = value;
      }
   private:
      virtual std::string toXMLImpl();

      DOMElement *command;
      DOMElement *kvListElement;
      KeyValueList kvList;
};

#endif /* DOMAIN_REGISTRANT_TRANSFER_COMMAND_HPP_ */
