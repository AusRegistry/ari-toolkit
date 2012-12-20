#include "se/PostalInfoType.hpp"

// Static member initialisation.
std::vector<const EnumType *> PostalInfoType::values;

const PostalInfoType* PostalInfoType::INTERNATIONAL()
{
	static const PostalInfoType type("int");
	return &type;
}

const PostalInfoType* PostalInfoType::LOCAL()
{
	static const PostalInfoType type("loc");
	return &type;
}

PostalInfoType::PostalInfoType(const std::string &type)
    : EnumType (values, type)
{ }

void PostalInfoType::init()
{
	INTERNATIONAL();
	LOCAL();
}

const PostalInfoType* PostalInfoType::value (const std::string &name)
{
    return (const PostalInfoType *)EnumType::value (name, values);
}
