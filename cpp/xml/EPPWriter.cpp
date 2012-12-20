#include "EPPWriter.hpp"
#include "xml/XStr.hpp"
#include "xml/XMLInit.hpp"


EPPWriter::EPPWriter (const std::string& version,
                      const std::string& encoding,
                            bool         /* standalone */,
                      const std::string& eppNamespace,
                      const std::string& xsi,
                      const std::string& eppSchemaLocation)
	: _doc(NULL),
	  _version(version),
	  _encoding(encoding)
{
	// XMLInit::init();
    // xml = "";
    
    DOMImplementation *impl =
        DOMImplementationRegistry::getDOMImplementation(XStr("Core").str());
    
    if (impl)
    {
        try
        {
            _doc.reset(impl->createDocument());
			_doc->setStandalone(false);
			_doc->setEncoding(XStr(encoding).str());
			_doc->setVersion(XStr(version).str());
            _eppElement = _doc->createElementNS(
					XStr(eppNamespace).str(), XStr("epp").str());
            _eppElement->setAttribute(
					XStr("xmlns:xsi").str(), XStr(xsi).str());
            _eppElement->setAttribute(
					XStr("xsi:schemaLocation").str(), XStr(eppSchemaLocation).str());
            _doc->appendChild(_eppElement);
        }
        catch (const OutOfMemoryException&)
        {
			ParsingException pe("Out of memory exception creating EPPWriter.");
			throw pe;
        }
        catch (const DOMException& e)
        {
            XERCES_STD_QUALIFIER cerr << "DOMException code is:  " << e.code << XERCES_STD_QUALIFIER endl;
        }
        catch (...)
        {
            XERCES_STD_QUALIFIER cerr << "An error occurred creating the document" << XERCES_STD_QUALIFIER endl;
        }
    }
    // End if (impl)
}

DOMElement* EPPWriter::getRoot(void) const
{
    return _eppElement;
}

void EPPWriter::setRoot(DOMElement *newRoot)
{
    _eppElement = newRoot;
}

DOMElement* EPPWriter::createElement (const std::string &uri, 
									  const std::string &name)
{
    return _doc->createElementNS(XStr(uri.c_str()).str(), XStr(name.c_str()).str());
}
