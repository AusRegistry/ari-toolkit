#ifndef __SSL_EXCEPTION_HPP
#define __SSL_EXCEPTION_HPP

#include "common/EPPException.hpp"
#include <openssl/ssl.h>
#include <string>

/// An SSL related exception.
class SSLException : public EPPException
{
public:
	SSLException(const std::string& msg)
		: EPPException (msg)
	{ }
	/// Report a message related to an SSL exception.
	/// @param ssl A valid SSL context.
	/// @param sslErrorCode The return code from the failed SSL library call.
	SSLException(const std::string& msg, SSL* ssl, int sslErrorCode);
	EPP_EXCEPTION(SSLException); 
};

#endif
