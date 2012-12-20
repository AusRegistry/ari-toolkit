#include "se/StandardObjectType.hpp"

using namespace std;

vector<const EnumType *> StandardObjectType::values;
vector<string> StandardObjectType::stdURIs;

const StandardObjectType* StandardObjectType::DOMAIN()
{
	static StandardObjectType obj(
		"domain",
		"urn:ietf:params:xml:ns:domain-1.0",
		"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd",
		"name");
	return &obj;
}

const StandardObjectType* StandardObjectType::HOST()
{
	static StandardObjectType obj(
		"host",
		"urn:ietf:params:xml:ns:host-1.0",
		"urn:ietf:params:xml:ns:host-1.0 host-1.0.xsd",
		"name");
	return &obj;
}

const StandardObjectType* StandardObjectType::CONTACT()
{
	static StandardObjectType obj(
		"contact",
		"urn:ietf:params:xml:ns:contact-1.0",
		"urn:ietf:params:xml:ns:contact-1.0 contact-1.0.xsd",
		"id");
	return &obj;
}

StandardObjectType::StandardObjectType(const string& name,
                                       const string& uri,
                                       const string& schemaLocation,
                                       const string& identType)
    : EnumType(values, name, uri, schemaLocation, identType)
{ }

const StandardObjectType* StandardObjectType::value(const string &name)
{
    return (const StandardObjectType *)EnumType::value (name, values);
}


const vector<string>& StandardObjectType::getStandardURIs()
{
    if (stdURIs.size() == 0)
    {
        stdURIs.push_back(DOMAIN()->getURI());
        stdURIs.push_back(HOST()->getURI());
        stdURIs.push_back(CONTACT()->getURI());
    }
    
    return stdURIs;
}

void StandardObjectType::init()
{
    getStandardURIs();
	DOMAIN();
	HOST();
	CONTACT();
}
