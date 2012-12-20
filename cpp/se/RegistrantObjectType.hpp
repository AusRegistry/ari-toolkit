#ifndef REGISTRANT_OBJECT_TYPE
#define REGISTRANT_OBJECT_TYPE

#include "ObjectType.hpp"

#include <string>

class RegistrantObjectType : public ObjectType {
public:
    virtual const std::string& getName() const;
    virtual const std::string& getURI() const;
    virtual const std::string& getSchemaLocation() const;
    virtual const std::string& getIdentType() const;
};

#endif // REGISTRANT_OBJECT_TYPE

