#ifndef __XML_HELPER_HPP
#define __XML_HELPER_HPP

#include <xercesc/util/XMLString.hpp>
#include <xercesc/dom/DOMNode.hpp>
#include <xercesc/dom/DOMElement.hpp>
#include <string>
#include "xml/XStr.hpp"
#include <cstdio>

class XMLHelper
{
public:
	static void setTextContent(xercesc::DOMNode* node, const char *str)
	{
		if (node)
		{
			node->setTextContent(XStr(str).str());
		}
	}

	static void setTextContent(xercesc::DOMNode* node, const std::string& str)
	{
		setTextContent(node, str.c_str());
	}

	/// Set text as serialisation of integer.
	// @param val Integer to be serialised.
	static void setTextContent(xercesc::DOMNode* node, int val)
	{
		char buf[32];
		sprintf(buf, "%d", val);
		setTextContent(node, buf);
	}

	static void setAttribute (xercesc::DOMElement* node, 
					   const std::string& name,
					   const std::string& value)
	{
		node->setAttribute(XStr(name).str(), XStr(value).str());
	}
};

#endif // __XML_HELPER_HPP
