#ifndef __DISCLOSE_ITEM_HPP
#define __DISCLOSE_ITEM_HPP

#include <string>

/**
 * Disclosure preferences are viewed via an instances of this class.  This
 * class is an interface to the EPP disclose element which is described in
 * RFC3733, where uses of the element are also described.  Contact information
 * disclosure status may be found in the result data of a command info
 * response, implemented in the ContactInfoResponse class.
 *
 * @see ContactInfoCommand
 * @see ContactInfoResponse
 */
class DiscloseItem
{
public:
    /// @TODO SWIG/Perl workaround - figure out why SWIG wants an empty constructor.
    DiscloseItem () {}

    DiscloseItem (const std::string &elementName,
                  const std::string &type = "")
        : name(elementName), type(type)
	{ }
    
    const std::string& getElementName() { return name; };
    const std::string& getType() { return type; };

private:
    std::string name, type;
};
#endif // __DISCLOSE_ITEM_HPP
