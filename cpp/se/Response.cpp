#include "se/Response.hpp"
#include "se/EPPDateFormatter.hpp"
#include "se/ResponseExtension.hpp"
#include "common/StringUtils.hpp"
#include "common/Constants.hpp"
#include "common/ErrorPkg.hpp"

const std::string Response::RESULT_COUNT_EXPR()
{
	return "count(" + Response::RESPONSE_EXPR() + "/e:result)";
}

const std::string Response::RESULT_EXPR()
{
	return Response::RESPONSE_EXPR() + "/e:result[IDX]";
}

const std::string Response::RESULT_CODE_EXPR()
{
	return "/@code";
}

const std::string Response::RESULT_MSG_EXPR()
{
	return "/e:msg";
}

const std::string Response::RESULT_VALUE_EXPR()
{
	return "/e:value";
}

const std::string Response::RESULT_XVALUE_EXPR()
{
	return "/e:extValue/e:value";
}

const std::string Response::RESULT_REASON_EXPR()
{
	return "/e:result";
}

const std::string Response::MSGQ_COUNT_EXPR()
{
	return RESPONSE_EXPR() + "/e:msgQ/@count";
}

const std::string Response::MSGQ_ID_EXPR()
{
	return RESPONSE_EXPR() + "/e:msgQ/@id";
}

const std::string Response::MSGQ_QDATE_EXPR()
{
	return RESPONSE_EXPR() + "/e:msgQ/e:qDate/text()";
}

const std::string Response::MSGQ_MSG_EXPR()
{
	return RESPONSE_EXPR() + "/e:msgQ/e:msg/text()";
}

const std::string Response::MSGQ_MSG_LANG_EXPR()
{
	return RESPONSE_EXPR() + "/e:msgQ/e:msg/@lang";
}

const std::string Response::CLTRID_EXPR()
{
	return RESPONSE_EXPR() + "/e:trID/e:clTRID/text()";
}

const std::string Response::SVTRID_EXPR()
{
	return RESPONSE_EXPR() + "/e:trID/e:svTRID/text()";
}


const std::string Response::RESPONSE_EXPR()
{
	static std::string expr = "/e:epp/e:response";
	return expr;
}


Response::Response()
	: msgCount(0), msgLang(Constants::DEFAULT_LANG)
{ }

void Response::registerExtension(ResponseExtension * const extension)
{
   extensions.push_back(extension);
}

void Response::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    debugLogger->LOG_FINEST("enter");
    try
    {
        int resultCount = xmlDoc->getNodeCount(RESULT_COUNT_EXPR());
        resultArray.clear();
        
        for (int i = 0; i < resultCount; i++)
        {
            std::string qry = ReceiveSE::replaceIndex(RESULT_EXPR(), i + 1);
            std::string code = xmlDoc->getNodeValue(qry + RESULT_CODE_EXPR());
            std::string msg = xmlDoc->getNodeValue(qry + RESULT_MSG_EXPR());
            const XalanNode *value = xmlDoc->getElement(qry + RESULT_VALUE_EXPR());
            std::string xvalue = xmlDoc->getNodeValue(qry + RESULT_XVALUE_EXPR());
            std::string reason = xmlDoc->getNodeValue(qry + RESULT_REASON_EXPR());

            resultArray.push_back(Result(atoi(code.c_str()), msg, value, xvalue, reason));
            debugLogger->LOG_FINEST(resultArray[i].toString());
        }
        
        std::string msgQcount = xmlDoc->getNodeValue(MSGQ_COUNT_EXPR());
        if (msgQcount != "")
            msgCount = atoi(msgQcount.c_str());
        
        std::string msgQid = xmlDoc->getNodeValue (MSGQ_ID_EXPR());

        if (msgQid != "")
            msgID = msgQid.c_str();
        
        std::string msgQqDate = xmlDoc->getNodeValue(MSGQ_QDATE_EXPR());
        if (msgQqDate != "")
            qDate = std::auto_ptr<XMLGregorianCalendar>(EPPDateFormatter::fromXSDateTime(msgQqDate));
        
        msg = xmlDoc->getNodeValue(MSGQ_MSG_EXPR());
        msgLang = xmlDoc->getNodeValue (MSGQ_MSG_LANG_EXPR());
        
        clTRID = xmlDoc->getNodeValue(CLTRID_EXPR());
        svTRID = xmlDoc->getNodeValue(SVTRID_EXPR());

        std::list<ResponseExtension*>::const_iterator extensionsIterator;
        for (extensionsIterator = extensions.begin();
              extensionsIterator != extensions.end();
              extensionsIterator++)
        {
           (*extensionsIterator)->fromXML(xmlDoc);
        }
    }
    catch (XPathExpressionException &e)
    {
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
    }
    
    debugLogger->LOG_FINEST("exit");
}
// End Response::fromXML()


std::string Response::toString (void) const
{
    std::string retval = "(msgcount = ";
	
	retval += StringUtils::makeString(getMsgCount()) + ")"
		+ (getMsgID().size() > 0 ? "(msgID = " +getMsgID() + ")" : "" )
		+ (getMessageEnqueueDate() != NULL ? 
			"(qDate = " + EPPDateFormatter::toXSDateTime(*getMessageEnqueueDate()) + ")"
			: "") + 
        "(clTRID = " + getCLTRID() + ")" +
        "(svTRID = " + getSVTRID() + ")";
    
    std::vector<Result>::const_iterator p;
    
    for (p = resultArray.begin(); p != resultArray.end(); p++)
        retval += "\n" + p->toString();
    
    return retval;
}
