#ifndef __SENDSE_HPP
#define __SENDSE_HPP

#include "xml/EPPWriter.hpp"
#include "common/Logger.hpp"

#include <memory>
#include <string>

/**
 * The base class for EPP command service elements that provides
 * the basic XML serialisation protocol.
 */
class SendSE
{
public:
    SendSE (const std::string &xmlVersion = XML_VERSION(),
            const std::string &xmlEncoding = XML_ENCODING(),
            bool xmlStandalone = XML_STANDALONE);
    virtual ~SendSE();
    
    virtual std::string toXML()
    {
        if (xml.size() == 0) xml = toXMLImpl();
        return xml;
    }

    static void init();
protected:
    static Logger *userLogger;
    
    std::string xml;
    XMLWriter* xmlWriter;
    
    virtual std::string toXMLImpl() = 0;

    static const std::string XML_VERSION();
    static const std::string XML_ENCODING();
    static const bool XML_STANDALONE = false;

    static std::string& eppns();
    static std::string& xsi();
    static std::string& schemaLocation();
};

#endif // __SENDSE_HPP

