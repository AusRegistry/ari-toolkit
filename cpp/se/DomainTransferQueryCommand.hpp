#ifndef __DOMAIN_TRANSFER_QUERY_COMMAND_HPP
#define __DOMAIN_TRANSFER_QUERY_COMMAND_HPP

#include "se/DomainTransferCommand.hpp"
#include "se/TransferOp.hpp"

/**
 * Use this to query the transfer state of a domain object.  Instances of this
 * class generate RFC3730 and RFC3731 compliant domain transfer EPP command
 * service elements via the toXML method with the transfer operation set to
 * "query".
 *
 * @see DomainTransferResponse
 */     
class DomainTransferQueryCommand : public DomainTransferCommand
{
public:
    /**
     * Create a domain transfer command for the idenfitied domain, specifying
     * the 'query' transfer operation.
     *
     * @param name The name of the domain to query the transfer state of.
     */     
    DomainTransferQueryCommand (const std::string &name)
        : DomainTransferCommand (TransferOp::QUERY(), name)
	{ }
    
    /**     
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'query' transfer operation.
     *
     * @param name The name of the domain to query the transfer state of.
     *      
     * @param pw The identified domain's password.
     */
    DomainTransferQueryCommand (const std::string &name, const std::string &pw)
        : DomainTransferCommand (TransferOp::QUERY(), name, pw)
	{ }

    /**
     * Create a domain transfer command for the idenfitied domain, specifying
     * the designated password and the 'query' transfer operation.
     *
     * @param name The name of the domain to query the transfer state of.
     *
     * @param roid The repository object identifier of the contact for which
     * the password is specified.  The identified contact must be a contact
     * associated with the domain object being transferred.
     *
     * @param pw The password of the contact identified by the supplied roid.
     */
    DomainTransferQueryCommand (const std::string &name,
                                const std::string &roid,
                                const std::string &pw)
        : DomainTransferCommand (TransferOp::QUERY(), name, roid, pw)
	{ }
};

#endif // __DOMAIN_TRANSFER_QUERY_COMMAND_HPP
