#ifndef _SECDNS_DOMAIN_CREATE_COMMAND_EXTENSION_HPP_
#define _SECDNS_DOMAIN_CREATE_COMMAND_EXTENSION_HPP_

#include "se/CommandExtension.hpp"
#include "SecDNSDSOrKeyType.hpp"

/**
 * This class models the <create> elements as defined in the
 * AusRegistry secDNS-1.1 EPP command extension.
 */

class DomainSecDNSCreateCommandExtension : public CommandExtension
{
	public:
		virtual void addToCommand(const Command &command) const;

	    /**
	     * Set the Create Data.
	     * The memory of createData pointer will be handled DomainSecDNSCreateCommandExtension,
	     * memory will be auto freed when this extension instance is out of scope
	     *
	     * @param createData
	     *            The DSData or KeyData in the command xml
	     *
	     */
		void setCreateData(SecDNSDSOrKeyType* createData);
	private:
		std::auto_ptr<SecDNSDSOrKeyType> createData;
};

inline void DomainSecDNSCreateCommandExtension::setCreateData(SecDNSDSOrKeyType* createData)
{
	this->createData.reset(createData);
}

#endif /* _SECDNS_DOMAIN_CREATE_COMMAND_EXTENSION_HPP_ */
