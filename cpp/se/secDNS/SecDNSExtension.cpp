#include "SecDNSExtension.hpp"

std::string& SecDNSExtension::getURI() const
{
	static std::string uri = "urn:ietf:params:xml:ns:secDNS-1.1";
	return uri;
}

std::string& SecDNSExtension::getSchemaLocation() const
{
	static std::string loc = "urn:ietf:params:xml:ns:secDNS-1.1 secDNS-1.1.xsd";
	return loc;
}
