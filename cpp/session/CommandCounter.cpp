#include "se/CommandType.hpp"

#include "session/CommandCounter.hpp"
#include "session/Timer.hpp"

CommandCounter::CommandCounter (int resetInterval)
	: recentTotal(0), resetInterval(resetInterval), total(0)
{ }

CommandCounter::~CommandCounter()
{
	CommandTimeMapType::iterator p;

	for (p = recentMap.begin(); p != recentMap.end(); p++)
		delete p->second;
}

void CommandCounter::increment(const CommandType* type)
{
	StringUtils::HashType hash = type->hash();

	// increment total for command type.
	CommandCountMapType::iterator q;
	if ((q = totalMap.find(hash)) != totalMap.end())
	{
		q->second += 1;
	}
	else
	{
		totalMap.insert(std::make_pair(hash, 1));
	}

	// add command time to recent list for that command
	TimeList *timeList;
	CommandTimeMapType::iterator p = recentMap.find(hash);
	if ((p = recentMap.find(hash)) != recentMap.end())
	{
		timeList = p->second;
		clean(timeList);
	}
	else
	{
		timeList = new TimeList;
		recentMap.insert(std::make_pair(hash, timeList));
	}
	timeList->push_back(Timer::now());
	recentTotal++;
	total++;
}

int CommandCounter::getCount(const CommandType* type) const
{
	StringUtils::HashType hash = type->hash();
    
    CommandCountMapType::const_iterator p;
	if((p = totalMap.find(hash)) == totalMap.end())
		return 0;
	else
		return p->second;
}

int CommandCounter::getRecentCount(const CommandType* type)
{
	StringUtils::HashType hash = type->hash();

    CommandTimeMapType::iterator p = recentMap.find(hash);
    if (p == recentMap.end())
	{
		return 0;
	}
	else
	{
		return clean(p->second);
	}
}

int CommandCounter::getRecentExactTotal()
{
	CommandTimeMapType::iterator i;
	for(i = recentMap.begin(); i != recentMap.end(); ++i)
	{
		clean(i->second);
	}
	return recentTotal;
}

int CommandCounter::clean(TimeList *timeList)
{
	TimeList::iterator listIter;

	// 'now' is milliseconds.
	long long now = Timer::now();
	while ((listIter = timeList->begin()) != timeList->end())
	{
		if (now - *listIter > static_cast<long long>(resetInterval))
		{
			timeList->pop_front();
			recentTotal--;
		}
		else
		{
			break;
		}
	}
	return timeList->size();
}
