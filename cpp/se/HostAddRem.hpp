#ifndef __HOST_ADD_REM_HPP
#define __HOST_ADD_REM_HPP

#include "se/InetAddress.hpp"
#include "se/Status.hpp"

#include <xercesc/dom/DOMElement.hpp>
#include <vector>

class XMLWriter;

/**
 * Use this to specify attributes to add to or remove from a host object via a
 * host update EPP service element, implemented in HostUpdateCommand.  This
 * class implements writing the add and rem elements to a host update command.
 */
class HostAddRem
{
public:
    /**
     * Each of the parameters is optional, but at least one must be specified.
     */
    HostAddRem (const std::vector<InetAddress> &addresses,
                const std::vector<Status> &statuses);
    
    xercesc::DOMElement* appendToElement(
		XMLWriter *xmlWriter, xercesc::DOMElement *parent) const;
    
private:
    std::vector<InetAddress> addresses;
    std::vector<Status> statuses;
};

#endif // __HOST_ADD_REM_HPP
