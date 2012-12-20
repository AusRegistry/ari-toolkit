#include "se/AddRemType.hpp"

// Static member initialisation.
std::vector<const EnumType *> AddRemType::values;

const AddRemType* AddRemType::ADD()
{
	static const AddRemType arm("add");
	return value("add");
}

const AddRemType* AddRemType::REM()
{
	static const AddRemType arm("rem");
	return value("rem");
}

AddRemType::AddRemType(const std::string& typeVal)
    : EnumType (values, typeVal)
{ }

const AddRemType* AddRemType::value(const std::string &name)
{
    return (const AddRemType *)EnumType::value (name, values);
}

void AddRemType::init()
{
	ADD();
	REM();
}
