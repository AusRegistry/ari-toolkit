#include "se/E164Extension.hpp"

std::string& E164Extension::getURI() const
{
    static std::string uri = "urn:ietf:params:xml:ns:e164epp-1.0";
    return uri;
}

std::string& E164Extension::getSchemaLocation() const
{
    static std::string schemaLocation = 
        "urn:ietf:params:xml:ns:e164epp-1.0 e164epp-1.0.xsd";
    return schemaLocation;
}

