#ifndef __TLSCONTEXT_H
#define __TLSCONTEXT_H

#include "session/TLSSocket.hpp"
#include "session/SSLException.hpp"
#include "common/EPPException.hpp"
#include <string>
#include <memory>

class TLSContext
{
public:

    TLSContext(const std::string& private_key_file,
               const std::string& cert_file,
               const std::string& ca_file,
               const std::string& pass);
    ~TLSContext();
    
    std::auto_ptr<TLSSocket> createSocket(const std::string& host,
                                          int port, int soTimeout);

private:
    int tcpConnect(const std::string& host, int port, int soTimeout);
    void setSocketTimeout(int sock, int timeoutPeriod);

    std::string commonName;
    std::string password;
    SSL_CTX* ctx;
};

#endif  // __TLSCONTEXT_H
