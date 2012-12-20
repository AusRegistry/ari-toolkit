#include "se/EnumType.hpp"
#include "common/ErrorPkg.hpp"


EnumType::EnumType(std::vector<const EnumType *> &values,
                    const std::string &first,
                    const std::string &second /* = "" */,
                    const std::string &third  /* = "" */,
                    const std::string &fourth /* = "" */)
    : first(first), second(second), third(third), fourth(fourth)
{
    values.push_back (this);
}

const EnumType* EnumType::value(const std::string &name,
                                std::vector<const EnumType *> &values)
	throw (IllegalArgException)
{
    for (unsigned int i = 0; i < values.size(); i++)
    {
        if (values[i]->toString() == name)
        {
            return values[i];
        }
    }
    throw IllegalArgException(
		ErrorPkg::getMessage(
			"common.exception.illegal.arg.enum", "<<val>>", name));
			
}
