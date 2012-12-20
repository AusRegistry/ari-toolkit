#include "se/SendSE.hpp"

using namespace std;

Logger* SendSE::userLogger;

string& SendSE::eppns()
{
	static string expr = "urn:ietf:params:xml:ns:epp-1.0";
	return expr;
}

string& SendSE::xsi()
{
	static string expr = "http://www.w3.org/2001/XMLSchema-instance";
	return expr;
}

string& SendSE::schemaLocation()
{
	static string expr = "urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd";
	return expr;
}

const string SendSE::XML_VERSION()
{
	static const string ver = "1.0";
	return ver;
}

const string SendSE::XML_ENCODING()
{
	static const string enc = "UTF-8";
	return enc;
}

SendSE::SendSE(const string& xmlVersion,
               const string& xmlEncoding,
               bool xmlStandalone)
{
    xmlWriter = new EPPWriter(xmlVersion, xmlEncoding, xmlStandalone,
	                          eppns(), xsi(), schemaLocation());
}

SendSE::~SendSE()
{
	delete xmlWriter;
}

void SendSE::init ()
{
    userLogger = Logger::getLogger("com.ausregistry.cpptoolkit.se.user");
}
