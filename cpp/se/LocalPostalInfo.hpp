#ifndef __LOCALPOSTALINFO_HPP
#define __LOCALPOSTALINFO_HPP

#include "se/PostalInfo.hpp"
#include "se/PostalInfoType.hpp"

#include <string>
#include <vector>

/**
 * A character encoding-flexible subclass of PostalInfo which supports full
 * UTF-8 character encoding for all attribute values.
 */
class LocalPostalInfo : public PostalInfo
{
public:
    LocalPostalInfo (const std::string &name,
                     const std::string &city,
                     const std::string &countryCode)
        : PostalInfo (PostalInfoType::LOCAL(), 
                      name, 
                      city, 
                      countryCode) {};
    
    LocalPostalInfo (const std::string &name,
                     const std::string &org,
                     const std::vector<std::string> &street,
                     const std::string &city,
                     const std::string &stateProv,
                     const std::string &postcode,
                     const std::string &countryCode)
        : PostalInfo (PostalInfoType::LOCAL(),
                      name, org, street, city, stateProv,
                      postcode, countryCode) {};
};

#endif // __LOCALPOSTALINFO_HPP
