#include "se/Disclose.hpp"
#include "xml/XMLWriter.hpp"

namespace {
	inline bool bitSet(unsigned int set, int bit) { return set & (0x1 << bit); }
}
using namespace xercesc;
DOMElement * Disclose::appendToElement (XMLWriter *xmlWriter,
                                        DOMElement *parent) const
{
    DOMElement *disclose = 
        xmlWriter->appendChild (parent, "disclose",
                                "flag", allow);

    if (!setBits)  // none set
    {
        xmlWriter->appendChild (disclose, "voice");
        return disclose;
    }
    
    if (bitSet (setBits, bs_NameInt))
        xmlWriter->appendChild (disclose, "name", "type", "int");
    if (bitSet (setBits, bs_NameLoc))
        xmlWriter->appendChild (disclose, "name", "type", "loc");
    if (bitSet (setBits, bs_OrgInt))
        xmlWriter->appendChild (disclose, "org", "type", "int");
    if (bitSet (setBits, bs_OrgLoc))
        xmlWriter->appendChild (disclose, "org", "type", "loc");
    if (bitSet (setBits, bs_AddrInt))
        xmlWriter->appendChild (disclose, "addr", "type", "int");
    if (bitSet (setBits, bs_AddrLoc))
        xmlWriter->appendChild (disclose, "addr", "type", "loc");
    if (bitSet (setBits, bs_Voice))
        xmlWriter->appendChild (disclose, "voice");
    if (bitSet (setBits, bs_Fax))
        xmlWriter->appendChild (disclose, "fax");
    if (bitSet (setBits, bs_Email))
        xmlWriter->appendChild (disclose, "email");
    
    return disclose;
}
