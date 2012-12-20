#include "se/AuExtensionV1.hpp"

std::string& AuExtensionV1::getURI() const
{
	static std::string uri = "urn:au:params:xml:ns:auext-1.0";
	return uri;
}

std::string& AuExtensionV1::getSchemaLocation() const
{
	static std::string loc = "urn:au:params:xml:ns:auext-1.0 auext-1.0.xsd";
	return loc;
}
