#ifndef __AEEXTENSION_HPP
#define __AEEXTENSION_HPP

#include "se/Extension.hpp"

/**
 * A bundled set of constants representing the .ae EPP extension
 * schema.  The namespace URI uniquely identifies the extension.
 */
class AeExtension : public Extension
{
public:

    virtual ~AeExtension(void) { }

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

#endif // __AEEXTENSION_HPP

