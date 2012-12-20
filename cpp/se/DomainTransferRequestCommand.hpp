#ifndef __DOMAIN_TRANSFER_REQUEST_COMMAND_HPP
#define __DOMAIN_TRANSFER_REQUEST_COMMAND_HPP

#include "se/DomainTransferCommand.hpp"
#include "se/TransferOp.hpp"

/**
 * Use this to request the transfer of a domain object from another client.
 * The domain object MUST NOT be sponsored by the client attempting to request
 * the transfer.  Instances of this class generate RFC3730 and RFC3731
 * compliant domain transfer EPP command service elements via the toXML method
 * with the transfer operation set to "request".
 *
 * @see DomainTransferResponse
 */
class DomainTransferRequestCommand : public DomainTransferCommand
{
public:
    /**
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'request' transfer operation.
     *
     * @param name The name of the domain to request transfer of.
     *
     * @param pw The identified domain's password.
     */
    DomainTransferRequestCommand (const std::string &name,
                                  const std::string &pw)
        : DomainTransferCommand (TransferOp::REQUEST(), name, pw)
	{ }
    
    /**
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'request' transfer operation.
     *
     * @param name The name of the domain to request transfer of.
     *
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied roid.
     */
    DomainTransferRequestCommand (const std::string &name,
                                  const std::string &roid,
                                  const std::string &pw)
        : DomainTransferCommand (TransferOp::REQUEST(), name, roid, pw)
	{ }

    /**
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'request' transfer operation.
     *
     * @param name The name of the domain to request transfer of.
     *
     * @param period The period of time to extend the validity period of the
     * domain by upon approval of the transfer.
     *
     * @param pw The identified domain's password.
     */
    DomainTransferRequestCommand (const std::string &name,
                                  const Period &period,
                                  const std::string &pw)
        : DomainTransferCommand (TransferOp::REQUEST(), name, period, pw)
	{ }

    /**
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'request' transfer operation.
     *
     * @param name The name of the domain to request transfer of.
     *
     * @param period The period of time to extend the validity period of the
     * domain by upon approval of the transfer.
     *
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied roid.
     */
    DomainTransferRequestCommand(const std::string &name,
                                 const Period &period,
                                 const std::string &roid,
                                 const std::string &pw)
        : DomainTransferCommand(TransferOp::REQUEST(), name, period, roid, pw)
	{ }
};    
    
#endif // __DOMAIN_TRANSFER_REQUEST_COMMAND_HPP
