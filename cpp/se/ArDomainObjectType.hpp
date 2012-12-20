#ifndef ARDOMAIN_OBJECT_TYPE
#define ARDOMAIN_OBJECT_TYPE

#include "ObjectType.hpp"

#include <string>

class ArDomainObjectType : public ObjectType {
public:
    virtual const std::string& getName() const;
    virtual const std::string& getURI() const;
    virtual const std::string& getSchemaLocation() const;
    virtual const std::string& getIdentType() const;
};

#endif // ARDOMAIN_OBJECT_TYPE

