#ifndef __STATSMANAGER_H
#define __STATSMANAGER_H

#include "session/StatsViewer.hpp"

/**
 * Extend the capabilities of a StatsViewer by supporting modification of the
 * available statistics.
 */
class StatsManager : public StatsViewer
{
public:
	virtual ~StatsManager(void) { }
	/**
	 * Increment the result count for the given code.  This is reflected in the
	 * return value of {@link
	 * com.ausregistry.jtoolkit2.session.StatsViewer#getResultCodeCount}.
	 */
    virtual void incResultCounter(int resultCode) = 0;

	/**
	 * Increment the command count for the given command type.  This is
	 * reflected in the return value of {@link
	 * com.ausregistry.jtoolkit2.session.StatsViewer#getCommandCount}.
	 */
    virtual void incCommandCounter(const CommandType* type) = 0;
};


#endif // __STATSMANAGER_H
