#include "xml/NamespaceResolver.hpp"
#include "xml/XMLParser.hpp"
#include "xml/XMLDocument.hpp"
#include "xml/XMLInit.hpp"
#include "xml/XStr.hpp"
#include "xml/ParsingException.hpp"
#include "common/SystemProperties.hpp"
#include "common/Logger.hpp"

#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/parsers/XercesDOMParser.hpp>
#include <xercesc/util/XMLString.hpp>
#include <xercesc/framework/MemBufInputSource.hpp>
#include <xercesc/dom/DOM.hpp>
#include <xercesc/sax/HandlerBase.hpp>
#include <xalanc/XalanTransformer/XalanTransformer.hpp>
#include <iostream>
#include <sstream>
#include <pthread.h>
#include <algorithm>

using namespace std;
using namespace xercesc;
using namespace xalanc;

NamespaceResolver* XMLParser::resolver = NULL;

namespace {
	class ErrorHandlerImpl : public ErrorHandler
	{
	public:
		ErrorHandlerImpl()
		{
			userLogger = Logger::getLogger("com.ausregistry.cpptoolkit.xml.user");
		}

		void warning(const SAXParseException& e)
		{
			char *message = XMLString::transcode(e.getMessage());
			userLogger->warning(string("Parser warning: ") + message);
			XMLString::release(&message);
		}
		void error(const SAXParseException& e)
		{
			char *message = XMLString::transcode(e.getMessage());
			const string msg(string("Error during parse: ") + message);
			XMLString::release(&message);
			userLogger->warning(msg);
			ParsingException pe(msg);
			throw pe;
		}
		void fatalError(const SAXParseException& e)
		{
			char *message=XMLString::transcode(e.getMessage());
			cerr << "ErrorHandlerImpl:: fatalError: " << message << endl;
			XMLString::release(&message);
		}
		void resetErrors()
		{ 
			userLogger->LOG_FINEST("ErrorHandlerImpl:: resetErrors called.");
		}
	private:
		Logger *userLogger;
	};
}


XMLParser::XMLParser(void) throw (EPPException)
	: parser(NULL)
{
	try
	{
		// Initialise parser.
		parser = new XercesDOMParser();
		parser->setDoNamespaces(true);

		if (SystemProperties::getBooleanProperty("xml.validation.enable", true))
		{
			parser->setValidationScheme(XercesDOMParser::Val_Always);
			parser->setDoValidation(true);

			// Prime XSD entities.
			ostringstream locs;
			typedef vector<pair<string,string> > pairs;
			const pairs schemas(SystemProperties::getProperties("xml.schema.location"));
			for (pairs::const_iterator i = schemas.begin(); i != schemas.end(); ++i)
			{
				locs << i->second << " ";
			}
			parser->setExternalSchemaLocation(XStr(locs.str()).str());

			parser->setDoSchema(true);
			parser->setValidationSchemaFullChecking(true);
			parser->setValidationConstraintFatal(true);
			parser->useCachedGrammarInParse(true);
			parser->cacheGrammarFromParse(true);
		}

		parser->setCalculateSrcOfs(false);
		parser->useScanner(XMLUni::fgSGXMLScanner);
		errHandler = new ErrorHandlerImpl;
		parser->setErrorHandler(errHandler);
	}
	catch (const XMLException& e)
	{
		char *message=XMLString::transcode(e.getMessage());
		EPPException exp(string("Error initialising XMLParser: ") + message);
		XMLString::release(&message);
		throw exp;
	}
}

XMLParser::~XMLParser(void)
{
	delete errHandler;
	delete parser;
}

void XMLParser::init()
{
	XMLParser::resolver = new NamespaceResolver;
}

XMLDocument* XMLParser::parse(const string& xml) throw (ParsingException)
{	
	XMLDocument *xmlDoc=NULL;
	try
	{
		MemBufInputSource input((const XMLByte *)xml.c_str(),xml.size(),"",false);
		parser->parse(input);

		// Pass ownership of the document to XMLDocument.
		xmlDoc = new XMLDocument(parser->adoptDocument(), XMLParser::resolver);
	}
	catch (const SAXParseException &e)
	{
		char *message=XMLString::transcode(e.getMessage());
		ParsingException pe(message);
		XMLString::release(&message);
		throw pe;
	}
	catch (const XMLException &e)
	{
		char *message=XMLString::transcode(e.getMessage());
		ParsingException pe(message);
		XMLString::release(&message);
		throw pe;
	}
	catch (const DOMException &e)
	{
		char* message = XMLString::transcode(e.msg);
		ParsingException pe(message);
		XMLString::release(&message);
		throw pe;
	}
	return xmlDoc;
}
