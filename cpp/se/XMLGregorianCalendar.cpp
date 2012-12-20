#include "se/XMLGregorianCalendar.hpp"
#include "common/StringUtils.hpp"
#include "common/EPPException.hpp"

#include <sstream>
#include <string>
#include <ctype.h>  // isdigit()
#include <stdlib.h> // atol()

using namespace std;

const char * XMLGregorianCalendar::FIELD_NAME[] =
	  { "Year",
        "Month",
        "Day",
        "Hour",
        "Minute",
        "Second",
        "Millisecond",
        "Timezone" };

const int XMLGregorianCalendar::daysInMonth[] = 
{ 0, // XML Schema months start at 1.
  31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
};

typedef enum
{
    DATETIME,
    DATE,
    TIME,
    GYEARMONTH,
    GMONTHDAY,
    GYEAR,
    GMONTH,
    GDAY
} QName;

string formatInteger (int number, int width = 0)
{
	ostringstream str;
    str.fill('0');
    str.width(width);
    str << number;

    return str.str();
}

string formatBigDecimal (long double number, int digits = 10)
{
	ostringstream str;
    str.precision(digits);
    str << number;
    return str.str();
}

class InternalError : public EPPException
{
public:
	InternalError(const string msg)
		: EPPException(msg)
	{ }
	EPP_EXCEPTION(InternalError);
};

class Parser
{
public:
    Parser (const string &format, 
            const string &value, 
            XMLGregorianCalendar *calendar)
        : calendar(calendar), format(format), value(value), flen(format.length()),
          vlen(value.length()), fidx(0), vidx(0)
    { }
    
    void parse() throw (IllegalArgException, InternalError)
    {
        while (fidx < flen)
        {
            char fch = format[fidx++];
            
            if (fch != '%')
            {
                skip(fch);
                continue;
            }
            
            switch (format[fidx++])
            {
            case 'Y':   // year
                parseAndSetYear(4);
                break;
            
            case 'M':   // month
                calendar->setMonth(parseInt(2, 2));
                break;
            
            case 'D':   // days
                calendar->setDay(parseInt(2, 2));
                break;
                
            case 'h':   // hours
                calendar->setHour(parseInt(2, 2), false);
                break;
            
            case 'm':   // minutes
                calendar->setMinute(parseInt(2, 2));
                break;
            
            case 's':   // parse seconds.
                {
                    calendar->setSecond(parseInt(2, 2));

                    if (peek() == '.')
                        calendar->setFractionalSecond(parseBigDecimal());
                }                
                break;
            
            case 'z':   // time zone. missing 'Z', or [+-]nn:nn
                {
                    char vch = peek();
                    if (vch == 'Z')
                    {
                        vidx++;
                        calendar->setTimezone(0);
                    }
                    else if (vch == '+' || vch == '-')
                    {
                        vidx++;
                        int h = parseInt(2, 2);
                        skip(':');
                        int m = parseInt(2, 2);
                        calendar->setTimezone((h * 60 + m) * (vch == '+' ? 1 : -1));
                    }
                }
                break;
                
            default:
                throw InternalError("Unexpected internal format character");
            }
            // End switch
        }
        // End while
        
        if (vidx != vlen)
            throw IllegalArgException(value);
    };
    // End parse()
    
private:
    XMLGregorianCalendar *calendar;
    string format, value;
    int flen, vlen;
    
    int fidx, vidx;
    
    char peek ()
    {
        if (vidx == vlen)
            return (char)-1;
        
        return value[vidx];
    };
    
    char read()
    {
        if (vidx == vlen)
            throw IllegalArgException(value);
        
        return value[vidx++];
    };
    
    void skip(char ch)
    {
        if (read() != ch)
            throw IllegalArgException(value);
    };
    
    int parseInt (int minDigits, int maxDigits)
    {
        int n = 0;
        char ch;
        int vstart = vidx;
        while (isdigit(ch = peek()) && (vidx - vstart) <= maxDigits)
        {
            vidx++;
            n = n*10 + ch-'0';
        }
        if ((vidx - vstart) < minDigits)
            throw IllegalArgException(value);
        
        return n;
    };
    
