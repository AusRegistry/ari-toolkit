#ifndef __AU_DOMAIN_TRANSFER_REGISTRANT_RESPONSE
#define __AU_DOMAIN_TRANSFER_REGISTRANT_RESPONSE

#include "common/Deprecated.hpp"
#include "se/DataResponse.hpp"
#include "se/XMLGregorianCalendar.hpp"

/**
 * Use this to access create data for a domain as provided in an EPP domain
 * create response compliant with RFCs 3730 and 3731.  Such a service element
 * is sent by a compliant EPP server in response to a valid domain create
 * command, implemented by the DomainCreateCommand.
 *
 * @see DomainCreateCommand

 * @deprecated Performing a registrant transfer with AU eligibility extensions
 *             should now be managed through the use of the
 *             @c DomainRegistrantTransferCommand and
 *             @c DomainRegistrantTransferResponse
 *
 *             See
 *             {@link DomainRegistrantTransferCommand}
 *             and
 *             {@link DomainRegistrantTransferResponse}.
 */
class AuDomainTransferRegistrantResponse : public DataResponse
{
public:
      DEPRECATED(AuDomainTransferRegistrantResponse());

	const std::string& getName() const { return name; }
	const XMLGregorianCalendar* getExpiryDate() const { return exDate.get(); }
	void fromXML(XMLDocument* xmlDoc) throw (ParsingException);

private:
	static const std::string AUDOM_NAME_EXPR;
	static const std::string AUDOM_EX_DATE_EXPR;

	std::string name;
	std::auto_ptr<XMLGregorianCalendar> exDate;
};

#endif // __AU_DOMAIN_TRANSFER_REGISTRANT_RESPONSE
