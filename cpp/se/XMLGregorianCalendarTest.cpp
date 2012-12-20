#include "common/Test.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include "se/IllegalArgException.hpp"

using namespace std;

/* Ordinarily wouldn't bother testing setters and getters, but it's necessary
 * here because the underlying implementation of of fractional seconds changed
 * from a pointer to primitive, and is used throughout the class.  No good
 * reason why it was done like this in the first place, other than to
 * unnecessarily provide a poor analogue of behaviour in the Java version.
 */

void testNoFractionalSecondSet()
{
    XMLGregorianCalendar calendar;
    ASSERT_EQ(calendar.getFractionalSecond(), 0);
}

void testSetZeroFractionalSecond()
{
    XMLGregorianCalendar calendar;
    calendar.setFractionalSecond(0.0);
    ASSERT_EQ(calendar.getFractionalSecond(), 0);
}

void testSetNonZeroFractionalSecond()
{
    XMLGregorianCalendar calendar;
    calendar.setFractionalSecond(0.123);
    ASSERT_EQ(calendar.getFractionalSecond(), 0.123);
}

void testSetNegativeFractionalSecond()
{
    XMLGregorianCalendar calendar;

    try
    {
        calendar.setFractionalSecond(-0.0000001);
        FAIL("Should have thrown an illegal argument exception");
    }
    catch (IllegalArgException& e)
    {
        ASSERT_EQ(e.getMessage(), "Invalid fractional seconds");
    }
}

void testSetFractionalSecondToWholeSecond()
{
    XMLGregorianCalendar calendar;

    try
    {
        calendar.setFractionalSecond(1);
        FAIL("Should have thrown an illegal argument exception");
    }
    catch (IllegalArgException& e)
    {
        ASSERT_EQ(e.getMessage(), "Invalid fractional seconds");
    }
}

/* Like fractional seconds, it seems that eons were implemented to maintain
 * parity with the Java class.  Given that it leaks memory, and we're not too
 * concerned with the usability of the usability of this toolkit a billion
 * years from now, eons have now been removed.  The following tests ensure that
 * the year field works correctly, since it's affected by the change.
 */
void testValidYearSet()
{
    XMLGregorianCalendar calendar;
    calendar.setDay(1);
    calendar.setMonth(3);
    calendar.setYear(2010);
    ASSERT_EQ(calendar.isValid(), true);
}

void testInvalidNoYearSet()
{
    XMLGregorianCalendar calendar("2015-04-08T04:23:29.0Z");
    ASSERT_EQ(calendar.isValid(), true);
    calendar.setYear(0);
    ASSERT_EQ(calendar.isValid(), false);
}

void testInvalidNonLeapYear()
{
    XMLGregorianCalendar calendar("2009-02-08T04:23:29.0Z");
    ASSERT_EQ(calendar.isValid(), true);
    calendar.setDay(29);
    ASSERT_EQ(calendar.isValid(), false);
}

void testFormatYear()
{
    XMLGregorianCalendar calendar;
    calendar.setYear(2015);
    ASSERT_EQ(calendar.format("%Y"), "2015");
}

/* No good reason why we should be allowing negative years, but there was code
 * to explicitly ensure it was output correctly, so it does appear to be
 * intended behaviour.
 */

void testValidNegativeYear()
{
    XMLGregorianCalendar calendar;
    calendar.setYear(-2102);
    ASSERT_EQ(calendar.isValid(), true);
}

void testFormatNegativeYear()
{
    XMLGregorianCalendar calendar;
    calendar.setYear(-2015);
    ASSERT_EQ(calendar.format("%Y"), "-2015");
}

int main(int argc, char* argv[])
{
    TEST_run(testNoFractionalSecondSet);
    TEST_run(testSetZeroFractionalSecond);
    TEST_run(testSetNonZeroFractionalSecond);
    TEST_run(testSetNegativeFractionalSecond);
    TEST_run(testSetFractionalSecondToWholeSecond);
    TEST_run(testValidYearSet);
    TEST_run(testInvalidNoYearSet);
    TEST_run(testInvalidNonLeapYear);
    TEST_run(testFormatYear);
    TEST_run(testValidNegativeYear);
    TEST_run(testFormatNegativeYear);
    return TEST_errorCount();
}
