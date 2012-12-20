#ifndef __LOGIN_COMMAND_HPP
#define __LOGIN_COMMAND_HPP

#include "se/Command.hpp"

/**
 * Use this to open an EPP session in order to perform commands only permitted
 * from within the context of a session.  Instances of this class generate, via
 * the toXML method, login service elements compliant with the login
 * specification in RFC3730.
 *  
 * @see Greeting For services available to be used
 * in the login command on the chosen EPP server.
 *
 * @see LogoutCommand To end a session opened
 * using LoginCommand.
 */
class LoginCommand : public Command
{
public:
    LoginCommand(const std::string& clID, const std::string& password);
    LoginCommand(const std::string& clID, const std::string& password,
                 const std::string* newPW);
    LoginCommand(const std::string& clID, const std::string& password,
                 const std::vector<std::string> objURIs,
                 const std::vector<std::string> extURIs);
    LoginCommand(const std::string& clID, const std::string& password,
                 const std::string& version, const std::string& lang,
                 const std::vector<std::string> objURIs,
                 const std::vector<std::string> extURIs);
    LoginCommand(const std::string& clID, 
                 const std::string& password,
                 const std::string* newPassword,
                 const std::string& version, 
                 const std::string& lang,
                 const std::vector<std::string> objURIs,
                 const std::vector<std::string> extURIs);
private:
    static const std::string DEFAULT_VERSION, DEFAULT_LANG;
    
    std::string clID, pw, newPW, version, lang;
    std::vector<std::string> objURIs, extURIs;
    
    void init(const std::string& clID,
              const std::string& password,
              const std::string& version,
              const std::string& lang,
              const std::string* newPassword,
              const std::vector<std::string> objURIs,
              const std::vector<std::string> extURIs);
};

#endif // __LOGIN_COMMAND_HPP
