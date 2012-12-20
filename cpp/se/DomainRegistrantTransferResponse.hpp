#ifndef __DOMAIN_REGISTRANT_TRANSFER_RESPONSE
#define __DOMAIN_REGISTRANT_TRANSFER_RESPONSE

#include "se/DataResponse.hpp"
#include "se/XMLGregorianCalendar.hpp"

/**
 * Use this to access registrant transfer data for a domain as provided in an
 * EPP registrantTransfer response using the
 * \c urn:X-ar:params:xml:ns:registrant-1.0 namespace. Such a service
 * element is sent by a compliant EPP server in response to a valid domain
 * registrant transfer command, implemented by the
 * DomainRegistrantTransferCommand.
 *
 * @see DomainRegistrantTransferCommand
 */

class DomainRegistrantTransferResponse : public DataResponse
{
public:
    DomainRegistrantTransferResponse();

	const std::string& getName() const { return name; }
	const XMLGregorianCalendar* getExpiryDate() const { return exDate.get(); }
	void fromXML(XMLDocument* xmlDoc) throw (ParsingException);

private:
	static const std::string REGISTRANT_NAME_EXPR;
	static const std::string REGISTRANT_EX_DATE_EXPR;

	std::string name;
	std::auto_ptr<XMLGregorianCalendar> exDate;
};

#endif // __DOMAIN_REGISTRANT_TRANSFER_RESPONSE
