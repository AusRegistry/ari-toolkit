#ifndef __XMLDOCUMENT_HPP
#define __XMLDOCUMENT_HPP

#include "common/EPPException.hpp"

#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/util/XMLString.hpp>
#include <xercesc/dom/DOMNode.hpp>
#include <xercesc/dom/DOMNodeList.hpp>
#include <xercesc/dom/DOMDocument.hpp>

#include <xalanc/XalanDOM/XalanElement.hpp>
#include <xalanc/XalanDOM/XalanDocument.hpp>
#include <xalanc/XPath/NodeRefList.hpp>
#include <xalanc/XercesParserLiaison/XercesDocumentWrapper.hpp>
#include <xalanc/Include/PlatformDefinitions.hpp>

#include <vector>
#include <string>

#ifndef SWIG
XALAN_USING_XALAN(XercesDocumentWrapper)
XALAN_USING_XALAN(XalanDocument)
XALAN_USING_XALAN(XalanNode)
XALAN_USING_XALAN(NodeRefList)
#endif /* SWIG */


class XPathExpressionException : public EPPException
{
public:
	XPathExpressionException(const std::string& msg)
		: EPPException(msg) { }
	EPP_EXCEPTION(XPathExpressionException);
};

class NamespaceResolver;

/** 
 * The purpose of an XMLDocument is to provide a simple xpath evaluation
 * interface for extracting node values from an XML document.  Where the names
 * of elements are known in advance, non DOM-specific methods should be used in
 * order to reduce the dependency of client classes on the DOM interface.
 */ 
class XMLDocument
{
public:

    /**
     * Create an XMLDocument rooted at the given element.
     *  
     * @param root The root element of the XML document.
     * @param resolver XPath prefix resolver implementation.
     */
	XMLDocument(xercesc::DOMDocument* doc, NamespaceResolver* resolver);
	~XMLDocument(void);

    /**     
     * Get the number of nodes returned by the given xpath expression.
     *      
     * @param query The XPath expression to evaluate.
     *
     * @return The number of nodes.
     */
	int getNodeCount(const std::string& query) const throw (XPathExpressionException);
    /**
     * Get as a String the text content of a node identified by the given XPath
     * expression.
     *
     * @param query The XPath expression to evaluate.
     *
     * @return The text content of the identified node.
     */
	std::string getNodeValue(const std::string& query) const throw (XPathExpressionException);
    /**
     * Get as a String the name of the node identified by the given XPath
         * expression.
     *
     * @param query The XPath expression to evaluate.
	 * @param throwIfUnmatched If false, an empty string is returned.
     *
     * @return The name of the identified node.
     */
	std::string getNodeName(const std::string& query, bool throwIfUnmatched = true) const
		throw (XPathExpressionException);
#ifndef SWIG
	std::string getChildName(const std::string& query, bool throwIfUnmatched = true) const
		throw (XPathExpressionException);
#endif
	std::vector<std::string> getChildNames(const std::string& query) const throw (XPathExpressionException);
    /**
     * Get the names of all the nodes which are children of the node identified
     * by the given XPath expression.
     *
     * @param query The XPath expression to evaluate.
     *
     * @return The names of all the children nodes.
     */
	std::vector<std::string> getNodeValues(const std::string& query) const throw (XPathExpressionException);

    /**
     * Get the node set identified by the given XPath expression.
     *
     * @param query The XPath expression to evaluate.
     *
	 * @return The set of nodes identified by the given expression.  Ownership
	 *    remains with this XMLDocument instance.
     *
     */
	const xalanc::XalanNode* getElement(const std::string& query) const throw (XPathExpressionException);
	const xalanc::NodeRefList getElements(const std::string& query) const throw (XPathExpressionException);

private:
	xercesc::DOMDocument* _doc;
	xercesc::DOMElement* _root;
	xalanc::XercesDocumentWrapper* _xerces_doc_wrapper;
	xalanc::XalanDocument* _xalan_doc;
    XalanNode* _xalan_root;
	NamespaceResolver* resolver;

	xalanc::XalanElement* getElement(const std::string& query, xalanc::XalanNode* qRoot);
	std::vector<std::string> getChildNames(const std::string& query,
		xalanc::XalanNode* qRoot);
};

#endif //__XMLDOCUMENT_HPP
