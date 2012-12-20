#include "session/TLSSocket.hpp"
#include "common/StringUtils.hpp"
#include "session/SSLException.hpp"
#include "common/EPPException.hpp"

#include <string>
#include <unistd.h>
#include <netinet/in.h>  // htonl

using namespace std;

TLSSocket::TLSSocket(SSL_CTX* ctx, int sock, const string& host,
		int port, int soTimeout)
	throw (SSLException)
{
    //SSL_CTX_set_timeout(ctx, 12L);

	ssl = SSL_new(ctx);
	if (ssl == NULL) throw SSLException("Failed to create the SSL context"); 

	// Bind the socket to the SSL object.
	int ret = SSL_set_fd (ssl, sock);

	if (ret != 1)
	{
		SSLException e("Failed to set the file descriptor", ssl, ret);
		SSL_shutdown(ssl);
		throw e;
	}

	SSL_set_connect_state(ssl);
	ret = SSL_do_handshake (ssl);

	if (ret != 1)
	{
		SSLException e("Failed during SSL handshake", ssl, ret);
		SSL_shutdown(ssl);
		throw e;
	}

	if ((ret = SSL_get_verify_result(ssl)) != X509_V_OK)
	{
		SSLException e("Certificate verify failed", ssl, ret);
		SSL_shutdown(ssl);
		throw e;
	}

	// Common name
	char cn[256];
	X509 *peer = SSL_get_peer_certificate(ssl);
	X509_NAME_get_text_by_NID(X509_get_subject_name(peer), NID_commonName, cn, 256);
	commonName = cn;
	X509_free(peer);
}

TLSSocket::~TLSSocket()
{
	SSL_shutdown(ssl);
	close(SSL_get_fd(ssl));
	SSL_free(ssl);
}

int TLSSocket::writeInt(int i) throw (SSLException)
{
	int j=htonl(i);
	int res = SSL_write(ssl, &j, sizeof(j));
	if (res < 1)
	{
		throw SSLException("SSL failuring during write", ssl, res);
	}
	return res;
}

int TLSSocket::writeBytes(const string& bytes) throw (SSLException)
{
	unsigned int bytesWritten = 0;
	while (bytesWritten < bytes.size())
	{
		int res = SSL_write(
				ssl,
				bytes.data() + bytesWritten,
				bytes.size() - bytesWritten);
		if (res < 1)
		{
			throw SSLException("SSL failuring during write", ssl, res);
		}
		bytesWritten += res;
	}
	return bytes.size();
}

int TLSSocket::readInt() throw (SSLException)
{
	string buff;
	readFully(buff, sizeof(int));
	return ntohl(*reinterpret_cast<const int *>(buff.data()));
}

void TLSSocket::readFully(string& buffer, int length) throw (SSLException)
{
	const int buffLen = 2048;
	char buff[buffLen];
	while (length > 0)
	{
		int toRead = std::min(buffLen, length);
		int amountRead = SSL_read(ssl, buff, toRead);
		if (amountRead < 1)
		{
			throw SSLException("SSL read returned zero bytes", ssl, amountRead);
		}
		buffer.append(string(buff, amountRead));
		length -= amountRead;
	}
}
