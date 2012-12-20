#include <string>

#include "se/KVExtension.hpp"

std::string& KVExtension::getURI() const
{
	static std::string uri = "urn:X-ar:params:xml:ns:kv-1.0";
	return uri;
}

std::string& KVExtension::getSchemaLocation() const
{
	static std::string loc = "urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd";
	return loc;
}
