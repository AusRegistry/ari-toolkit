#ifndef __SECDNSEXTENSION_HPP
#define __SECDNSEXTENSION_HPP

#include <string>

#include "se/Extension.hpp"

class SecDNSExtension : public Extension
{
public:

    virtual ~SecDNSExtension(void) { }

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

#endif // __SECDNSEXTENSION_HPP

