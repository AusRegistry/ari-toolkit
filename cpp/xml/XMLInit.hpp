#ifndef __XML_INIT_HPP
#define __XML_INIT_HPP

#include <xalanc/XalanTransformer/XalanTransformer.hpp>
#include <xalanc/XercesParserLiaison/XercesDOMSupport.hpp>
#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/util/PlatformUtils.hpp>

class XMLInit
{
public:
	XMLInit()
	{
		xercesc::XMLPlatformUtils::Initialize();
		xalanc::XalanTransformer::initialize();
	}
	~XMLInit()
	{
		xalanc::XalanTransformer::terminate();
		xercesc::XMLPlatformUtils::Terminate();
	}

	static xalanc::XercesDOMSupport& theDOMSupport()
	{
		init();
		static xalanc::XercesDOMSupport support(*xercesc::XMLPlatformUtils::fgMemoryManager);
		return support;
	}

	static void init()
	{
		static XMLInit doInit;
	}
};

#endif // __XML_INIT_HPP
