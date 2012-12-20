#ifndef AEDOMAIN_OBJECT_TYPE
#define AEDOMAIN_OBJECT_TYPE

#include "ObjectType.hpp"

#include <string>

class AeDomainObjectType : public ObjectType {
public:
    virtual ~AeDomainObjectType(void) { }

    virtual const std::string& getName() const;
    virtual const std::string& getURI() const;
    virtual const std::string& getSchemaLocation() const;
    virtual const std::string& getIdentType() const;
};

#endif // AEDOMAIN_OBJECT_TYPE

