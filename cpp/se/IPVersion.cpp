#include "se/IPVersion.hpp"

using namespace std;

// Static member initialisation.
vector<const EnumType *> IPVersion::values;

const IPVersion* IPVersion::IPv4()
{
	static const IPVersion ver("v4");
	return &ver;
}

const IPVersion* IPVersion::IPv6()
{
	static const IPVersion ver("v6");
	return &ver;
}

void IPVersion::init()
{
	IPv4();
	IPv6();
}

IPVersion::IPVersion (const string &ip)
    : EnumType (values, ip)
{ }

const IPVersion* IPVersion::value (const string &name)
{
    return (const IPVersion *)EnumType::value (name, values);
}
