#include "se/Command.hpp"
#include "SecDNSExtension.hpp"
#include "xml/XMLHelper.hpp"
#include "DomainSecDNSCreateCommandExtension.hpp"

namespace {
	SecDNSExtension& secDNSExtension() {
		static SecDNSExtension* secDNSExtension = new SecDNSExtension();
		return *secDNSExtension;
	}
}; // anonymous namespace

void DomainSecDNSCreateCommandExtension::addToCommand(const Command &command) const
{
	XMLWriter* xmlWriter = command.getXmlWriter();
	DOMElement* extensionElement = command.getExtensionElement();
	DOMElement* createElement = xmlWriter->appendChild(extensionElement,
			"create", secDNSExtension().getURI());

	if (createData.get() != NULL)
	{
		createData->createXMLElement(xmlWriter, createElement);
	}
}
