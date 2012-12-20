#ifndef __SESSION_POOL_IMPL_HPP
#define __SESSION_POOL_IMPL_HPP

#include "session/SessionPool.hpp"
#include "session/StatsViewer.hpp"

#include <vector>
#include <set>

class Transaction;
class SessionPoolProperties;
class Session;
class Logger;

class SessionPoolImpl : public SessionPool, public StatsViewer
{
public:
	/// @para props Must outlive the SessionPoolImpl instance.
	SessionPoolImpl(SessionPoolProperties* props);
	virtual ~SessionPoolImpl();

	void setMaxSize (int size) { maximumSize = size; };
	std::set<Session *>& viewSessions() { return pool; };
	const Greeting* getLastGreeting() throw (SessionConfigurationException, SessionOpenException, EPPInterruptedException);
	void init() throw (SessionConfigurationException,
					   SessionOpenException,
					   EPPInterruptedException);
	long keepAlive() throw (EPPIOException);
	void empty();
	void clean() throw (SessionConfigurationException, SessionOpenException);

	Session* getSession()
		throw (SessionConfigurationException,
		       SessionOpenException,
		       EPPInterruptedException);

	Session* getSession(const CommandType *type)
		throw (SessionConfigurationException,
		       SessionOpenException,
		       EPPInterruptedException);

	Session* getSession(const std::vector<Transaction>& txs)
		throw (SessionConfigurationException,
		       SessionOpenException,
		       EPPInterruptedException);

	void releaseSession (Session *session);

	const StatsViewer & getStatsViewer() const { return dynamic_cast<const StatsViewer&>(*this); };

	int getCommandCount();
	int getCommandCount(const CommandType* type);
	long getMruInterval() const;
	int getResultCodeCount(int resultCode);
	
private:
	static const int MAX_ACCEPTABLE_FAIL_COUNT = 3;
	static const float PI_STO_FRACT;
	static const long MIN_ACCEPTABLE_POLL_INTERVAL = 120000;

	static const std::string pname;

	typedef std::set<Session *>::iterator PoolIter;
	std::set<Session *> pool;

	Session *lastSession;
	unsigned int sessionsInUse;
	unsigned int maximumSize;
	/// Milliseconds.
	long pollInterval, waitTimeout, limitExceededWaitTimeout;

	/// Minimum duration to keep sessions alive.
	long clientTimeout;

	SessionPoolProperties* properties;
	Logger *debugLogger, *userLogger;

	pthread_mutex_t mtx;
	pthread_cond_t cond;

	bool isRepeatableFailedLogin(const EPPException& e, int count);

	Session * getBestAvailableSession 
		(const CommandType *type) throw (EPPInterruptedException);	// TBD synchronised

	void openSession() throw (SessionConfigurationException,
							  SessionOpenException);
	void waitForRelease();	// TBD synchronised
	
	
};
				
#endif // __SESSION_POOL_IMPL_HPP
