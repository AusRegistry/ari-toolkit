#include "session/SSLException.hpp"
#include <openssl/err.h>

namespace {

	const char* ssl_err(SSL* ssl, int ret)
	{
		switch (SSL_get_error(ssl, ret))
		{
			case SSL_ERROR_NONE:
				return "No error.";
			case SSL_ERROR_ZERO_RETURN:
				return "The underlying connection has been closed.";
			case SSL_ERROR_WANT_READ:
				return "SSL_ERROR_WANT_READ";
			case SSL_ERROR_WANT_WRITE:
				return "SSL_ERROR_WANT_WRITE";
			case SSL_ERROR_WANT_CONNECT:
				return "SSL_ERROR_WANT_CONNECT";
			case SSL_ERROR_WANT_ACCEPT:
				return "SSL_ERROR_WANT_ACCEPT";
			case SSL_ERROR_SYSCALL:
                if (ERR_get_error() != 0)
                    return "SSL_ERROR_SYSCALL";
                return "EOF was observed that violates the TLS protocol";
			case SSL_ERROR_WANT_X509_LOOKUP:
				return "SSL_ERROR_WANT_X509_LOOKUP";
			case SSL_ERROR_SSL:
				return "SSL_ERROR_SSL";
			default:
				return "(Unknown SSL error state)";
		}
	}
}

SSLException::SSLException(const std::string& msg, SSL* ssl, int sslErrorCode)
	: EPPException(msg + ": " + ssl_err(ssl, sslErrorCode))
{ }
