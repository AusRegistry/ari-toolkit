#include "se/PeriodUnit.hpp"

std::vector<const EnumType *> PeriodUnit::values;

const PeriodUnit* PeriodUnit::MONTHS()
{
	static const PeriodUnit unit("m");
	return &unit;
}

const PeriodUnit* PeriodUnit::YEARS()
{
	static const PeriodUnit unit("y");
	return &unit;
}

PeriodUnit::PeriodUnit(const std::string &description)
    : EnumType(values, description)
{ }

const PeriodUnit* PeriodUnit::value (const std::string &name)
{
    try
    {
        return (const PeriodUnit *)EnumType::value (name, values);
    }
    catch (IllegalArgException)
    {
        return YEARS();
    }
}

void PeriodUnit::init()
{
	MONTHS();
	YEARS();
}
