#include "se/AeExtension.hpp"

std::string& AeExtension::getURI() const
{
	static std::string uri = "urn:X-ae:params:xml:ns:aeext-1.0";
	return uri;
}

std::string& AeExtension::getSchemaLocation() const
{
	static std::string loc = "urn:X-ae:params:xml:ns:aeext-1.0 aeext-1.0.xsd";
	return loc;
}

