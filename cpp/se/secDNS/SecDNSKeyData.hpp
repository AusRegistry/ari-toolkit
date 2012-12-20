#ifndef SECDNSKEYDATA_HPP_
#define SECDNSKEYDATA_HPP_

#include <string>

#include "xercesc/dom/DOMElement.hpp"
#include "xml/XMLWriter.hpp"


class SecDNSKeyData
{
	public:
		SecDNSKeyData() :
			flags(0),
			protocol(0),
			alg(0),
			pubKey("")
		{}

		SecDNSKeyData(int flags, int protocal, int alg, const std::string &pubKey)
		{
			setFlags(flags);
			setProtocol(protocal);
			setAlg(alg);
			setPubKey(pubKey);
		}

		virtual ~SecDNSKeyData() {}

		int getFlags() const;
		int getProtocol() const;
		int getAlg() const;
		std::string getPubKey() const;

		void setFlags(int flags);
		void setProtocol(int protocol);
		void setAlg(int alg);
		void setPubKey(const std::string& pubKey);

		void appendKeyDataElement(XMLWriter* xmlWriter, DOMElement* addElement);
	private:
		int flags;
		int protocol;
		int alg;
		std::string pubKey;
};

inline int SecDNSKeyData::getFlags() const
{
	return flags;
}
inline int SecDNSKeyData::getProtocol() const
{
	return protocol;
}
inline int SecDNSKeyData::getAlg() const
{
	return alg;
}
inline std::string SecDNSKeyData::getPubKey() const
{
	return pubKey;
}
inline void SecDNSKeyData::setFlags(int flags)
{
	this->flags = flags;
}
inline void SecDNSKeyData::setProtocol(int protocol)
{
	this->protocol = protocol;
}
inline void SecDNSKeyData::setAlg(int alg)
{
	this->alg = alg;
}
inline void SecDNSKeyData::setPubKey(const std::string& pubKey)
{
	this->pubKey = pubKey;
}
#endif /* SECDNSKEYDATA_HPP_ */