    void parseAndSetYear(int minDigits) throw (IllegalArgException)
    {
        int vstart = vidx;
        int n = 0;
        bool neg = false;
        
        if (peek() == '-')
        {
            vidx++;
            neg = true;
        }
        
        for (;;)
        {
            char ch = peek();
            if (!isdigit(ch))
                break;
            vidx++;
            n = n*10 + ch-'0';
        }
        
        if ((vidx - vstart) < minDigits)
            throw IllegalArgException(value);
        
        if (vidx - vstart < 7)
        {
            if (neg)
                n = -n;
            
            calendar->year = n;
        }
        else
            calendar->setYear ((long long)atol (value.substr(vstart, vidx - vstart).c_str()));
    };
    
    long double parseBigDecimal()
    {
        int vstart = vidx;
        
        if (peek() == '.')
            vidx++;
        else
            throw IllegalArgException(value);
        
        while (isdigit(peek()))
            vidx++;
        
        return strtold (value.substr(vstart, vidx - vstart).c_str(), NULL);
    };
};
// End class Parser



XMLGregorianCalendar::XMLGregorianCalendar ()
    : fractionalSecond(0), year(FIELD_UNDEFINED),
      month(FIELD_UNDEFINED), day(FIELD_UNDEFINED), timezone(FIELD_UNDEFINED), 
      hour(FIELD_UNDEFINED), minute(FIELD_UNDEFINED), second(FIELD_UNDEFINED)
{ }

XMLGregorianCalendar::XMLGregorianCalendar(const string &lexicalRepresentation)
	throw (MalformedDateException)
    : fractionalSecond(0), year(FIELD_UNDEFINED),
      month(FIELD_UNDEFINED), day(FIELD_UNDEFINED), timezone(FIELD_UNDEFINED), 
      hour(FIELD_UNDEFINED), minute(FIELD_UNDEFINED), second(FIELD_UNDEFINED)
{
    string format(""),
                lexRep = lexicalRepresentation;
                
    const string::size_type NOT_FOUND = string::npos;
    int lexRepLength = lexRep.length();
    
    // Parser needs a format string - work out which xml schema date/time
    // datatype this lexRep represents.
    if (lexRep.find ('T', 0) != NOT_FOUND)
    {
        // Found date/time separator -> must be xsd:DateTime
        format = string("%Y-%M-%DT%h:%m:%s") + "%z";
    }
    else if (lexRepLength >= 3 && lexRep[2] == ':')
    {
        // Found ':' -> must be xsd:Time
        format = string("%h:%m:%s") + "%z";
    }
    else if (lexRep.find("--", 0) == 0)
    {
        // Check for gDay/gMonth/gMonthDay
        if (lexRepLength >= 3 && lexRep[2] == '-')
        {
            // gDay
            format = string("---%D") + "%z";
        }
        else if (lexRepLength == 4 ||   // --MM
                 lexRepLength == 5 ||   // --MMZ
                 lexRepLength == 10)    // --MMSHH:MM
        {
            // gMonth
            format = string("--%M") + "%z";
        }
        else
        {
            // gMonthDay
            format = string("--%M-%D") + "%z";
        }
    }
    else
    {
        // Check for date/gYear/gYearMonth
        int countSeparator = 0;
        const size_t timezoneOffset = lexRep.find(':', 0);
        if (timezoneOffset != NOT_FOUND)
            lexRepLength -= 6;
        
        // Start at 1 to skip potential -ve sign for year.
        for (int i = 1; i < lexRepLength; i++)
            if (lexRep[i] == '-')
                countSeparator++;

        if (countSeparator == 0)
        {
            // gYear
            format = string("%Y") + "%z";        
        }
        else if (countSeparator == 1)
        {
            // gYearMonth
            format = string("%Y-%M") + "%z";
        }
        else
        {
            // date
            format = string("%Y-%M-%D") + "%z";
        }
    }
    // End if
    
	Parser p(format, lexRep, this);
	try
	{
		p.parse();
	}
	catch (InternalError& i)
	{
		MalformedDateException e("Could not parse date: " + lexicalRepresentation);
		e.causedBy(i);
		throw e;
    }
	catch (IllegalArgException& i)
	{
		MalformedDateException e("Could not parse date: " + lexicalRepresentation);
		e.causedBy(i);
		throw e;
    }
    if (!isValid()) throw MalformedDateException("common.date.format");
}

