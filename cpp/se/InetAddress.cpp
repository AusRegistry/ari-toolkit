#include "se/InetAddress.hpp"
#include "xml/XMLWriter.hpp"

xercesc::DOMElement* InetAddress::appendToElement(
		XMLWriter* xmlWriter, xercesc::DOMElement* parent) const
{
	return xmlWriter->appendChild(parent, "addr", textRep, "ip", getVersion());
}
