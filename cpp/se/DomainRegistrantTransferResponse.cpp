#include "se/DomainRegistrantTransferResponse.hpp"
#include "se/RegistrantObjectType.hpp"
#include "se/RegistrantTransferCommandType.hpp"
#include "se/EPPDateFormatter.hpp"

namespace {
    const RegistrantTransferCommandType rtrnType;
    const RegistrantObjectType registrantType;
} // anonymous namespace

using namespace std;

const string DomainRegistrantTransferResponse::REGISTRANT_NAME_EXPR =
        "/e:epp/e:response/e:resData/registrant:rtrnData/registrant:name/text()";

const string DomainRegistrantTransferResponse::REGISTRANT_EX_DATE_EXPR =
        "/e:epp/e:response/e:resData/registrant:rtrnData/registrant:exDate/text()";

DomainRegistrantTransferResponse::DomainRegistrantTransferResponse()
    : DataResponse(&rtrnType, &registrantType)
{
}

void DomainRegistrantTransferResponse::fromXML(XMLDocument* xmlDoc) throw (ParsingException)
{
    DataResponse::fromXML(xmlDoc);

    if (!(resultArray[0].succeeded())) {
        return;
    }

    name = xmlDoc->getNodeValue(REGISTRANT_NAME_EXPR);
    std::string exDateStr = xmlDoc->getNodeValue(REGISTRANT_EX_DATE_EXPR);
    exDate = std::auto_ptr<XMLGregorianCalendar>(
            EPPDateFormatter::fromXSDateTime(exDateStr));
}

