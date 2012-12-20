#ifndef __EPPWRITER_H
#define __EPPWRITER_H

#include <memory>

#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/util/XMLString.hpp>
#include <xercesc/dom/DOM.hpp>
#include <xercesc/util/OutOfMemoryException.hpp>

#ifndef SWIG
XERCES_CPP_NAMESPACE_USE
#endif /* SWIG */

#include "xml/XMLWriter.hpp"
#include "xml/ParsingException.hpp"

/**
 * An EPP-specific implementation of an XMLWriter.  Instances of this class
 * set the xml declaration and root element appropriately for EPP service
 * elements and also set the namespace attributes of the root element.  This
 * implementation uses a DocumentBuilder to create the XML document which
 * contains the EPP service element.
 */
class EPPWriter : public XMLWriter
{
public:
    /**
     * Creates an EPP service element lexical representation generator with
     * default xml declaration and root element attributes.
     */
    EPPWriter (const std::string& version = "1.0",
               const std::string& encoding = "UTF-8",
               bool  standalone = false,
               const std::string& eppNamespace = "urn:ietf:params:xml:ns:epp-1.0",
               const std::string& xsi = "http://www.w3.org/2001/XMLSchema-instance",
               const std::string& eppSchemaLocation = "urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd");

    /**
     * Get the <code>epp</code> element, which is the root of the XML tree
     * upon which the lexical representation will be based.
     */
    DOMElement* getRoot(void) const;

protected:
    virtual void setRoot (DOMElement *newRoot);
    virtual DOMElement* createElement (const std::string &uri, const std::string &name);
	virtual std::string version() { return _version; }
	virtual std::string encoding() { return _encoding; }


private:
    std::auto_ptr<DOMDocument> _doc;
    DOMElement *_eppElement;

	std::string _version;
	std::string _encoding;
};
#endif // __EPPWRITER_H
