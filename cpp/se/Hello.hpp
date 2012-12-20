#ifndef __HELLO_HPP
#define __HELLO_HPP

#include "se/SendSE.hpp"

/**
 * Use this to request service information in the form of an EPP greeting
 * from an EPP server.  Instances of this class generate via the toXML method
 * hello service elements compliant with the specification of hello in RFC3730.
 */
class Hello : public SendSE
{
public:
    Hello()
    {
        xmlWriter->appendChild (xmlWriter->getRoot(), "hello");
    }
protected:
	std::string toXMLImpl()
	{
		return xmlWriter->toXML();
	}
};

#endif // __HELLO_HPP
