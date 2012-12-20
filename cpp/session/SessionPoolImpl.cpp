#include "session/SessionPoolImpl.hpp"

#include "session/SessionLimitExceededException.hpp"
#include "session/SessionFactory.hpp"
#include "session/SessionManagerProperties.hpp"
#include "session/StatsManager.hpp"
#include "session/CommandFailedException.hpp"
#include "session/Timer.hpp"

#include "common/AutoMutex.hpp"
#include "common/SystemProperties.hpp"
#include "common/Logger.hpp"

#include <sys/time.h>
#include <sstream>
#include <set>
#include <memory>
#include <typeinfo> // for 'bad_cast'
#include <errno.h>
#include <climits>
#include <limits>

using namespace std;

const float SessionPoolImpl::PI_STO_FRACT = 0.4;
const string SessionPoolImpl::pname("com.ausregistry.cpptoolkit.session");


SessionPoolImpl::SessionPoolImpl(SessionPoolProperties* props)
	: lastSession(NULL), sessionsInUse(0), maximumSize(props->getMaximumPoolSize()),
	  pollInterval((long)(PI_STO_FRACT * (float)props->getServerTimeout())),
	  waitTimeout(props->getWaitTimeout()), clientTimeout(props->getClientTimeout()),
	  properties(props),
	  debugLogger(Logger::getLogger(pname + ".debug")),
	  userLogger(Logger::getLogger(pname + ".user"))
{
	pthread_mutex_init(&mtx, NULL);
	pthread_cond_init(&cond, NULL);
	ostringstream minPollStr;
	minPollStr << MIN_ACCEPTABLE_POLL_INTERVAL;
	
	istringstream configMinPollStr(
		SystemProperties::getProperty("client.pollinterval.min", minPollStr.str()));

	long minPollInterval;
	configMinPollStr >> minPollInterval;
	pollInterval = max<long>(pollInterval, minPollInterval);

	// wait for lesser of 200ms and 10% command limit interval,
	// but no less that 50ms if command limit exceeded
	limitExceededWaitTimeout =
		max(min(props->getCommandLimitInterval() / 10, 200L),
		    50L);
}


SessionPoolImpl::~SessionPoolImpl()
{
	pthread_mutex_destroy(&mtx);
	pthread_cond_destroy(&cond);
}

const Greeting* SessionPoolImpl::getLastGreeting()
	throw (SessionConfigurationException, SessionOpenException, EPPInterruptedException)
{
	if (lastSession == NULL) init();
	return lastSession->getGreeting();
}

void SessionPoolImpl::init()
	throw (SessionConfigurationException, SessionOpenException, EPPInterruptedException)
{
	releaseSession(getSession());
}

long SessionPoolImpl::keepAlive() throw (EPPIOException)
{
	debugLogger->LOG_FINEST("enter");

	for (PoolIter session = pool.begin(); session != pool.end(); ++session)
	{
		long mruInterval = (*session)->getStatsManager()->getMruInterval();
		if (pollInterval < mruInterval && mruInterval < clientTimeout)
		{
			(*session)->keepAlive();
		}
	}
	debugLogger->LOG_FINEST("exit");
	return pollInterval;
}

/// Synchronised.
void SessionPoolImpl::empty()
{
	AutoMutex lock(&mtx);
	if (pool.size() == 0) return;

	for (PoolIter session = pool.begin(); session != pool.end(); ++session)
	{
		if (*session != NULL)
		{
			(*session)->close();
			delete *session;
		}
	}
	pool.clear();
}

/// Synchronised.
void SessionPoolImpl::clean() throw (SessionConfigurationException, SessionOpenException)
{
	AutoMutex lock(&mtx);
	int count = pool.size();
	empty();
	for (int i=0; i < count; i++)
	{
		try
		{
			openSession();
		}
		catch (SessionOpenException& e)
		{
			try
			{
				dynamic_cast<SessionLimitExceededException&>(e);
				continue;
			}
			catch (bad_cast& e)
			{
				throw e;
			}
		}
	}
}

Session* SessionPoolImpl::getSession()
	throw (SessionConfigurationException, SessionOpenException, EPPInterruptedException)
{
	return getSession(NULL);
}


Session* SessionPoolImpl::getSession(const CommandType* type)
	throw (SessionConfigurationException, SessionOpenException, EPPInterruptedException)
{
	debugLogger->LOG_FINEST("enter");
	Session* acquiredSession = NULL;
	int failCount = 0;

	do
	{
		acquiredSession = getBestAvailableSession(type);
		if (acquiredSession == NULL)
		{
			if (pool.size() < maximumSize)
			{
				try
				{
					openSession();
				}
				catch (SessionOpenException& soe)
				{
					try
					{
						dynamic_cast<SessionLimitExceededException&>(soe);

						vector<string> args;
						args.push_back("<<total>>");
						args.push_back("<<cutoff>>");

						vector<string> vals;
						ostringstream v;
						v << maximumSize;
						vals.push_back(v.str());
						v.str("");
						v << pool.size();
						vals.push_back(v.str());

						userLogger->warning(
								ErrorPkg::getMessage("epp.session.open.fail.limit_exceeded",
									args, vals));
						waitForRelease();
					}
					catch (bad_cast&)
					{
						if (isRepeatableFailedLogin(soe, failCount))
						{
							ostringstream fc;
							fc << failCount;
							userLogger->warning(soe.getMessage());
							userLogger->warning(ErrorPkg::getMessage(
								"epp.server.failure.retry",
								"<<count>>",
								fc.str()));
							failCount++;
						}
						else throw soe;
					}
				}
			}
			else
			{
				waitForRelease();
			}
		}
	}
	while (acquiredSession == NULL);
	debugLogger->LOG_FINEST("exit");
	return acquiredSession;
}

