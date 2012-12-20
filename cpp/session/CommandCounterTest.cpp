#include "se/StandardCommandType.hpp"
#include "session/CommandCounter.hpp"
#include "session/Timer.hpp"

#include "common/Test.hpp"
#include "common/init.hpp"

using namespace std;

void testLoginCount()
{
    Timer::setTime("20070101.010101");
    CommandCounter cc(5000);
    ASSERT_EQ(0, cc.getTotal());
    ASSERT_EQ(0, cc.getCount(StandardCommandType::LOGIN()));

    cc.increment(StandardCommandType::LOGIN());
    ASSERT_EQ(1, cc.getCount(StandardCommandType::LOGIN()));

    cc.increment(StandardCommandType::LOGIN());
    ASSERT_EQ(2, cc.getCount(StandardCommandType::LOGIN()));

    ASSERT_EQ(2, cc.getTotal());
    ASSERT_EQ(2, cc.getRecentTotal());
    ASSERT_EQ(2, cc.getRecentExactTotal());
}

void testMixedCommandsCount()
{
    Timer::setTime("20070101.010101");

    // 5 second 'recent' time.
    CommandCounter cc(5000);
    ASSERT_EQ(0, cc.getCount(StandardCommandType::POLL()));
    ASSERT_EQ(0, cc.getCount(StandardCommandType::LOGIN()));
    ASSERT_EQ(0, cc.getCount(StandardCommandType::CHECK()));
    ASSERT_EQ(0, cc.getCount(StandardCommandType::LOGOUT()));
    cc.increment(StandardCommandType::POLL());
    cc.increment(StandardCommandType::LOGIN());
    cc.increment(StandardCommandType::CHECK());
    cc.increment(StandardCommandType::LOGOUT());
    ASSERT_EQ(1, cc.getCount(StandardCommandType::POLL()));
    ASSERT_EQ(1, cc.getCount(StandardCommandType::LOGIN()));
    ASSERT_EQ(1, cc.getCount(StandardCommandType::CHECK()));
    ASSERT_EQ(1, cc.getCount(StandardCommandType::LOGOUT()));

    ASSERT_EQ(4, cc.getTotal());
    ASSERT_EQ(4, cc.getRecentTotal());
    ASSERT_EQ(4, cc.getRecentExactTotal());

    // 10 seconds later, which is greater than 5 seconds.
    Timer::setTime("20070101.010111");
    ASSERT_EQ(4, cc.getTotal());
    ASSERT_EQ(4, cc.getRecentTotal());

    // Alters cached recent total val.
    ASSERT_EQ(0, cc.getRecentExactTotal());
    ASSERT_EQ(0, cc.getRecentTotal());
    ASSERT_EQ(4, cc.getTotal());
}

void testPollCount()
{
    Timer::setTime("");
    Timer::setTime("20070101.010101");
    // 5 second recent window.
    CommandCounter cc(5000);

    ASSERT_EQ(0, cc.getRecentCount(StandardCommandType::POLL()));

    // event at 1sec
    cc.increment(StandardCommandType::POLL());
    ASSERT_EQ(1, cc.getRecentCount(StandardCommandType::POLL()));

    // advance to 3sec
    Timer::setTime("20070101.010103");
    cc.increment(StandardCommandType::POLL());
    ASSERT_EQ(2, cc.getRecentCount(StandardCommandType::POLL()));

    // advance to 8sec, (1sec event gone)
    Timer::setTime("20070101.010108");
    ASSERT_EQ(1, cc.getRecentCount(StandardCommandType::POLL()));

    // advance to 17sec (7 second event gone).
    Timer::setTime("20070101.010117");
    ASSERT_EQ(0, cc.getRecentCount(StandardCommandType::POLL()));
}

int main(int argc, char* argv[])
{
	init("etc/toolkit2.conf");

	TEST_run(testLoginCount);
	TEST_run(testMixedCommandsCount);
	TEST_run(testPollCount);

	return TEST_errorCount();
}
