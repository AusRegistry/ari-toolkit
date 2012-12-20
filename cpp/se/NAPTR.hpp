#ifndef __NAPTR_HPP
#define __NAPTR_HPP

#include <xercesc/dom/DOMElement.hpp>
#include "se/Appendable.hpp"
#include <string>

/**
 * This class models Naming Authority Pointer (NAPTR) resource records.
 * Naming Authority Pointer (NAPTR) resource records are associated with
 * ENUM domain names via the e164 extended create and update EPP commands.
 * Instances of this class are used to construct NAPTR records to assign
 * to ENUM domain objects, or to view attributes of NAPTR records already
 * assigned to ENUM domain objects provisioned in an EPP Registry.
 *  
 * @see EnumDomainCreateCommand Associate NAPTR
 * records with a new ENUM domain object, rather than delegating to
 * nameservers.
 *              
 * @see EnumDomainUpdateCommand Add or remove
 * NAPTR record associations to/from an ENUM domain object.
 *  
 * @see EnumDomainInfoResponse Report assocations
 * between a domain object and NAPTRs.
 */
class NAPTR : public Appendable
{
public:
    /// @TODO SWIG/Perl workaround - figure out why SWIG wants an empty constructor.
    NAPTR () {}

    NAPTR (int order,
           int preference, 
           const char *flags,
           const std::string &service,
           const std::string &regex = "",
           const std::string &replacement = "");
	virtual ~NAPTR(){};

    int getOrder() const { return order; };
    int getPreference() const { return preference; };
    const std::string &getFlags() const { return flags; };
    const std::string &getService() const { return svc; };
    const std::string &getRegex() const { return regex; };
    const std::string &getReplacement() const { return replacement; };
    
    virtual xercesc::DOMElement* appendToElement(
			XMLWriter *xmlWriter, xercesc::DOMElement *parent) const;
private:
    int order, preference;
    std::string flags, svc, regex, replacement;
};

#endif // __NAPTR_HPP
