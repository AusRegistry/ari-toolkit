#ifndef SECDNSDSORKEYTYPE_HPP_
#define SECDNSDSORKEYTYPE_HPP_

#include <vector>
#include <tr1/memory>

#include "xercesc/dom/DOMElement.hpp"
#include "xml/XMLWriter.hpp"

#include "SecDNSMaxSigLifeType.hpp"
#include "SecDNSKeyData.hpp"
#include "SecDNSDSData.hpp"

class SecDNSDSOrKeyType
{
	public:
		SecDNSDSOrKeyType() :
			maxSigLife(NULL),
			dsDataList(),
			keyDataList()
			{}
		virtual ~SecDNSDSOrKeyType() {};

		void setMaxSigLife(SecDNSMaxSigLifeType* maxSigLife);
		void addToDSData(SecDNSDSData* dsData);
		void addToKeyData(SecDNSKeyData* keyData);

		int getDSDataListSize();
		std::tr1::shared_ptr<const SecDNSDSData> getDSData(int index) const;
		std::tr1::shared_ptr<SecDNSDSData> getDSData(int index);

		int getKeyDataListSize();
		std::tr1::shared_ptr<const SecDNSKeyData> getKeyData(int index) const;
		std::tr1::shared_ptr<SecDNSKeyData> getKeyData(int index);

		void createXMLElement(XMLWriter* xmlWriter, DOMElement* addElement);

	private:
		std::auto_ptr<SecDNSMaxSigLifeType> maxSigLife;
		std::vector<std::tr1::shared_ptr<SecDNSDSData> > dsDataList;
		std::vector<std::tr1::shared_ptr<SecDNSKeyData> > keyDataList;
};

#endif /* SECDNSDSORKEYTYPE_HPP_ */
