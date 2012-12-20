#include "se/EnumDomainInfoResponse.hpp"

const std::string EnumDomainInfoResponse::E164_INF_DATA_EXPR
    ("/e:epp/e:response/e:extension/e164epp:infData");

const std::string EnumDomainInfoResponse::NAPTR_COUNT_EXPR
    ("count(" + 
     EnumDomainInfoResponse::E164_INF_DATA_EXPR + 
     "/e164epp:naptr)");

const std::string EnumDomainInfoResponse::NAPTR_IND_EXPR
    (EnumDomainInfoResponse::exprReplace
        (EnumDomainInfoResponse::E164_INF_DATA_EXPR) +
     "/e164epp:naptr[IDX]");

const std::string EnumDomainInfoResponse::NAPTR_ORDER_EXPR
    ("/e164epp:order/text()");

const std::string EnumDomainInfoResponse::NAPTR_PREF_EXPR
    ("/e164epp:pref/text()");

const std::string EnumDomainInfoResponse::NAPTR_FLAGS_EXPR
    ("/e164epp:flags/text()");

const std::string EnumDomainInfoResponse::NAPTR_SVC_EXPR
    ("/e164epp:svc/text()");

const std::string EnumDomainInfoResponse::NAPTR_REGEX_EXPR
    ("/e164epp:regex/text()");

const std::string EnumDomainInfoResponse::NAPTR_REPL_EXPR
    ("/e164epp:repl/text()");


EnumDomainInfoResponse::EnumDomainInfoResponse()
    : DomainInfoResponse()
{
}

void EnumDomainInfoResponse::fromXML (XMLDocument *xmlDoc) throw (ParsingException)
{
    DomainInfoResponse::fromXML (xmlDoc);
    
    if (!(resultArray[0].succeeded())) {
        return;
    }

    try
    {
        int naptrCount = xmlDoc->getNodeCount (NAPTR_COUNT_EXPR);
        naptrs.clear();
        
        for (int i = 0; i < naptrCount; i++)
        {
            std::string qry = ReceiveSE::replaceIndex(NAPTR_IND_EXPR, i+1);

            std::string order = xmlDoc->getNodeValue (qry + NAPTR_ORDER_EXPR);
            std::string pref = xmlDoc->getNodeValue (qry + NAPTR_PREF_EXPR);
            std::string flags = xmlDoc->getNodeValue (qry + NAPTR_FLAGS_EXPR);
            std::string svc = xmlDoc->getNodeValue (qry + NAPTR_SVC_EXPR);
            std::string regex = xmlDoc->getNodeValue (qry + NAPTR_REGEX_EXPR);
            std::string repl = xmlDoc->getNodeValue (qry + NAPTR_REPL_EXPR);
            
            naptrs.push_back (NAPTR (atoi(order.c_str()),
                                     atoi(pref.c_str()),
                                     flags.c_str(),
                                     svc,
                                     regex,
                                     repl));
        }
    }
    catch (XPathExpressionException& e)
	{
		maintLogger->warning(e.getMessage());
		ParsingException pe;
		pe.causedBy(e);
		throw pe;
	}
}

std::string EnumDomainInfoResponse::exprReplace (const std::string &expr)
{
    return StringUtils::replaceAll (expr,
                                    DataResponse::OBJ(),
                                    "e164epp");
}
