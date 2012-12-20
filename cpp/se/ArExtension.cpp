#include "se/ArExtension.hpp"

std::string& ArExtension::getURI() const
{
	static std::string uri = "urn:X-ar:params:xml:ns:arext-1.0";
	return uri;
}

std::string& ArExtension::getSchemaLocation() const
{
	static std::string loc = "urn:X-ar:params:xml:ns:arext-1.0 arext-1.0.xsd";
	return loc;
}

