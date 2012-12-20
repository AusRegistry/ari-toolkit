#ifndef __INTPOSTALINFO_HPP
#define __INTPOSTALINFO_HPP

#include "se/PostalInfo.hpp"
#include "se/PostalInfoType.hpp"

#include <string>
#include <vector>

/**
 * A restricted subclass of PostalInfo which supports only US ASCII character
 * encoding as attribute values.
 */
class IntPostalInfo : public PostalInfo
{
public:
    IntPostalInfo (const std::string &name,
                   const std::string &city,
                   const std::string &countryCode)
        : PostalInfo(PostalInfoType::INTERNATIONAL(), name, city, countryCode)
	{ }
    
    IntPostalInfo (const std::string &name,
                   const std::string &org,
                   const std::vector<std::string> &street,
                   const std::string &city,
                   const std::string &stateProv,
                   const std::string &postcode,
                   const std::string &countryCode)
        : PostalInfo (PostalInfoType::INTERNATIONAL(),
                      name, org, street, city, stateProv,
                      postcode, countryCode)
	{ }

	virtual ~IntPostalInfo() { }
};

#endif // __INTPOSTALINFO_HPP
