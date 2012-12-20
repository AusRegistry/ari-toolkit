#ifndef __AUEXTENSION_HPP
#define __AUEXTENSION_HPP

#include "se/Extension.hpp"

/**
 * A bundled set of constants representing the .au EPP extension
 * schema.  The namespace URI uniquely identifies the extension.
 */
class AuExtension : public Extension
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

#endif // __AUEXTENSION_HPP

