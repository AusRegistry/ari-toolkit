#include "common/StringUtils.hpp"

#include "xml/XMLDocument.hpp"
#include "xml/XMLInit.hpp"
#include "xml/NamespaceResolver.hpp"

#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/util/XMLString.hpp>
#include <xercesc/dom/DOMNode.hpp>
#include <xercesc/dom/DOMNodeList.hpp>
#include <xercesc/dom/DOMDocument.hpp>
#include <xercesc/dom/DOMException.hpp>
#include <xercesc/dom/DOMXPathResult.hpp>

#include <xalanc/XPath/XPathEvaluator.hpp>
#include <xalanc/XPath/XObject.hpp>
#include <xalanc/XercesParserLiaison/XercesDOMSupport.hpp>
#include <xalanc/XPath/XPathParserException.hpp>

// #include <xalanc/PlatformSupport/PrefixResolver.hpp>
// Is this the right include when using a Xerces parse? Or does xalan use its
// own in some way, or whould be use 'PrefixResolver' raw and create
// a liason object per JR comments?

#include <xalanc/DOMSupport/XalanDocumentPrefixResolver.hpp>

#include <vector>
#include <string>

using namespace xercesc;
using namespace xalanc;
using namespace std;



XMLDocument::XMLDocument(DOMDocument *doc, NamespaceResolver *resolver)
	: _doc(doc)
{
	XMLInit::init();
	_root = _doc->getDocumentElement();

	_xerces_doc_wrapper = new XercesDocumentWrapper(*XMLPlatformUtils::fgMemoryManager, _doc);
	this->resolver = resolver;

    // Xalan wrapper
    _xalan_doc  = _xerces_doc_wrapper->getOwnerDocument();
    _xalan_root = static_cast<XalanNode *>(_xerces_doc_wrapper->getDocumentElement());
    
}

XMLDocument::~XMLDocument()
{
    
    delete _xerces_doc_wrapper;
	_doc->release();
}

const XalanNode* XMLDocument::getElement(const string &query) const throw (XPathExpressionException)
{
    XPathEvaluator theEvaluator;

	try
	{
		return theEvaluator.selectSingleNode(XMLInit::theDOMSupport(),
											 _xalan_root,
											 XalanDOMString(query.c_str()).c_str(),
											 *resolver);
	}
	catch (const XPathParserException& e)
	{
		CharVectorType msg;
		e.getMessage().transcode(msg);
		throw XPathExpressionException(msg.begin());
	}
	return theEvaluator.selectSingleNode(XMLInit::theDOMSupport(),
                                         _xalan_root,
                                         XalanDOMString(query.c_str()).c_str(),
                                         *resolver);
}

int XMLDocument::getNodeCount(const string& query) const
	throw (XPathExpressionException)
{
	XPathEvaluator theEvaluator;
	try
	{
		XObjectPtr theResult =
			theEvaluator.evaluate(XMLInit::theDOMSupport(),
								  _xalan_root,
								  XalanDOMString(query.c_str()).c_str(),
								  *resolver);
		if (theResult->getType() != XObject::eTypeNumber)
		{
			throw XPathExpressionException(
					"Result of XPath expression was not a number.");
		}
		return (int)theResult->num();
	}
	catch (const XPathParserException& e)
	{
		CharVectorType msg;
		e.getMessage().transcode(msg);
		throw XPathExpressionException(msg.begin());
	}
}

const NodeRefList XMLDocument::getElements(const string &query) const throw (XPathExpressionException)
{
    XPathEvaluator theEvaluator;
    NodeRefList theList;
    
	try
	{
		theEvaluator.selectNodeList(theList,
									XMLInit::theDOMSupport(),
									_xalan_root,
									XalanDOMString(query.c_str()).c_str(),
									*resolver);
		return theList;
	}
	catch (const XPathParserException& e)
	{
		CharVectorType msg;
		e.getMessage().transcode(msg);
		throw XPathExpressionException(msg.begin());
	}
}


string XMLDocument::getNodeValue(const string &query) const throw (XPathExpressionException)
{
	XPathEvaluator theEvaluator;

	try
	{
		XObjectPtr theResult =
			theEvaluator.evaluate(XMLInit::theDOMSupport(),
								  _xalan_root,
								  XalanDOMString(query.c_str()).c_str(),
								  *resolver);
		CharVectorType res;
		theResult->str().transcode(res);
		return res.begin();
	}
	catch(const XPathParserException& e)
	{
		CharVectorType msg;
		e.getMessage().transcode(msg);
		throw XPathExpressionException(msg.begin());
	}
}

string XMLDocument::getNodeName(const string &query, bool throwIfUnmatched) const
	throw (XPathExpressionException)
{
	const XalanNode *element = getElement(query);
	if (element == NULL)
	{
		if (throwIfUnmatched)
			throw XPathExpressionException(
				"Can not resolve node name because XPATH query '"
				+ query + "' does not match any nodes.");
		else
			return "";
	}
	CharVectorType res;
	element->getLocalName().transcode(res);
	return res.begin();
}

vector<string> XMLDocument::getChildNames(const string& query) const
	throw (XPathExpressionException)
{
    vector<string> names;
    NodeRefList nodes = getElements(query + "/*");
    
    for (NodeRefList::size_type i = 0; i < nodes.getLength(); ++i)
	{
        XalanDOMString str = nodes.item(i)->getLocalName();
        names.push_back(c_str(TranscodeToLocalCodePage(str)));
	}
    
    return names;
}    

vector<string> XMLDocument::getNodeValues(const string &query) const
	throw (XPathExpressionException)
{
    vector<string> values;
    NodeRefList nodes = getElements(query);

    for (NodeRefList::size_type i = 0; i < nodes.getLength(); ++i)
    {
        string q = query + "[" + StringUtils::makeString((int) i + 1) + "]/text()";
        string s = getNodeValue(q);

        values.push_back(s);
    }
    return values;
}
