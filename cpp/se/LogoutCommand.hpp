#ifndef __LOGOUT_COMMAND_HPP
#define __LOGOUT_COMMAND_HPP

#include "se/Command.hpp"
#include "se/StandardCommandType.hpp"

/**
 * Use this to close an open EPP session.  This should be used to cleanly end a
 * session which is no longer needed, or when changing an EPP client password.
 * Instances of this class generate, via the toXML method, logout service elements
 * compliant with the logout specification in RFC3730.
 *
 * @see LoginCommand The session should have been
 * opened using this command prior to logging out.
 */
class LogoutCommand : public Command
{
public:
    LogoutCommand ()
        : Command(StandardCommandType::LOGOUT()) { }
};

#endif // __LOGOUT_COMMAND_HPP
