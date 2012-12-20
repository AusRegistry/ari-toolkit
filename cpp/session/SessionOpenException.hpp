#ifndef __SESSION_OPEN_EXCEPTION_HPP
#define __SESSION_OPEN_EXCEPTION_HPP

#include "common/EPPException.hpp"

/**
 * Container exception for all exceptions which may be thrown in trying to
 * open a Session.  Examples of such exceptions are IOException,
 * SSLHandshakeException, GreetingException and LoginException.
 */
class SessionOpenException : public EPPException
{
public:
	SessionOpenException(const std::string &msg)
		: EPPException(msg) { };
	EPP_EXCEPTION(SessionOpenException);
};

#endif // __SESSION_OPEN_EXCEPTION_HPP
