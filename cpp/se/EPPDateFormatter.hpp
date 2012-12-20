#ifndef __EPPDATEFORMATTER_HPP
#define __EPPDATEFORMATTER_HPP

#include <string>
#include <memory>
#include "se/XMLGregorianCalendar.hpp"

class EPPDateFormatter
{
public:
    static std::string toXSDateTime(const XMLGregorianCalendar& date);
    static std::string toXSDate(const XMLGregorianCalendar& date);
	static XMLGregorianCalendar* fromXSDateTime(const std::string& dateStr);
};

#endif // __EPPDATEFORMATTER_HPP
