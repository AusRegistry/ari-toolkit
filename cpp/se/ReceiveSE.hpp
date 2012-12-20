#ifndef __RECEIVESE_HPP
#define __RECEIVESE_HPP

#include "xml/XMLDocument.hpp"
#include "common/Logger.hpp"

#include <string>

/**
 * Handles basic processing of all EPP packets received by the client. Parsing
 * of the received XML document is the only responsibility of this class.
 */
class ReceiveSE
{
public:
    const std::string& toXML() const { return xml; };
	virtual ~ReceiveSE(){};

    /**
     * Set attribute values according to the given XML document.
     * @throws ParsingException
     */
    virtual void fromXML(XMLDocument *xmlDoc) = 0;

	static void init();
protected:
	static Logger* maintLogger;
    static Logger* supportLogger;
    static Logger* userLogger;
    static Logger* debugLogger;

    ReceiveSE() { };
    std::string xml;
    static std::string replaceIndex(const std::string &inputExpr, int index);
};

#endif // __RECEIVESE_HPP
