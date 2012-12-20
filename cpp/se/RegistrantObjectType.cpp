#include "RegistrantObjectType.hpp"

#include <string>

const std::string& RegistrantObjectType::getName() const {
    static const std::string name = "registrant";
    return name;
}

const std::string& RegistrantObjectType::getURI() const {
    static const std::string uri = "urn:X-ar:params:xml:ns:registrant-1.0";
    return uri;
}

const std::string& RegistrantObjectType::getSchemaLocation() const {
    static const std::string schemaLocation =
            "urn:X-ar:params:xml:ns:registrant-1.0 registrant-1.0.xsd";
    return schemaLocation;
}

const std::string& RegistrantObjectType::getIdentType() const {
    static const std::string ident = "name";
    return ident;
}

