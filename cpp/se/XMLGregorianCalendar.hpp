#ifndef __XMLGREGORIANCALENDAR_HPP
#define __XMLGREGORIANCALENDAR_HPP

#include "common/IllegalStateException.hpp"
#include "common/EPPException.hpp"
#include "se/IllegalArgException.hpp"

#include <string>

class InvalidDateCombination : public EPPException
{
public:
	InvalidDateCombination (const std::string &msg) 
		: EPPException(msg) { }
	EPP_EXCEPTION(InvalidDateCombination);
};

class MalformedDateException : public EPPException
{
public:
	MalformedDateException(const std::string& msg)
		: EPPException(msg) { }
	EPP_EXCEPTION(MalformedDateException);
};


class Parser;
class XMLGregorianCalendar
{
public:   
    friend class Parser;

	enum { FIELD_UNDEFINED = -1 };

	enum
	{
		JANUARY = 1,
		FEBRUARY,
		MARCH,
		APRIL,
		MAY,
		JUNE,
		JULY,
		AUGUST,
		SEPTEMBER,
		OCTOBER,
		NOVEMBER,
		DECEMBER
	};

    
	std::string toXMLFormat() const;
    std::string format (const std::string &format) const;
    
    static XMLGregorianCalendar * createDate (int year,
                                              int month,
                                              int day,
                                              int timezone);
    static XMLGregorianCalendar * createTime (int hours,
                                              int minutes,
                                              int seconds,
                                              int timezone);
    static XMLGregorianCalendar * createTime (int hours,
                                              int minutes,
                                              int seconds,
                                              long double fractionalSecond,
                                              int timezone);
    static XMLGregorianCalendar * createTime (int hours,
                                              int minutes,
                                              int seconds,
                                              int milliseconds,
                                              int timezone);

    bool isValid() const;
            
    long double getFractionalSecond () const { return fractionalSecond; };
    int getYear() const { return year; };
    int getMonth() const { return month; };
    int getHour() const { return hour; };
    int getMinute() const { return minute; };
    int getSecond() const { return second; };
    int getDay() const { return day; };
    int getTimezone() const { return timezone; };
    long double getSeconds() const;

	void setYear (int year) { this->year = year; }
	void setMonth (int month);
	void setDay (int day);
	void setTimezone (int offset);
	void setTime (int hour, 
				  int minute, 
				  int second, 
				  long double fractional = 0);
	void setHour (int hour, bool validate = true);
	void setMinute (int minute);
	void setSecond (int second);
	void setFractionalSecond (long double fractional);
    
    
    XMLGregorianCalendar (const std::string &lexicalRepresentation)
		throw (MalformedDateException);
    XMLGregorianCalendar ();
protected:
    XMLGregorianCalendar (long year,
                          int  month,
                          int  day,
                          int  hour,
                          int  minute,
                          int  second,
                          long double fractionalSecond,
                          int  timezone);
    
private:
    XMLGregorianCalendar (long year,
                          int  month,
                          int  day,
                          int  hour,
                          int  minute,
                          int  second,
                          int  millisecond,
                          int  timezone);

    long double fractionalSecond;
    int year,
        month,
        day,
        timezone,
        hour,
        minute,
        second;
    
    static const long BILLION = 1000000000;
    
    enum { YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, MILLISECOND, TIMEZONE };
    
    static const char * FIELD_NAME[];
	static const int daysInMonth[];
                                   
	void testHour ();

	static int maximumDayInMonthFor (long long year, int month);

	void invalidFieldValue (int field, int value) 
		throw (IllegalArgException);
};    


#endif // __XMLGREGORIANCALENDAR_HPP
