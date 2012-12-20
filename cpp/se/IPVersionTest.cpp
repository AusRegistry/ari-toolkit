#include "common/init.hpp"
#include "common/Test.hpp"
#include "se/CLTRID.hpp"
#include "se/IPVersion.hpp"
#include "session/Timer.hpp"

#include <iostream>

using namespace std;

void doWork()
{
	init("etc/toolkit2.conf");
	{
		ASSERT_EQ(IPVersion::IPv4()->toString(), "v4");
		ASSERT_EQ(IPVersion::IPv6()->toString(), "v6");
	}
}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