bool XMLGregorianCalendar::isValid() const
{
    if (year == 0)
        return false;

    if (getHour() == 24 &&
        (getMinute() != 0 || getSecond() != 0))
    {
        return false;
    }

    if (getMonth() == FEBRUARY)
    {
        int maxDays = 29;
        
        if (year != FIELD_UNDEFINED)
            maxDays = maximumDayInMonthFor(year, getMonth());

        if (getDay() > maxDays)
            return false;
    }
    
    return true;
}

void XMLGregorianCalendar::setMonth (int month)
{
	if ((month < JANUARY || DECEMBER < month) && month != FIELD_UNDEFINED)
		invalidFieldValue (MONTH, month);
	
	this->month = month;
}

void XMLGregorianCalendar::setDay (int day)
{
	if ((day < 1 || 31 < day) && day != FIELD_UNDEFINED)
		invalidFieldValue (DAY, day);

	this->day = day;
}

void XMLGregorianCalendar::setTimezone (int offset)
{
	if ((offset < -14*60 || 14*60 < offset) && offset != FIELD_UNDEFINED)
		invalidFieldValue (TIMEZONE, offset);

	this->timezone = offset;
}

void XMLGregorianCalendar::setTime (int hour,
								    int minute,
									int second,
									long double fractional)
{
	setHour (hour, false);
	setMinute (minute);
	if (second != 60)
		setSecond(second);
	else if ((hour == 23 && minute == 59) ||
			 (hour == 0 && minute == 0))
	{
		setSecond(second);
	}
	else
		invalidFieldValue (SECOND, second);

	setFractionalSecond(fractional);

	testHour();
}

void XMLGregorianCalendar::testHour()
{
	if (hour == 24 && (getMinute() != 0 || 
					   getSecond() != 0))
		invalidFieldValue (HOUR, getHour());
}


void XMLGregorianCalendar::setHour (int hour, bool validate)
{
	if ((hour < 0 || hour > 24) && hour != FIELD_UNDEFINED)
		invalidFieldValue (HOUR, hour);

	this->hour = hour;

	if (validate)
		testHour();
}

void XMLGregorianCalendar::setMinute (int minute)
{
	if ((minute < 0 || 59 < minute) && minute != FIELD_UNDEFINED)
		invalidFieldValue (MINUTE, minute);

	this->minute = minute;
}

void XMLGregorianCalendar::setSecond (int second)
{
	if ((second < 0 || 60 < second) &&	// leap second allows for 60
		second != FIELD_UNDEFINED)
	{
		invalidFieldValue (SECOND, second);
	}

	this->second = second;
}

void XMLGregorianCalendar::setFractionalSecond(long double fractional)
{
    if (fractional < 0.0 || fractional >= 1.0)
	   throw IllegalArgException ("Invalid fractional seconds");

    fractionalSecond = fractional;
}


