#ifndef __KVEXTENSION_HPP
#define __KVEXTENSION_HPP

#include <string>

#include "se/Extension.hpp"

/**
 * A bundled set of constants representing the KV
 * EPP extension schema.  The namespace URI uniquely identifies the extension.
 */
class KVExtension : public Extension
{
public:

    virtual ~KVExtension(void) { }

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

#endif // __KVEXTENSION_HPP

