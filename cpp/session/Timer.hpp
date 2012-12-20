#ifndef __TIMER_HPP
#define __TIMER_HPP

#include "common/ParameterSyntaxException.hpp"
#include <time.h>

class Timer
{
public:

    /// milliseconds since epoch.
    static long long now();

    /// argument and result is milliseconds.
    static long long msDiff(long long compareTime);

    /// Once called, future calls to now() will return this
    /// date until setTime(...) is called again with an empty string.
    static void setTime(const std::string& dateString) throw (ParameterSyntaxException);

    /**
     * Give the value, in struct timespec representation, of the time
     * represented by the given offset (in milliseconds) in the future from
     * the current time as understood by the Timer.
     *
     * @param msOffset the number of milliseconds in the future to give a
     * time for.
     */
    static struct timespec msOffset2abs(long long msOffset);

private:
    
    static clockid_t getClock();

    static bool useRealTime;

    // milliseconds.
    static long long fixedTime;
};

#endif // __TIMER_HPP

