#ifndef __DISCLOSE_HPP
#define __DISCLOSE_HPP

#include "se/Appendable.hpp"
#include <string>

namespace {
	inline void setBit(unsigned int& set, int bit) { set |= (0x1 << bit); }
}

/**
 * Disclosure preferences are configured via an instance of this class.  This
 * class is an interface to the EPP disclose element which is described in
 * RFC3733 where uses of the element are also described.  Contact information
 * disclosure preferences may be set via contact transform operations,
 * implemented in such classes as ContactCreateCommand and
 * ContactUpdateCommand.
 *  
 * @see ContactCreateCommand
 * @see ContactUpdateCommand
 */ 
class Disclose : public Appendable
{
public:
    /**
     * Construct a Disclose object with all items not yet set.  This is an invalid
     * final state for an EPP disclose element, requiring at least one setX method
     * to be invoked on the instance prior to a transform command using the Disclose
     * object.
     *
     * @param allow Whether or not elements to be set later via setX should be
     * disclosed or not.  This is only a request to the server and may not be
     * honoured.
     */
    Disclose (bool allow, 
              bool nameInt = false, 
              bool nameLoc = false,
              bool orgInt  = false, 
              bool orgLoc  = false, 
              bool addrInt = false,
              bool addrLoc = false, 
              bool voice   = false, 
              bool fax     = false,
              bool email   = false)
    {
        this->allow = allow ? "1" : "0";
        setBits = 0L;
    };

	virtual ~Disclose(){};
    
    void setVoice()   { setBit (setBits, bs_Voice); };
    void setFax()     { setBit (setBits, bs_Fax); };
    void setEmail()   { setBit (setBits, bs_Email); };
    void setNameInt() { setBit (setBits, bs_NameInt); };
    void setNameLoc() { setBit (setBits, bs_NameLoc); };
    void setOrgInt()  { setBit (setBits, bs_OrgInt); };
    void setOrgLoc()  { setBit (setBits, bs_OrgLoc); };
    void setAddrInt() { setBit (setBits, bs_AddrInt); };
    void setAddrLoc() { setBit (setBits, bs_AddrLoc); };
    
	xercesc::DOMElement* appendToElement(
			XMLWriter *xmlWriter, 
			xercesc::DOMElement *parent) const;
private:

    typedef enum 
    {
      bs_Voice,
      bs_Fax,
      bs_Email,
      bs_NameInt,
      bs_NameLoc,
      bs_OrgInt,
      bs_OrgLoc,
      bs_AddrInt,
      bs_AddrLoc
    } bsBit;
    
    unsigned int setBits;
    std::string allow;
};
    
#endif // __DISCLOSE_HPP
