#ifndef __POSTALINFO_HPP
#define __POSTALINFO_HPP

#include "se/PostalInfoType.hpp"
#include "se/Appendable.hpp"

#include <xercesc/dom/DOMElement.hpp>

#include <string>
#include <vector>

class XMLWriter;

/** 
 * This class models postal information of contact objects.  Instances may be
 * used to either transform postal information or access attributes of postal
 * information obtained by querying a contact object via a contact info EPP
 * command, the response to which is implemented in the class
 * ContactInfoResponse.
 */     
class PostalInfo : public Appendable
{
public:
    virtual ~PostalInfo(){};

    xercesc::DOMElement* appendToElement(XMLWriter* xmlWriter,
		    xercesc::DOMElement* parent) const;
    
    const std::string& getCountryCode() const { return cc; };
    const std::string& getCity() const { return city; };
    const std::string& getName() const { return name; };
    const std::string& getOrganisation() const { return org; };
    const std::string& getPostcode() const { return pc; };
    const std::string& getSp() const { return sp; };
    const std::vector<std::string>& getStreet() const { return street; };
    const std::string& getType() const { return type; };
    
protected:
    /**
     * Minimal information required as per RFC3733 for creation of a contact.
     */
    PostalInfo (const PostalInfoType* type, 
                const std::string& name, 
                const std::string& city,
                const std::string& countryCode);
    
    /**
     * All fields defined in RFC3733 for postalInfoType.
     */
    PostalInfo (const PostalInfoType* type,
                const std::string& name,
                const std::string& org,
                const std::vector<std::string>& street,
                const std::string& city,
                const std::string& stateProv,
                const std::string& postcode,
                const std::string& countryCode);
private:
	std::string type;
    std::string name;
    std::string org;
    std::vector<std::string> street;
	std::string city;
    std::string sp;
    std::string pc;
    std::string cc;

    void Init (const std::string& type,
               const std::string& name,
               const std::string& org,
               const std::vector<std::string>& street,
               const std::string& city,
               const std::string& stateProv,
               const std::string& postcode,
               const std::string& countryCode);
};
    

#endif // __POSTALINFO_HPP
