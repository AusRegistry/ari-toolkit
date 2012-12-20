#include "se/AuExtension.hpp"

std::string& AuExtension::getURI() const
{
	static std::string uri = "urn:X-au:params:xml:ns:auext-1.1";
	return uri;
}

std::string& AuExtension::getSchemaLocation() const
{
	static std::string loc = "urn:X-au:params:xml:ns:auext-1.1 auext-1.1.xsd";
	return loc;
}

