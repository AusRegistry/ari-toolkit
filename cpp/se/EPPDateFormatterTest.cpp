#include "se/EPPDateFormatter.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include <string>
#include <vector>
#include <iostream>

#include "common/Test.hpp"

void doWork()
{
	const std::string dateStr = "2008-02-11T01:02:03.3Z";
	XMLGregorianCalendar* dt = EPPDateFormatter::fromXSDateTime(dateStr);
	std::string res;
	if (dt) res = EPPDateFormatter::toXSDateTime(*dt);
	ASSERT_EQ(res, "2008-02-11T01:02:03.0Z");
}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
