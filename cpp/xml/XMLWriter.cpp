#include "xml/XMLWriter.hpp"
#include "xml/XStr.hpp"
#include "xml/XMLParser.hpp"
#include "xml/XMLDocument.hpp"
#include "common/SystemProperties.hpp"
#include <xercesc/util/Janitor.hpp>
#include <xercesc/util/XMLString.hpp>
#include <xercesc/dom/DOMWriter.hpp>
#include <xercesc/dom/DOMDocument.hpp>
#include <xercesc/dom/DOMImplementation.hpp>
#include <xercesc/dom/DOMImplementationRegistry.hpp>
#include <xercesc/framework/MemBufFormatTarget.hpp>

using namespace std;

Logger* XMLWriter::debugLogger;
Logger* XMLWriter::userLogger;

namespace {

string xmlProlog(const string& version, const string& enc)
{
	return "<?xml version=\"" + version + "\" encoding=\"" + enc + "\"?>";
}

} // namespace


void XMLWriter::init()
{
	const string pname("com.ausregistry.cpptoolkit.xml");
	debugLogger = Logger::getLogger(pname + ".debug");
	userLogger = Logger::getLogger(pname + ".user");
}

XMLWriter::~XMLWriter()
{ }

DOMElement* XMLWriter::appendChild(DOMElement* parent,
                                   const string& name)
{
	ArrayJanitor<char> nsURI(XMLString::transcode(parent->getNamespaceURI()));
	return appendChild(parent, name, nsURI.get());
}


DOMElement* XMLWriter::appendChild (DOMElement* parent,
                                    const string& name,
                                    const string& uri)
{
	DOMElement* newElement = createElement(uri, name);
	parent->appendChild(newElement);
	return newElement;
}

DOMElement* XMLWriter::appendChild(DOMElement* parent,
                                   const string& name,
                                   const string& attrName,
                                   const string& attrValue)
{
	DOMElement* newElement = appendChild(parent, name);
	newElement->setAttribute(XStr(attrName).str(), XStr(attrValue).str());
	return newElement;
}

DOMElement* XMLWriter::appendChild(DOMElement* parent,
                                   const string& name,
                                   const string& value,
                                   const string& attrName,
                                   const string& attrValue)
{
	DOMElement* newElement = appendChild(parent, name, attrName, attrValue);
	newElement->setTextContent(XStr(value).str());
	return newElement;
}

DOMElement * XMLWriter::appendChild (DOMElement* parent,
                                     const string& name,
                                     const string& uri,
                                     const string& value,
                                     const string_list& attrNames,
                                     const string_list& attrValues)
{
    DOMElement * newElement = appendChild (parent, name, uri);
    
	for (string_list::const_iterator i = attrNames.begin();
	     i != attrNames.end(); ++i)
	{
		newElement->setAttribute(XStr(*i).str(), XStr(*i).str());
	}
    
    if (value != "")
	{
        newElement->setTextContent(XStr(value).str());
	}
    
    return newElement;
}

void XMLWriter::appendChildren(DOMElement* parent,
                               const string& name,
                               const string_list& values)
{
    for (string_list::const_iterator i = values.begin(); i != values.end(); ++i)
    {
        DOMElement* child = appendChild(parent, name);
        child->setTextContent(XStr(*i).str());
    }
}


string XMLWriter::toXML()
{
	using namespace xercesc;

	DOMImplementation* impl =
		DOMImplementationRegistry::getDOMImplementation(XStr("CORE").str());
	auto_ptr<DOMWriter> writer(
		static_cast<DOMImplementationLS *>(impl)->createDOMWriter());

	// Determine encoding stored inside the MemBufFormatTarget.
	writer->setEncoding(XStr("UTF-8").str());
	MemBufFormatTarget target;

	// Would like to do:
	// 
	// writer->writeNode(&target, *getDocument()->getDocumentElement());
	//
	// Xerces doco for DOMWriter says: "[..] Documents are written
	// including an XML declaration [...]. Writing a document node
	// serializes the entire document."  Does not seem to be the case for
	// 2.7.x, as XMLWriter::writerNode(...) refuses to treat DOMDocument as a
	// DOMNode, so we need to hand cruft the XML preamble in xmlProlog().
	/// @todo Figure out why DOMWriter refuses a DocumentElement as a
	/// serialisation root.
	writer->writeNode(&target, *getRoot());
	return xmlProlog(version(), encoding())
		+ string(reinterpret_cast<const char *>(target.getRawBuffer()), target.getLen());
}
