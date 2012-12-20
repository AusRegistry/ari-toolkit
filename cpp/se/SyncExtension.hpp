#ifndef __SYNCEXTENSION_HPP
#define __SYNCEXTENSION_HPP

#include <string>

#include "se/Extension.hpp"

/**
 * A bundled set of constants representing the Domain Expiry Synchronisation
 * EPP extension schema.  The namespace URI uniquely identifies the extension.
 */
class SyncExtension : public Extension
{
public:

    virtual ~SyncExtension(void) { }

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

#endif // __SYNCEXTENSION_HPP

