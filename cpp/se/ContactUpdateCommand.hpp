#ifndef __CONTACTUPDATECOMMAND_HPP
#define __CONTACTUPDATECOMMAND_HPP

#include "se/UpdateCommand.hpp"
class Status;
class IntPostalInfo;
class LocalPostalInfo;
class Disclose;

/**
 * Use this to request the update of a contact object provisioned in an EPP
 * Registry.  Instances of this class generate RFC3730 and RFC3733 compliant
 * contact update EPP command service elements via the toXML method.  The
 * response expected from a server should be handled by a Response object.
 *
 * @see Response
 */
class ContactUpdateCommand : public UpdateCommand
{
public:
    /**
     * The complete set of attributes of a contact which may be updated as per
     * RFC3733.
     */
    ContactUpdateCommand (const std::string &id,
                          const std::string &pw,
                          const std::vector<Status> *addStatuses = NULL,
                          const std::vector<std::string> *remStatuses = NULL,
                          const IntPostalInfo *newIntPostalInfo = NULL,
                          const LocalPostalInfo *newLocPostalInfo = NULL,
                          const std::string *newVoice = NULL,
                          const std::string *newVoiceExt = NULL,
                          const std::string *newFax = NULL,
                          const std::string *newFaxExt = NULL,
                          const std::string *newEmail = NULL,
                          const Disclose *disclose = NULL);
};
#endif // __CONTACTUPDATECOMMAND_HPP
