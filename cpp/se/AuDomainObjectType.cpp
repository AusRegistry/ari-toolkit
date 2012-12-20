#include "AuDomainObjectType.hpp"

#include <string>

const std::string& AuDomainObjectType::getName() const {
    static const std::string name = "audom";
    return name;
}

const std::string& AuDomainObjectType::getURI() const {
    static const std::string uri = "urn:X-au:params:xml:ns:audomain-1.0";
    return uri;
}

const std::string& AuDomainObjectType::getSchemaLocation() const {
    static const std::string schemaLocation =
            "urn:X-au:params:xml:ns:audomain-1.0 audomain-1.0.xsd";
    return schemaLocation;
}

const std::string& AuDomainObjectType::getIdentType() const {
    static const std::string ident = "name";
    return ident;
}

