#ifndef SECDNSCHGTYPE_HPP_
#define SECDNSCHGTYPE_HPP_

#include <list>
#include <tr1/memory>

#include "xercesc/dom/DOMElement.hpp"
#include "xml/XMLWriter.hpp"

#include "SecDNSMaxSigLifeType.hpp"

class SecDNSChgType
{
	public:
		SecDNSChgType() :
			maxSigLife(NULL)
			{}
		virtual ~SecDNSChgType() {};

		void setMaxSigLife(SecDNSMaxSigLifeType* maxSigLife);
		void createXMLElement(XMLWriter* xmlWriter, DOMElement* chgElement);

	private:
		std::auto_ptr<SecDNSMaxSigLifeType> maxSigLife;
};

inline void SecDNSChgType::setMaxSigLife(SecDNSMaxSigLifeType* maxSigLife)
{
	this->maxSigLife.reset(maxSigLife);
}

#endif /* SECDNSCHGTYPE_HPP_ */
