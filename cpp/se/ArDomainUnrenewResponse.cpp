#include "se/ArDomainUnrenewResponse.hpp"
#include "se/ArDomainObjectType.hpp"
#include "se/ArUnrenewCommandType.hpp"
#include "se/EPPDateFormatter.hpp"

namespace {
    const ArUnrenewCommandType urenType;
    const ArDomainObjectType ardomType;
} // anonymous namespace

using namespace std;

const string ArDomainUnrenewResponse::ARDOM_NAME_EXPR =
        "/e:epp/e:response/e:resData/ardom:urenData/ardom:name/text()";

const string ArDomainUnrenewResponse::ARDOM_EX_DATE_EXPR =
        "/e:epp/e:response/e:resData/ardom:urenData/ardom:exDate/text()";

ArDomainUnrenewResponse::ArDomainUnrenewResponse()
    : DataResponse(&urenType, &ardomType)
{
}

void ArDomainUnrenewResponse::fromXML(XMLDocument* xmlDoc) throw (ParsingException)
{
    DataResponse::fromXML(xmlDoc);

    if (!(resultArray[0].succeeded())) {
        return;
    }

    name = xmlDoc->getNodeValue(ARDOM_NAME_EXPR);
    std::string exDateStr = xmlDoc->getNodeValue(ARDOM_EX_DATE_EXPR);
    exDate = std::auto_ptr<XMLGregorianCalendar>(
            EPPDateFormatter::fromXSDateTime(exDateStr));
}

