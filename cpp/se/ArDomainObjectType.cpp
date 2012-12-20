#include <string>

#include "ArDomainObjectType.hpp"

const std::string & ArDomainObjectType::getName() const {
    static const std::string name = "ardom";
    return name;
}

const std::string & ArDomainObjectType::getURI() const {
    static const std::string uri = "urn:X-ar:params:xml:ns:ardomain-1.0";
    return uri;
}

const std::string & ArDomainObjectType::getSchemaLocation() const {
    static const std::string schemaLocation =
            "urn:X-ar:params:xml:ns:ardomain-1.0 ardomain-1.0.xsd";
    return schemaLocation;
}

const std::string & ArDomainObjectType::getIdentType() const {
    static const std::string ident = "name";
    return ident;
}

