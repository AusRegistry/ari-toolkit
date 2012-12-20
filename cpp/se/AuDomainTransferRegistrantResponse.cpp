#include "se/AuDomainTransferRegistrantResponse.hpp"
#include "se/AuDomainObjectType.hpp"
#include "se/RegistrantTransferCommandType.hpp"
#include "se/EPPDateFormatter.hpp"

namespace {
    const RegistrantTransferCommandType rtrnType;
    const AuDomainObjectType audomType;
} // anonymous namespace

using namespace std;

const string AuDomainTransferRegistrantResponse::AUDOM_NAME_EXPR =
        "/e:epp/e:response/e:resData/audom:rtrnData/audom:name/text()";

const string AuDomainTransferRegistrantResponse::AUDOM_EX_DATE_EXPR =
        "/e:epp/e:response/e:resData/audom:rtrnData/audom:exDate/text()";

AuDomainTransferRegistrantResponse::AuDomainTransferRegistrantResponse()
    : DataResponse(&rtrnType, &audomType)
{
}

void AuDomainTransferRegistrantResponse::fromXML(XMLDocument* xmlDoc) throw (ParsingException)
{
    DataResponse::fromXML(xmlDoc);

    if (!(resultArray[0].succeeded())) {
        return;
    }

    name = xmlDoc->getNodeValue(AUDOM_NAME_EXPR);
    std::string exDateStr = xmlDoc->getNodeValue(AUDOM_EX_DATE_EXPR);
    exDate = std::auto_ptr<XMLGregorianCalendar>(
            EPPDateFormatter::fromXSDateTime(exDateStr));
}

