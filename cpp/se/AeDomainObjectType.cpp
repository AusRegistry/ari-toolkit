#include "AeDomainObjectType.hpp"

#include <string>

const std::string& AeDomainObjectType::getName() const {
    static const std::string name = "aedom";
    return name;
}

const std::string& AeDomainObjectType::getURI() const {
    static const std::string uri = "urn:X-ae:params:xml:ns:aedomain-1.0";
    return uri;
}

const std::string& AeDomainObjectType::getSchemaLocation() const {
    static const std::string schemaLocation =
            "urn:X-ae:params:xml:ns:aedomain-1.0 aedomain-1.0.xsd";
    return schemaLocation;
}

const std::string& AeDomainObjectType::getIdentType() const {
    static const std::string ident = "name";
    return ident;
}

