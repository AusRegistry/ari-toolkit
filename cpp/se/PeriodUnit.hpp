#ifndef __PERIOD_UNIT_HPP
#define __PERIOD_UNIT_HPP

#include "se/EnumType.hpp"

/**
 * Enumeration of units supported by EPP for period elements.
 */
class PeriodUnit : public EnumType
{
public:
    PeriodUnit (const std::string &description);
    
    static const PeriodUnit* MONTHS();
    static const PeriodUnit* YEARS();

    static const PeriodUnit* value(const std::string &name);

	static void init();
private:
    static std::vector<const EnumType *> values;
};

#endif // __PERIOD_UNIT_HPP
