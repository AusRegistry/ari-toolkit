#include "session/SessionFactory.hpp"
#include "session/SessionManagerProperties.hpp"
#include "common/SystemProperties.hpp"
#include "common/init.hpp"
#include "session/Session.hpp"
#include "session/StatsManager.hpp"

#include "session/TestEnvironment.hpp"
#include "common/Test.hpp"
#include <string>
#include <iostream>
#include <memory>
using namespace std;

static std::string TEST_SE =
	"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><hello/></epp>";


void doWork()
{
	init("etc/toolkit2.conf");
	{
		TestEnvironment props;
		auto_ptr<Session> sess(SessionFactory::newInstance(&props));
		sess->open();
		sess->close();
	}
	{
		TestEnvironment props;
		auto_ptr<Session> sess(SessionFactory::newInstance(&props));

		sess->open();
		sess->writeXML(TEST_SE);
		sess->read();

		sess->close();
	}
	{
		TestEnvironment props;
		auto_ptr<Session> sess(SessionFactory::newInstance(&props));

		sess->open();

		StatsManager* sm(sess->getStatsManager());
		sm->incResultCounter(1000);
		ASSERT_EQ(sm->getResultCodeCount(1000), 1);

		sess->close();
	}
	{
		TestEnvironment props;
		auto_ptr<Session> sess(SessionFactory::newInstance(&props));

		sess->open();

		StatsViewer* sv(sess->getStatsManager());
		ASSERT_EQ(sv->getCommandCount(), 1);
		ASSERT_EQ(sv->getCommandCount(StandardCommandType::LOGIN()), 1);
		ASSERT_EQ(sv->getCommandCount(StandardCommandType::LOGOUT()), 0);

		sess->close();
	}
	{
		TestEnvironment props;
		auto_ptr<Session> sess(SessionFactory::newInstance(&props));

		sess->open();

		StatsManager* sm(sess->getStatsManager());
		ASSERT_EQ(sm->getResultCodeCount(1000), 0);

		sess->close();
	}
}

int main(int argc, char* argv[])
{
	TEST_run(doWork);
	return TEST_errorCount();
}
