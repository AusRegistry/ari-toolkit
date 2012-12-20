#include "se/CLTRID.hpp"
#include "session/Timer.hpp"

#include <sstream>
#include <iostream>

using namespace std;

string& CLTRID::clID()
{
	static string id = "";
	return id;
}

int& CLTRID::val()
{
	static int v = 0;
	return v;
}

string CLTRID::nextVal()
{
	// Good until 2034.
	time_t now = Timer::now() / 1000;
    struct tm * tm_now = localtime(&now);
    char tmbuf[30];
    
	ostringstream retval;
    strftime(tmbuf, sizeof(tmbuf), ".%Y%m%d.%H%M%S.", tm_now);
    retval << clID() << tmbuf << val();
    inc();
    return retval.str();
}

void CLTRID::setClID (const string &clID)
{
	CLTRID::clID() = clID;
    val() = 0;
}

void CLTRID::inc()
{
    val() = (val() + 1) % maxVal;
}
