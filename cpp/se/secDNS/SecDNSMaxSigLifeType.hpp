#ifndef SECDNSMAXSIGLIFETYPE_HPP_
#define SECDNSMAXSIGLIFETYPE_HPP_

#include <list>
#include <tr1/memory>

#include "xercesc/dom/DOMElement.hpp"
#include "xml/XMLWriter.hpp"
#include "xml/XMLHelper.hpp"

class SecDNSMaxSigLifeType
{
	public:
		SecDNSMaxSigLifeType(int maxSigLife) :
			maxSigLife(maxSigLife)
			{}
		virtual ~SecDNSMaxSigLifeType() {};

		void createXMLElement(XMLWriter* xmlWriter, DOMElement* element);

	private:
		int maxSigLife;
};

inline void SecDNSMaxSigLifeType::createXMLElement(XMLWriter* xmlWriter, DOMElement* element)
{
    XMLHelper::setTextContent(xmlWriter->appendChild(element, "maxSigLife"), maxSigLife);
}

#endif /* SECDNSMAXSIGLIFETYPE_HPP_ */
