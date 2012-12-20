#include "session/ResultCounter.hpp"

void ResultCounter::increment (int code)
{
	std::map<int,int>::iterator p = map.find(code);

	if (p != map.end())
		p->second = p->second + 1;
	else
		map.insert(std::make_pair(code, 1));

	total++;
}

int ResultCounter::getValue (int code) const
{
	std::map<int,int>::const_iterator p = map.find(code);

	if (p != map.end())
		return p->second;
	else
		return 0;
}