QName getXMLSchemaType(int year, int month, int day, int hour, int minute, int second)
	throw (IllegalStateException)
{
    unsigned int mask =
		(year   != XMLGregorianCalendar::FIELD_UNDEFINED ? 0x20 : 0) |
		(month  != XMLGregorianCalendar::FIELD_UNDEFINED ? 0x10 : 0) |
		(day    != XMLGregorianCalendar::FIELD_UNDEFINED ? 0x08 : 0) |
		(hour   != XMLGregorianCalendar::FIELD_UNDEFINED ? 0x04 : 0) |
		(minute != XMLGregorianCalendar::FIELD_UNDEFINED ? 0x02 : 0) |
		(second != XMLGregorianCalendar::FIELD_UNDEFINED ? 0x01 : 0);

    switch (mask)
    {
    case 0x3F:
        return DATETIME;
        break;
    
    case 0x38:
        return DATE;
        break;
        
    case 0x07:
        return TIME;
        break;
    
    case 0x30:
        return GYEARMONTH;
        break;
    
    case 0x18:
        return GMONTHDAY;
        break;
    
    case 0x20:
        return GYEAR;
        break;
    
    case 0x10:
        return GMONTH;
        break;
    
    case 0x08:
        return GDAY;
        break;
    
    default:
        throw IllegalStateException("common.illegal.state");
    }
    // End switch
}

string XMLGregorianCalendar::toXMLFormat() const
{
    string formatString;
    
    switch (getXMLSchemaType(year, month, day, hour, minute, second))
    {
    case DATETIME:
        formatString = "%Y-%M-%DT%h:%m:%s%z";
        break;
    
    case DATE:
        formatString = "%Y-%M-%D%z";
        break;
    
    case TIME:
        formatString = "%h:%m:%s%z";
        break;
    
    case GMONTH:
        formatString = "--%M%z";
        break;
    
    case GDAY:
        formatString = "---%D%z";
        break;
    
    case GYEAR:
        formatString = "%Y%z";
        break;
    
    case GYEARMONTH:
        formatString = "%Y-%M%z";
        break;
    
    case GMONTHDAY:
        formatString = "--%M-%D%z";
        break;

    default:
        break;
    }
    // End switch
    
    return format (formatString);
}

string XMLGregorianCalendar::format (const string &format) const
{
    string buf;

    int fidx = 0, flen = format.length();

    while (fidx < flen)
    {
        char fch = format[fidx++];

        if (fch != '%') // not a meta char
        {
            buf.append(&fch, 1);
            continue;
        }

        switch (format[fidx++])
        {
        case 'Y':
            buf.append (formatInteger(getYear(), 4));
            break;

        case 'M':
            buf.append (formatInteger (getMonth(), 2));
            break;

        case 'D':
            buf.append (formatInteger (getDay(), 2));
            break;

        case 'h':
            buf.append (formatInteger (getHour(), 2));
            break;

        case 'm':
            buf.append (formatInteger (getMinute(), 2));
            break;

        case 's':
            buf.append (formatInteger (getSecond(), 2));
	    {
		    string frac = ".0";   // always truncate subsecond.
		    buf.append (frac);    // don't skip leading zero.
	    }
            break;

        case 'z':
            {
                int offset = getTimezone();
                if (offset == 0)
                {
                    buf.append ("Z");
                }
                else
                {
                    if (offset != FIELD_UNDEFINED)
                    {
                        if (offset < 0)
                        {
                            buf.append ("-");
                            offset = -offset;
                        }
                        else
                            buf.append ("+");

                        buf.append (formatInteger (offset/60));
                        buf.append (":");
                        buf.append (formatInteger (offset % 60));
                    }
                }
            }
            break;

        default:
            throw InternalError("Unexpected format character in output format");
        }
        // End switch
    }

    return buf;
}


long double XMLGregorianCalendar::getSeconds() const
{
    if (second == FIELD_UNDEFINED)
        return 0.0L;
    else
            return second + fractionalSecond;
}


int XMLGregorianCalendar::maximumDayInMonthFor (long long year, int month)
{
	if (month != FEBRUARY)
		return daysInMonth[month];
	else
	{
		if ((year % 400) == 0 || ((year % 100) != 0 && (year % 4) == 0))
		{
			return 29;
		}
		else
			return daysInMonth[month];
	}
}

void XMLGregorianCalendar::invalidFieldValue (int field, int value)
	throw (IllegalArgException)
{
	throw IllegalArgException(
		string("InvalidFieldValue") + 
		FIELD_NAME[field] + 
		StringUtils::makeString(value));
}
