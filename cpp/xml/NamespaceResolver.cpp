#include "xml/NamespaceResolver.hpp"
#include <algorithm>

using namespace xalanc;
using namespace std;

NamespaceResolver::NamespaceResolver(const char* baseURI)
{
    if (baseURI)
    {
        this->baseURI = XalanDOMString(baseURI);
    }
    addNamespace("urn:ietf:params:xml:ns:epp-1.0", "e");
    addNamespace("urn:ietf:params:xml:ns:contact-1.0", "contact");
    addNamespace("urn:ietf:params:xml:ns:host-1.0", "host");
    addNamespace("urn:ietf:params:xml:ns:domain-1.0", "domain");
    addNamespace("urn:ietf:params:xml:ns:e164epp-1.0", "e164epp");

    addNamespace("urn:au:params:xml:ns:auext-1.0", "auextv1");
    addNamespace("urn:X-au:params:xml:ns:auext-1.1", "auext");
    addNamespace("urn:X-au:params:xml:ns:audomain-1.0", "audom");

    addNamespace("urn:X-ae:params:xml:ns:aeext-1.0", "aeext");
    addNamespace("urn:X-ae:params:xml:ns:aedomain-1.0", "aedom");

    addNamespace("urn:X-ar:params:xml:ns:arext-1.0", "arext");
    addNamespace("urn:X-ar:params:xml:ns:ardomain-1.0", "ardom");
    addNamespace("urn:X-ar:params:xml:ns:sync-1.0", "sync");
    addNamespace("urn:X-ar:params:xml:ns:kv-1.0", "kv");
    addNamespace("urn:X-ar:params:xml:ns:registrant-1.0", "registrant");

    addNamespace("urn:ietf:params:xml:ns:secDNS-1.1", "secDNS");
}

NamespaceResolver::~NamespaceResolver()
{ }

const xalanc::XalanDOMString& NamespaceResolver::getURI() const
{
    return baseURI;
}

const XalanDOMString* NamespaceResolver::getNamespaceForPrefix(const XalanDOMString& prefix) const
{
    MapType::const_iterator i = prefixMap.find(prefix);
    if (i == prefixMap.end())
    {
        return NULL;
    }
    return &(i->second);
}

//// NB only first uri to prefix map call has an effect.
void NamespaceResolver::addNamespace(const char *uri, const char *prefix)
{
    prefixMap.insert(std::make_pair(XalanDOMString(prefix), XalanDOMString(uri)));
}
