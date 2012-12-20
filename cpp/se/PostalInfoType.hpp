#ifndef __POSTALINFOTYPE_HPP
#define __POSTALINFOTYPE_HPP

#include "se/EnumType.hpp"

/**
 * Enumeration of PostalInfo types supported by EPP.  The only difference
 * between the two types is the allowed character encoding; international
 * postal info is restricted to US ASCII, whereas local postal info element
 * content may be represented in unrestricted UTF8.
 */
class PostalInfoType : public EnumType
{
public:
    PostalInfoType(const std::string &type);
    
    static const PostalInfoType* value(const std::string &name);
    
    static const PostalInfoType* INTERNATIONAL();
    static const PostalInfoType* LOCAL();

	static void init();

private:
    static std::vector<const EnumType *> values;
};

#endif // __POSTALINFOTYPE_HPP
