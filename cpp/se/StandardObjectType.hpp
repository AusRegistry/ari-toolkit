#ifndef __STANDARDOBJECTTYPE_HPP
#define __STANDARDOBJECTTYPE_HPP

#include "se/EnumType.hpp"
#include "se/ObjectType.hpp"

#include <string>
#include <vector>

#ifdef DOMAIN
#undef DOMAIN
#endif


class StandardObjectType : public ObjectType, public EnumType
{
public:
    StandardObjectType(const std::string& name,
                       const std::string& uri,
                       const std::string& schemaLocation,
                       const std::string& identType);
    
    const std::string& getName() const { return getFirst(); };
    const std::string& getURI() const { return getSecond(); };
    const std::string& getSchemaLocation() const { return getThird(); };
    const std::string& getIdentType() const { return getFourth(); };

    static const std::vector<std::string>& getStandardURIs();
    
    static const StandardObjectType* value(const std::string &name);

    static const StandardObjectType* DOMAIN();
    static const StandardObjectType* HOST();
    static const StandardObjectType* CONTACT();

    static void init();
    

private:
    static std::vector<const EnumType *> values;
    static std::vector<std::string> stdURIs;
};

#endif // __STANDARDOBJECTTYPE_HPP
