#include "session/Timer.hpp"
#include <time.h>
#include <strings.h>

using namespace std;

long long Timer::fixedTime;
bool Timer::useRealTime(true);

long long Timer::now()
{
    if (!useRealTime) return fixedTime;
    struct timespec ts;
    clock_gettime(Timer::getClock(), &ts);
    return static_cast<long long>(ts.tv_sec) * 1000 + static_cast<long long>(ts.tv_nsec) / 1000000;
}

clockid_t Timer::getClock()
{
    return CLOCK_REALTIME;
}

long long Timer::msDiff(long long compareTime)
{
    return now() - compareTime;
}

void Timer::setTime(const string& time)
    throw (ParameterSyntaxException)
{
    const char* format = "%Y%m%d.%H%M%S";
    if (time == "")
    {
        // Revert to wall time for later calls.
        useRealTime = true;
        return;
    }

    // On success, strptime() should return a pointer to the address of the
    // null char.
    
    struct tm tm;
    bzero(&tm, sizeof(tm));
    // We do not scan for the 'daylight saving setting', so explicitly
    // undefine it.
    tm.tm_isdst = -1;
    time_t t;
    const char* res = strptime(time.c_str(), format, &tm);
    if (res == NULL || res != time.c_str() + time.size() || (t = mktime(&tm)) == -1)
    {
        throw ParameterSyntaxException(
            "Time '" + time + "' not in form 'YYYYMMDD.hhmmss' or is an illegal time.");
    }

    // mktime is in seconds, Date in milli.
    fixedTime = static_cast<long long>(t) * 1000L;
    useRealTime = false;
}

struct timespec Timer::msOffset2abs(long long msOffset)
{
    const unsigned int ms_per_sec = 1000;
    const unsigned int ns_per_ms = 1000;

    struct timespec now;
    struct timespec until;

    clock_gettime(Timer::getClock(), &now);
    until.tv_sec = now.tv_sec + msOffset / ms_per_sec;
    until.tv_nsec = now.tv_nsec + msOffset * ns_per_ms;

    return until;
}

