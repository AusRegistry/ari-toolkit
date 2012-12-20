#ifndef __INETADDRESS_HPP
#define __INETADDRESS_HPP

#include <xercesc/dom/DOMElement.hpp>
#include "se/IPVersion.hpp"
#include "se/Appendable.hpp"

class XMLWriter;

/**
 * Host Internet addresses are configured and viewed via instances of this
 * class.  InetAddress instances may be supplied to the HostCreateCommand and
 * HostUpdateCommand (indirectly via HostAddRem) constructors in order to
 * assign or remove Internet addresses to and from host objects.  They are also
 * used to view Internet address information retrieved from a HostInfoResponse
 * instance.
 */     
class InetAddress : public Appendable
{
public:
    /// @TODO SWIG/Perl workaround - figure out why SWIG wants an empty constructor.
    InetAddress() {}

    /**
     * Construct an InetAddress of the specified version (either IPv4 or IPv6)
     * using its textual representation.
     */     
    InetAddress(const std::string& textRep,
                const IPVersion* version = IPVersion::IPv4())
        : version(version), textRep(textRep)
	{ }

	virtual ~InetAddress(){};

	// static InetAddress getByName(const std::string& name);
    
    /**     
     * Get the Internet Protocol version of this address.
     *                  
     * @see IPVersion Enumerates possible return
     * values.
     */ 
    const std::string getVersion() const { return version->toString(); }

    /**
     * Get the textual representation of this Internet address.
     */
    const std::string& getTextRep() const { return textRep; }
    
	xercesc::DOMElement* appendToElement(
			XMLWriter* xmlWriter, xercesc::DOMElement* parent) const;
    
private:

    const IPVersion* version;
    std::string textRep;
};

#endif  // __INETADDRESS_HPP
