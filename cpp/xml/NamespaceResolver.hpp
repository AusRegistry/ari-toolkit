#ifndef __NAMESPACERESOLVER_HPP
#define __NAMESPACERESOLVER_HPP

#include <xalanc/PlatformSupport/PrefixResolver.hpp>
#include <xalanc/XalanDOM/XalanDOMString.hpp>

#include <vector>
#include <map>

/** EPP specific namespace prefix resolver use when parsing XPath expressions
 * withing repsonse documents.
 */
class NamespaceResolver: public xalanc::PrefixResolver
{
public:
	NamespaceResolver(const char* baseURI = NULL);
	~NamespaceResolver();

	// NB only first uri to prefix map call has an effect.
	void addNamespace(const char *uri, const char *prefix);

	// PrefixResolver inteface.
	const xalanc::XalanDOMString* getNamespaceForPrefix(const xalanc::XalanDOMString& prefix) const;
	const xalanc::XalanDOMString& getURI() const;

private:
	struct XalanStrCmp
	{
		// Provide a less-than operator for the map.
		bool operator()(const xalanc::XalanDOMString& lhs, const xalanc::XalanDOMString& rhs) const
		{
			return lhs.compare(rhs) < 0;
		}
	};
	typedef std::map<xalanc::XalanDOMString, xalanc::XalanDOMString, XalanStrCmp> MapType;
	MapType prefixMap;

	xalanc::XalanDOMString baseURI;
};

#endif //__NAMESPACERESOLVER_HPP
