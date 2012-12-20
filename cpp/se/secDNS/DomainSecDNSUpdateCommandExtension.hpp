#ifndef SECDNSDOMAINUPDATECOMMANDEXTENSION_HPP_
#define SECDNSDOMAINUPDATECOMMANDEXTENSION_HPP_

#include "se/CommandExtension.hpp"
#include "SecDNSRemType.hpp"
#include "SecDNSDSOrKeyType.hpp"
#include "SecDNSChgType.hpp"

/**
 * This class models the <update> elements as defined in the
 * AusRegistry secDNS-1.1 EPP command extension.
 */

class DomainSecDNSUpdateCommandExtension : public CommandExtension
{
	public:
		DomainSecDNSUpdateCommandExtension() :
			urgent(false),
			remData(NULL),
			addData(NULL),
			chgData(NULL)
		{}

		virtual void addToCommand(const Command &command) const;

		void setUrgent(bool urgent);

	    /**
	     * Set the remData.
	     * The memory of remData pointer will be handled by DomainSecDNSUpdateCommandExtension,
	     * memory will be auto freed when the extension instance is out of scope
	     *
	     * @param remData
	     *            The remData in the command xml
	     *
	     */
		void setRemData(SecDNSRemType* remData);

	    /**
	     * Set the addData.
	     * The memory of addData pointer will be handled by DomainSecDNSUpdateCommandExtension,
	     * memory will be auto freed when the extension instance is out of scope
	     *
	     * @param addData
	     *            The addData in the command xml
	     *
	     */
		void setAddData(SecDNSDSOrKeyType* addData);

	    /**
	     * Set the chgData.
	     * The memory of chgData pointer will be handled by DomainSecDNSUpdateCommandExtension,
	     * memory will be auto freed when the extension instance is out of scope
	     *
	     * @param chgData
	     *            The chgData in the command xml
	     *
	     */
		void setChgData(SecDNSChgType* chgData);
	private:
		bool urgent;
		std::auto_ptr<SecDNSRemType> remData;
		std::auto_ptr<SecDNSDSOrKeyType> addData;
		std::auto_ptr<SecDNSChgType> chgData;
};

inline void DomainSecDNSUpdateCommandExtension::setUrgent(bool urgent)
{
	this->urgent = urgent;
}
inline void DomainSecDNSUpdateCommandExtension::setRemData(SecDNSRemType* remData)
{
	this->remData.reset(remData);
}
inline void DomainSecDNSUpdateCommandExtension::setAddData(SecDNSDSOrKeyType* addData)
{
	this->addData.reset(addData);
}
inline void DomainSecDNSUpdateCommandExtension::setChgData(SecDNSChgType* chgData)
{
	this->chgData.reset(chgData);
}

#endif /* SECDNSDOMAINUPDATECOMMANDEXTENSION_HPP_ */
