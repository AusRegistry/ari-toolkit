#ifndef __COMMAND_HPP
#define __COMMAND_HPP

#include "se/SendSE.hpp"
#include "se/Extension.hpp"
#include "se/CommandExtension.hpp"

#include <xercesc/dom/DOMElement.hpp>
#include <string>

class CommandType;
class XMLParser;

/**
 * Standard and extension EPP command service elements are modelled by
 * subclasses of the Command class.  All such classes provide the means to
 * serialize their data to XML format as a valid EPP command, as well as
 * constructors sufficiently flexible to create any valid EPP command of that
 * type, and a method to query the type of command represented by an instance
 * of the class.
 */
class Command : public SendSE
{
public:
    Command(const CommandType* commandType);

    const CommandType* getCommandType() const { return cmdType; }
    XMLWriter* getXmlWriter() const { return xmlWriter; }
    DOMElement* getExtensionElement() const { return extensionElement; }
    void appendExtension(const CommandExtension& extension);
protected:
    DOMElement *command;
    DOMElement *cmdElement;
    DOMElement *extensionElement;
    const CommandType *cmdType;

    /** Establish the <command> element and the command specific sub-element.
     * Subclasses of Command that need to establish, for example, a specialised
     * extension namespace for these elements should provide these
     * two elements as arguments.
     */
private:
    virtual std::string toXMLImpl();
};
    
#endif // __COMMAND_HPP

