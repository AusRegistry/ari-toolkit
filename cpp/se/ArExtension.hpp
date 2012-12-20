#ifndef __AREXTENSION_HPP
#define __AREXTENSION_HPP

#include "se/Extension.hpp"

/**
 * A bundled set of constants representing the AusRegistry EPP extension
 * schema.  The namespace URI uniquely identifies the extension.
 */
class ArExtension : public Extension
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

#endif // __AREXTENSION_HPP

