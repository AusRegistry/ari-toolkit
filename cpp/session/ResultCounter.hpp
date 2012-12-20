#ifndef __RESULT_COUNTER_HPP
#define __RESULT_COUNTER_HPP

#include <map>


/**
 * Keep track of the number of each result code received in responses.
 * Instances of this class provide no synchronization.  If multiple threads
 * access an instance concurrently, they must provide their own
 * synchronization.  Specifically, concurrent executions of
 * <code>increment</code> may exhibit unexpected behaviour.
 */
class ResultCounter
{
public:
	ResultCounter() : total(0) {};

	void increment(int code);

	int getValue(int code) const;

	int getTotal() const { return total; };

private:
	int total;

	std::map<int,int> map;
};
	


#endif // __RESULT_COUNTER_HPP

