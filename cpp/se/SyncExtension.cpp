#include <string>

#include "se/SyncExtension.hpp"

std::string& SyncExtension::getURI() const
{
	static std::string uri = "urn:X-ar:params:xml:ns:sync-1.0";
	return uri;
}

std::string& SyncExtension::getSchemaLocation() const
{
	static std::string loc = "urn:X-ar:params:xml:ns:sync-1.0 sync-1.0.xsd";
	return loc;
}

