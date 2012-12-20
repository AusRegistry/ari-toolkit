#ifndef __STATS_VIEWER_HPP
#define __STATS_VIEWER_HPP

#include "se/CommandType.hpp"

/**
 * Provides the means to view individual or aggregate Session statistics.
 */
class StatsViewer
{
public:
	virtual ~StatsViewer(void) { }
	/**
	 * Get the number of responses received having the given result code.
	 */
    virtual int getResultCodeCount(int resultCode) = 0;

	/**
	 * Get the number of commands of the given type that the Session(s)
	 * associated with this viewer has/have processed recently (default: 1
	 * second).
	 */
    virtual int getCommandCount() = 0;

	/**
	 * Get the total number of commands recently processed by Sessions
	 * associated with this StatsViewer.
	 */
    virtual int getCommandCount(const CommandType* type) = 0;

	/**
	 * Get the time interval since the most recent use of a Session associated
	 * with this StatsViewer.
	 */
	virtual long getMruInterval() const = 0;
};

#endif // __STATS_VIEWER_HPP
