#ifndef __CONTACT_CREATE_COMMAND_HPP
#define __CONTACT_CREATE_COMMAND_HPP

#include "se/CreateCommand.hpp"
class PostalInfo;
class LocalPostalInfo;
class Disclose;

/**
 * Use this to request that a contact object be provisioned in an EPP Registry.
 * Instances of this class generate RFC3730 and RFC3733 compliant contact
 * create EPP command service elements via the toXML method.
 *
 * @see ContactCreateResponse
 */ 
class ContactCreateCommand : public CreateCommand
{
public:
    /**
     * Provision a contact with the specified details.  This constructor allows
     * specification of any and all parameters for a contact create command.
     *
     * @param id The new contact's identifier.
     *
     * @param pw The password to assign to the contact (also known as authInfo
     * or authorisation information).
     *
     * @param postalInfo Postal information for the new contact.  If
     * localPostalInfo is also specified, then this MUST be IntPostalInfo.
     *
     * @param localPostalInfo Local postal information for the new contact.
     *
     * @param voice The contact's voice telephone number.
     *
     * @param voiceExt The extension for the contact's voice telephone number,
     * if applicable.
     *
     * @param fax The contact's fax telephone number.
     *
     * @param faxExt The extension for the contact's fax telephone number, if
     * applicable.
     *
     * @param email The contact's email address.
     *
     * @param disclose Disclosure request information, which may modify what
     * information is disclosed by the Registry system in response to queries.
     * Note that the server may not accept specification of this parameter, or
     * may ignore any requests described by this parameter.
     */
    ContactCreateCommand (const std::string& id,
                          const std::string& pw,
                          const PostalInfo* postalInfo,
                          const std::string& email,
                          const LocalPostalInfo* localPostalInfo = NULL,
                          const std::string* voice = NULL,
                          const std::string* voiceExt = NULL,
                          const std::string* fax = NULL,
                          const std::string* faxExt = NULL,
                          const Disclose* disclose = NULL);
};

#endif // __CONTACT_CREATE_COMMAND_HPP
