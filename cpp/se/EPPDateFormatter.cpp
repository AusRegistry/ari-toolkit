#include "se/EPPDateFormatter.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include <sstream>

std::string EPPDateFormatter::toXSDateTime(const XMLGregorianCalendar& date)
{
	return date.toXMLFormat();
}

std::string EPPDateFormatter::toXSDate(const XMLGregorianCalendar& date)
{
	return date.format("%Y-%M-%D");
}

XMLGregorianCalendar* EPPDateFormatter::fromXSDateTime(const std::string &dateTime)
{
	try
	{
		return new XMLGregorianCalendar(dateTime);
	}
	catch (MalformedDateException& e)
	{
		return NULL;
	}
}
