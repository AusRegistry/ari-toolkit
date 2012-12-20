#ifndef __E164EXTENSION_HPP
#define __E164EXTENSION_HPP

#include "se/Extension.hpp"

/**
 * A bundled set of constants representing the E164 EPP extension
 * schema (ENUM).  The namespace URI uniquely identifies the extension.
 */
class E164Extension : public Extension
{
public:
    /**
     * Get the globally unique namespace URI which identifies this extension.
     */
    virtual std::string& getURI() const;
    
    /**
     * Get the location hint for the XML schema used to validate EPP service
     * element instances using this extension.
     */
    virtual std::string& getSchemaLocation() const;
};

#endif // __E164EXTENSION_HPP

