#ifndef AUDOMAIN_OBJECT_TYPE
#define AUDOMAIN_OBJECT_TYPE

#include "ObjectType.hpp"

#include <string>

class AuDomainObjectType : public ObjectType {
public:
    virtual const std::string& getName() const;
    virtual const std::string& getURI() const;
    virtual const std::string& getSchemaLocation() const;
    virtual const std::string& getIdentType() const;
};

#endif // AUDOMAIN_OBJECT_TYPE

