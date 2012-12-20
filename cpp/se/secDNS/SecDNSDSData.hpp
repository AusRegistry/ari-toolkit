#ifndef SECDNSDSDATA_HPP_
#define SECDNSDSDATA_HPP_

#include <string>

#include "xercesc/dom/DOMElement.hpp"
#include "xml/XMLWriter.hpp"

#include "SecDNSKeyData.hpp"

class SecDNSDSData
{
	public:
		SecDNSDSData() :
			keyTag(0),
			alg(0),
			digestType(0),
			digest("")
		{}

		SecDNSDSData(int keyTag, int alg, int digestType, const std::string &digest)
		{
			setKeyTag(keyTag);
			setAlg(alg);
			setDigestType(digestType);
			setDigest(digest);
		}

		virtual ~SecDNSDSData() {}

		int getKeyTag() const;
		int getAlg() const;
		int getDigestType() const;
		std::string getDigest() const;
		SecDNSKeyData* getKeyData() const;

		void setKeyTag(int key_tag);
		void setAlg(int alg);
		void setDigestType(int digestType);
		void setDigest(const std::string& digest);

		void setKeyData(SecDNSKeyData* keyData);

		void appendDSDataElement(XMLWriter* xmlWriter, DOMElement* addElement);
	private:
		int keyTag;
		int alg;
		int digestType;
		std::string digest;
		std::auto_ptr<SecDNSKeyData> keyData;
};

inline int SecDNSDSData::getKeyTag() const
{
	return keyTag;
}
inline int SecDNSDSData::getAlg() const
{
	return alg;
}
inline int SecDNSDSData::getDigestType() const
{
	return digestType;
}
inline std::string SecDNSDSData::getDigest() const
{
	return digest;
}
inline SecDNSKeyData* SecDNSDSData::getKeyData() const
{
	return keyData.get();
}

inline void SecDNSDSData::setKeyTag(int key_tag)
{
	this->keyTag = key_tag;
}
inline void SecDNSDSData::setAlg(int alg)
{
	this->alg = alg;
}
inline void SecDNSDSData::setDigestType(int digestType)
{
	this->digestType = digestType;
}
inline void SecDNSDSData::setDigest(const std::string& digest)
{
	this->digest = digest;
}
inline void SecDNSDSData::setKeyData(SecDNSKeyData* keyData)
{
	this->keyData.reset(keyData);
}

#endif /* SECDNSDSDATA_HPP_ */
