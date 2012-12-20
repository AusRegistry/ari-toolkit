#include "session/TLSContext.hpp"

#include "common/StringUtils.hpp"
#include "common/EPPException.hpp"

#include <openssl/ssl.h>
#include <netdb.h>
#include <unistd.h>
#include <sys/socket.h>

using namespace std;

namespace {
    int getPassword(char *buf, int size, int rwflag, void *password)
    {
        if ((unsigned)size < strlen ((char *)password) + 1) return 0;
        strcpy(buf, (char *)password);
        return (strlen (buf));
    }
}

TLSContext::~TLSContext()
{
    SSL_CTX_free(ctx);
}

TLSContext::TLSContext(const string& private_key_file,
                       const string& cert_file,
                       const string& ca_file,
                       const string& pass)
    : password(pass), ctx(NULL)
{
    SSL_load_error_strings();
    SSL_library_init();
    SSL_METHOD *meth = TLSv1_client_method();
    if (meth == NULL)
        throw SSLException("Error initialising SSL method");
    
    // SSL Context
    SSL_CTX *local_ctx = SSL_CTX_new(meth);
    if (local_ctx == NULL)
    {
        throw SSLException ("Error initialising SSL context");
    }
    
    // SSL Ciphers
    int i = SSL_CTX_set_cipher_list(local_ctx, "TLSv1");
    if (i == -1)
    {
        SSL_CTX_free(local_ctx);
        throw SSLException(
                "There was a problem initialising the SSL cipher list");
    }

    SSL_CTX_set_default_passwd_cb_userdata(local_ctx,
            static_cast<void*>(const_cast<char*>(password.c_str())));
    SSL_CTX_set_default_passwd_cb(local_ctx, getPassword);

    i = SSL_CTX_use_PrivateKey_file(local_ctx,
                                    private_key_file.c_str(),
                                    SSL_FILETYPE_PEM);
    if (i == -1)
    {
        SSL_CTX_free(local_ctx);
        throw SSLException(
                "There was a problem initialising SSL the private key '"
                + private_key_file + "'");
    }

    // Load the public certificate for our key.
    // Replace with 
    i = SSL_CTX_use_certificate_chain_file(local_ctx, cert_file.c_str());
    if (i == -1)
    {
        SSL_CTX_free(local_ctx);
        throw SSLException("Error loading cert_file '" + cert_file + "'");
    }

    // Load the CA certificate(s)
    i = SSL_CTX_load_verify_locations (local_ctx, ca_file.c_str(), NULL);

    if (i == -1)
    {
        SSL_CTX_free(local_ctx);
        throw EPPException ("Could not load CA file '" + ca_file +"'");
    }

    ctx = local_ctx;
}


auto_ptr<TLSSocket> TLSContext::createSocket(const string& host, int port, int soTimeout)
{
    int sock = tcpConnect(host, port, soTimeout);
    return auto_ptr<TLSSocket>(new TLSSocket(ctx, sock, host, port, soTimeout));
}

int TLSContext::tcpConnect(const string& host, int port, int soTimeout)
{
    struct hostent *hp = gethostbyname (host.c_str());
    if (hp == NULL)
        throw EPPException ("Could not resolve host: '" + host);

    struct sockaddr_in addr;
    bzero ((char *)&addr, sizeof (addr));
    addr.sin_addr = *(struct in_addr *)hp->h_addr_list[0];
    addr.sin_family = AF_INET;
    addr.sin_port = htons(port);

    int sock = socket(AF_INET, SOCK_STREAM, 0);

    if (sock < 0)
        throw EPPException("Could not create the socket.");

    setSocketTimeout(sock, soTimeout);

    if (connect(sock, (struct sockaddr *)&addr, sizeof (addr)) < 0)
    {
        close(sock);
        throw EPPException ("Could not connect to host");
    }

    return sock;
}

void TLSContext::setSocketTimeout(int sock, int timeoutPeriod)
{
    struct timeval snd_rcv_to_val;
    snd_rcv_to_val.tv_sec = timeoutPeriod;
    snd_rcv_to_val.tv_usec = 0;
    setsockopt(sock, SOL_SOCKET, SO_RCVTIMEO, &snd_rcv_to_val,
            sizeof(snd_rcv_to_val));
    setsockopt(sock, SOL_SOCKET, SO_SNDTIMEO, &snd_rcv_to_val,
            sizeof(snd_rcv_to_val));
}
