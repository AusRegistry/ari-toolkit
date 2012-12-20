#ifndef __OBJECTTYPE_HPP
#define __OBJECTTYPE_HPP

#include <string>

/**
 * The Extensible Provisioning Protocol defines object-specific commands.
 * Object mappings for EPP map those commands to the defined object.  Such
 * objects are identified definitively by namespace URI.  Instances of the
 * ObjectType class provide that identification within the toolkit, as well
 * as convenience methods for simplifying the usage of EPP object-specific
 * commands.
 */
class ObjectType
{
public:
    virtual ~ObjectType(void) = 0;
    /**
     * Get the commonly used name for the object identified by this type.
     */
    virtual const std::string & getName() const = 0;
    /**
     * Get the namespace URI of the object identified by this type.  This is
     * the authoritative key for distinguishing between object types.
     */
    virtual const std::string & getURI() const = 0;
    /**
     * Get the schema location hint normally prescribed for this object type.
     */
    virtual const std::string & getSchemaLocation() const = 0;
    /**
     * Get the label name of the primary identifier used in EPP service elements
     * mapped to the object identified by this type.
     */
    virtual const std::string & getIdentType() const = 0;
};

inline ObjectType::~ObjectType(void)
{
    return;
}

#endif // __OBJECTTYPE_HPP

