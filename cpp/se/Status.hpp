#ifndef __STATUS_HPP
#define __STATUS_HPP

#include "common/StringUtils.hpp"

/**
 * This class models EPP object statuses.  Instances of this class can be used
 * to update object statuses and are also returned by subclasses of
 * InfoResponse to provide access to the attributes of status values of the
 * queried object. 
 *  
 * @see UpdateCommand
 * @see InfoResponse
 */
class Status
{
public:
    /// @TODO SWIG/Perl workaround - figure out why SWIG wants an empty constructor.
    Status () {}

    Status (const std::string &status,
            const std::string &rationale = "",
            const std::string &lang = "")
        : status(status), 
          rationale(rationale), 
          lang(lang) {};
    
    const std::string& toString() const { return status; };
    const std::string& getRationale() const { return rationale; };
    const std::string& getLanguage() const { return lang; };

    bool equals (Status *obj) const
    {
        return (obj->toString().compare(status) == 0) ? true : false;
    };

    int hashCode() const { return (int)StringUtils::hashCode(status); };

private:
    std::string status, rationale, lang;
};

#endif // __STATUS_HPP