bool SessionPoolImpl::isRepeatableFailedLogin(const EPPException& e, int count)
{
	try
	{
		dynamic_cast<const LoginException&>(e);
		dynamic_cast<const CommandFailedException&>(e);
		return count < MAX_ACCEPTABLE_FAIL_COUNT;
	}
	catch (bad_cast&)
	{
		return false;
	}
}

/// Synchronized.
Session* SessionPoolImpl::getBestAvailableSession(const CommandType* type)
	throw (EPPInterruptedException)
{
	debugLogger->LOG_FINEST("enter");

	Session* bestSession = NULL;
	long min = LONG_MAX;
	AutoMutex lock(&mtx);

	for (PoolIter session = pool.begin(); session != pool.end(); ++session)
	{
		if ((*session)->isAvailable()) {

			int cutoff = INT_MAX;
			long  cc;
			if (type == NULL) {
				cc = (*session)->getStatsManager()->getCommandCount();
			} else {
				cutoff = properties->getCommandLimit(type);
				cc = (*session)->getStatsManager()->getCommandCount(type);
			}

			if (cc <= cutoff && cc < min) {
				min = cc;
				bestSession = *session;
			}
		}
	}

	if (bestSession != NULL)
	{
		try
		{
			// A previous user of this session to re-acquire, so this may timeout.
			bestSession->acquire();
			sessionsInUse++;
		}
		catch (EPPTimeoutException& e)
		{
			userLogger->warning("Timeout acquiring 'best' session.");
			bestSession = NULL;
		}

	}

	debugLogger->LOG_FINEST("exit");
	return bestSession;
}

void SessionPoolImpl::openSession()
	throw (SessionConfigurationException, SessionOpenException)
{
	Session * newSession = SessionFactory::newInstance(properties);
	newSession->open();
	{
		AutoMutex lock(&mtx);
		lastSession = newSession;
		pool.insert(newSession);
		pthread_cond_signal(&cond);
	}
}

void SessionPoolImpl::waitForRelease()
{
	AutoMutex lock(&mtx);
	if (sessionsInUse < pool.size())
	{
		// There are only inappropriate session available.
        struct timespec until(Timer::msOffset2abs(limitExceededWaitTimeout));

		if (pthread_cond_timedwait(&cond, &mtx, &until) != ETIMEDOUT)
		{
			userLogger->severe("This thread should not be interupted.");
		}
	}
	while (sessionsInUse >= pool.size() && pool.size() >= maximumSize)
	{
        struct timespec until(Timer::msOffset2abs(waitTimeout));

		if (pthread_cond_timedwait(&cond, &mtx, &until) != ETIMEDOUT)
		{
			userLogger->severe("This thread should not be interupted.");
		}
	}
}

void SessionPoolImpl::releaseSession(Session * session) {
	if (session == NULL) {
		return;
	}

	if (session->isInvalid()) {
		session->close();
		session->release();
		{
			AutoMutex lock(&mtx);
			pool.erase(session);
			delete session;
			sessionsInUse--;
			pthread_cond_signal(&cond);
		}
	}
	else if (!session->isOpen())
	{
		session->release();
		{
			pool.erase(session);
			AutoMutex lock(&mtx);
			sessionsInUse--;
			pthread_cond_signal(&cond);
		}
	}
	else
	{
		session->release();
		{
			AutoMutex lock(&mtx);
			sessionsInUse--;
			pthread_cond_signal(&cond);
		}
	}
}

int SessionPoolImpl::getCommandCount(const CommandType* type)
{
	int res = 0;
	for(PoolIter session = pool.begin(); session != pool.end(); ++session)
	{
		res += (*session)->getStatsManager()->getCommandCount(type);
	}
	return res;
}

int SessionPoolImpl::getCommandCount()
{
	int res = 0;
	for(PoolIter session = pool.begin(); session != pool.end(); ++session)
	{
		res += (*session)->getStatsManager()->getCommandCount();
	}
	return res;
}

long SessionPoolImpl::getMruInterval() const
{
	long minInterval = numeric_limits<long>::max();

	for(PoolIter session = pool.begin(); session != pool.end(); ++session)
	{
		long mruInterval = (*session)->getStatsManager()->getMruInterval();
		minInterval = min<long>(mruInterval,minInterval);
	}
	return minInterval;
}

int SessionPoolImpl::getResultCodeCount(int resultCode)
{
	int res = 0;
	for(PoolIter session = pool.begin(); session != pool.end(); ++session)
	{
		res += (*session)->getStatsManager()->getResultCodeCount(resultCode);
	}
	return res;
}


