#include "se/Command.hpp"
#include "SecDNSExtension.hpp"
#include "xml/XMLHelper.hpp"
#include "DomainSecDNSUpdateCommandExtension.hpp"

namespace {
	SecDNSExtension& secDNSExtension() {
		static SecDNSExtension* secDNSExtension = new SecDNSExtension();
		return *secDNSExtension;
	}
}; // anonymous namespace

void DomainSecDNSUpdateCommandExtension::addToCommand(const Command &command) const
{
	XMLWriter* xmlWriter = command.getXmlWriter();
	DOMElement* extensionElement = command.getExtensionElement();
	DOMElement* updateElement = xmlWriter->appendChild(extensionElement,
			"update", secDNSExtension().getURI());

	if (urgent)
	{
		XMLHelper::setAttribute(updateElement, "urgent", "true");
	}

	if (remData.get() != NULL)
	{
		DOMElement* remElement = xmlWriter->appendChild(updateElement, "rem");
		remData->createXMLElement(xmlWriter, remElement);
	}
	if (addData.get() != NULL)
	{
		DOMElement* addElement = xmlWriter->appendChild(updateElement, "add");
		addData->createXMLElement(xmlWriter, addElement);
	}
	if (chgData.get() != NULL)
	{
		DOMElement* chgElement = xmlWriter->appendChild(updateElement, "chg");
		chgData->createXMLElement(xmlWriter, chgElement);
	}
}
