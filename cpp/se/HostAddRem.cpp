#include "se/HostAddRem.hpp"
#include "xml/XMLWriter.hpp"

using namespace std;

HostAddRem::HostAddRem (const vector<InetAddress>& addresses,
                        const vector<Status>& statuses)
    : addresses(addresses), statuses(statuses)
{ }

DOMElement* HostAddRem::appendToElement(XMLWriter *xmlWriter,
                                        DOMElement *parent) const
{
    vector<InetAddress>::const_iterator inaddr;
    
    for (inaddr = addresses.begin(); inaddr != addresses.end(); inaddr++)
        inaddr->appendToElement(xmlWriter, parent);
    
    vector<Status>::const_iterator status;
    
    for (status = statuses.begin(); status != statuses.end(); status++)
        xmlWriter->appendChild(
			parent, "status", status->getRationale(), "s", status->toString());
             
    return parent;
}
