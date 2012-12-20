#ifndef __RESPONSE_HPP
#define __RESPONSE_HPP

#include <vector>
#include <list>

#include "se/ReceiveSE.hpp"
#include "se/XMLGregorianCalendar.hpp"
#include "se/Result.hpp"
#include "xml/ParsingException.hpp"

class ResponseExtension;

/**     
 * Use this to retrieve the values of attributes common to all EPP response
 * service elements.  Unless there is a specific subclass dedicated to handling
 * a type of response, an instance of this class should be used to handle the
 * response.  The commands which result in a response that should be handled by
 * this class are LoginCommand, LogoutCommand, subclasses of DeleteCommand and
 * subclasses of UpdateCommand.
 *  
 * @see LoginCommand
 * @see LogoutCommand
 * @see DeleteCommand
 * @see UpdateCommand
 */
class Response : public ReceiveSE
{
public:
    Response();
    
    const std::vector<Result>& getResults() const { return resultArray; };
    const XMLGregorianCalendar* getMessageEnqueueDate() const { return qDate.get(); };
    
    const std::string& getCLTRID() const { return clTRID; };
    const std::string& getSVTRID() const { return svTRID; };
    const std::string& getMessage() const { return msg; };
    const std::string& getMessageLanguage() const { return msgLang; };
    int getMsgCount() const { return msgCount; };

    /// @returns The msgQ id attribute, or a zero length string if it was not
    /// present.
    std::string getMsgID() const { return msgID; };
    
    void registerExtension(ResponseExtension * const extension);

    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
    std::string toString() const;

protected:
    static const std::string RESPONSE_EXPR();
    static const std::string RESULT_COUNT_EXPR();
    static const std::string RESULT_EXPR();
    static const std::string RESULT_CODE_EXPR();
    static const std::string RESULT_MSG_EXPR();
    static const std::string RESULT_VALUE_EXPR();
    static const std::string RESULT_XVALUE_EXPR();
    static const std::string RESULT_REASON_EXPR();
    static const std::string MSGQ_COUNT_EXPR();
    static const std::string MSGQ_ID_EXPR();
    static const std::string MSGQ_QDATE_EXPR();
    static const std::string MSGQ_MSG_EXPR();
    static const std::string MSGQ_MSG_LANG_EXPR();
    static const std::string CLTRID_EXPR();
    static const std::string SVTRID_EXPR();

    std::vector<Result> resultArray;
    std::list<ResponseExtension*> extensions;

private:
    std::string clTRID, svTRID;
    int msgCount;
    std::string msgID;
    std::auto_ptr<XMLGregorianCalendar> qDate;
    std::string msg;
    std::string msgLang;
};


#endif // __RESPONSE_HPP
