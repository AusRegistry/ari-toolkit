#ifndef __TLS_SOCKET_HPP
#define __TLS_SOCKET_HPP

#include "session/SSLException.hpp"
#include <openssl/ssl.h>
#include <string>

class TLSSocket
{
public:

	TLSSocket(SSL_CTX* ctx, int sock, const std::string& host,
			  int port, int soTimeout) throw (SSLException);

	~TLSSocket();

    const std::string& getCertificateCommonName() const
	{
		return commonName;
	}

	int writeInt(int i) throw (SSLException);
	int readInt() throw (SSLException);
	int writeBytes(const std::string& bytes) throw (SSLException);
	void readFully(std::string& buffer, int length) throw (SSLException);

private:
	TLSSocket(const TLSSocket&);
	TLSSocket& operator=(const TLSSocket&);

	std::string commonName;

	SSL* ssl;
};

#endif // __TLS_SOCKET_HPP
