#ifndef __ADDREMTYPE_HPP
#define __ADDREMTYPE_HPP

#include "se/EnumType.hpp"

/**
 * Represents add or remove in EPP domain update commands.  This is used
 * internally by the DomainAdd and DomainRem classes to control the behaviour
 * of their superclass DomainAddRem.
 * 
 */
class AddRemType : public EnumType
{
public:
    AddRemType(const std::string &typeVal);
    
    static const AddRemType* ADD();
    static const AddRemType* REM();
    
    static const AddRemType* value(const std::string &name);

	static void init();

private:
    static std::vector<const EnumType *> values;
};    
    
#endif // __ADDREMTYPE_HPP
