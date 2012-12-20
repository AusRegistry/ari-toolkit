#ifndef __IPVERSION_HPP
#define __IPVERSION_HPP

#include "se/EnumType.hpp"

/**
 * Enumeration of Internet Protocol versions supported by EPP.
 */
class IPVersion : public EnumType
{
public:
    IPVersion (const std::string& ip);
    
    static const IPVersion* IPv4();
    static const IPVersion* IPv6();
    
    static const IPVersion* value(const std::string &name);

	static void init();
    
private:
    static std::vector<const EnumType *> values;
};

#endif // __IPVERSION_HPP
