#include "session/Timer.hpp"

#include <string>
#include <ctime>

using namespace std;

#include "common/Test.hpp"
int main(int argc, char* argv[])
{
	const std::string bad_time = "123";
	const std::string good_time = "20070101.010101";
	const std::string good_time_string = "Mon Jan  1 01:01:01 2007\n";

	std::string timeString;
	time_t time;

	try
	{
		Timer::setTime(bad_time);
		ASSERT_EQ(1, 2);
	}
	catch (ParameterSyntaxException& pse)
	{
		// pass
	}
	Timer::setTime(good_time);
	time = static_cast<time_t>(Timer::now() / 1000);
	timeString = ctime(&time);
	ASSERT_EQ(timeString, good_time_string);

	// revert to wall clock.
	Timer::setTime("");
	time = static_cast<time_t>(Timer::now() / 1000);
	timeString = ctime(&time);
	ASSERT_NEQ(timeString, good_time_string);
}
