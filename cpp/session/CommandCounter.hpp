#ifndef __COMMANDCOUNTER_H
#define __COMMANDCOUNTER_H

#include <list>
#include <map>
#include <string>

class CommandType;

class CommandCounter
{
public:

	/// @param resetInterval Milliseconds.
    CommandCounter(int resetInterval = DEFAULT_RESET_INTERVAL);
	~CommandCounter();

	/** Count a command of type 'type'. */
    void increment(const CommandType* type);

	/** Get the approximate number of commands of the given type recorded by
	 * this counter. */
	int getRecentCount(const CommandType* type);

	/** Get the total number of commands of the given type recorded by this
	 * counter. */
	int getCount(const CommandType* type) const;
    
	/** Cet the the total number of commands of all types recorded by this
	 * counter. */
	int getTotal() const { return total; }

	/** Get an approximation of the total number of commands processed recently. */
	int getRecentTotal() const { return recentTotal; }

	/** Get the number of commands of the given type processed recently (within
	 * the reset interval from now). */
	int getRecentExactTotal();
    
private:
    CommandCounter(const CommandCounter& rhs);
    CommandCounter& operator=(const CommandCounter& rhs);

	// milliseconds.
	typedef long long Time;	// milli seconds.

	// oldest times towards the head of the list
	typedef std::list<Time> TimeList;
	typedef std::map<StringUtils::HashType, TimeList *> CommandTimeMapType;
    CommandTimeMapType recentMap;
	int recentTotal;

	typedef std::map<StringUtils::HashType, int> CommandCountMapType;
	CommandCountMapType totalMap;

	long resetInterval;
    int total;

	static const int INITIAL_TYPE_MAP_SIZE = 8,
					 DEFAULT_RESET_INTERVAL = 1000;

	int clean(TimeList * timeList);
};

#endif // __COMMANDCOUNTER_H
