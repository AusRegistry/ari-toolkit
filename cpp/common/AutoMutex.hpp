#ifndef __AUTO_MUTEX_HPP
#define __AUTO_MUTEX_HPP

#include <pthread.h>

/**
 * Wrapper to ensure mutex lock/unlock matching in the presence of exceptions.
 */
class AutoMutex
{
public:
	AutoMutex(pthread_mutex_t* mutex)
		: mtx(mutex)
	{
		// XXX errors
		pthread_mutex_lock(mtx);
	}

	~AutoMutex()
	{
		// XXX errors
		pthread_mutex_unlock(mtx);
	}

private:
	pthread_mutex_t*	mtx;
};
#endif // __AUTO_MUTEX_HPP

