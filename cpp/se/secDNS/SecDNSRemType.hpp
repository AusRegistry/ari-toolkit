#ifndef SECDNSREMTYPE_HPP_
#define SECDNSREMTYPE_HPP_

#include <list>
#include <tr1/memory>

#include "xercesc/dom/DOMElement.hpp"
#include "xml/XMLWriter.hpp"

#include "SecDNSKeyData.hpp"
#include "SecDNSDSData.hpp"

class SecDNSRemType
{
	public:
		SecDNSRemType() :
			removeAll(false),
			dsDataList(),
			keyDataList()
			{}
		virtual ~SecDNSRemType() {};

		void setRemoveAll(bool removeAll);
		void addToDSData(SecDNSDSData* dsData);
		void addToKeyData(SecDNSKeyData* keyData);

		void createXMLElement(XMLWriter* xmlWriter, DOMElement* remElement);

	private:
		bool removeAll;
		std::list<std::tr1::shared_ptr<SecDNSDSData> > dsDataList;
		std::list<std::tr1::shared_ptr<SecDNSKeyData> > keyDataList;
};

inline void SecDNSRemType::setRemoveAll(bool removeAll)
{
	this->removeAll = removeAll;
}
inline void SecDNSRemType::addToDSData(SecDNSDSData* dsData)
{
	std::tr1::shared_ptr<SecDNSDSData> dsDataPtr(dsData);
	dsDataList.push_back(dsDataPtr);
}
inline void SecDNSRemType::addToKeyData(SecDNSKeyData* keyData)
{
	std::tr1::shared_ptr<SecDNSKeyData> keyDataPtr(keyData);
	keyDataList.push_back(keyDataPtr);
}


#endif /* SECDNSREMTYPE_HPP_ */
