#ifndef SECDNSDOMAININFORESPONSEEXTENSION_HPP_
#define SECDNSDOMAININFORESPONSEEXTENSION_HPP_

#include "se/ResponseExtension.hpp"
#include "SecDNSDSOrKeyType.hpp"

/**
 * This class models the <infData> elements as defined in the
 * AusRegistry secDNS-1.1 EPP command extension.
 */

class DomainSecDNSInfoResponseExtension : public ResponseExtension
{
	public:
		DomainSecDNSInfoResponseExtension() :
			initialised(false),
			infData(NULL)
		{}
		virtual void fromXML(XMLDocument *xmlDoc);
		virtual bool isInitialised() const;
		SecDNSDSOrKeyType* getInfData() const;
	private:
		int getResponseDSData(const XMLDocument* xmlDoc);
		int getResponseKeyData(const XMLDocument* xmlDoc);

		std::auto_ptr<SecDNSDSData> getDSData(const XMLDocument* xmlDoc, const std::string& dsDataXPath);
		std::auto_ptr<SecDNSKeyData> getKeyData(const XMLDocument* xmlDoc, const std::string& keyDataXPath);
		int getInt(const std::string& value);

		bool initialised;
		std::auto_ptr<SecDNSDSOrKeyType> infData;

		static const std::string DS_DATA_LIST_EXPR();
		static const std::string KEY_DATA_LIST_EXPR();
		static const std::string MAXSIGLIFE_EXPR();

};

inline bool DomainSecDNSInfoResponseExtension::isInitialised() const
{
   return initialised;
}

#endif /* SECDNSDOMAININFORESPONSEEXTENSION_HPP_ */
