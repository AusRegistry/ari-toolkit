#include "se/AeDomainTransferRegistrantResponse.hpp"
#include "se/AeDomainObjectType.hpp"
#include "se/RegistrantTransferCommandType.hpp"
#include "se/EPPDateFormatter.hpp"

namespace {
    const RegistrantTransferCommandType rtrnType;
    const AeDomainObjectType aedomType;
} // anonymous namespace

using namespace std;

const string AeDomainTransferRegistrantResponse::AEDOM_NAME_EXPR =
        "/e:epp/e:response/e:resData/aedom:rtrnData/aedom:name/text()";

const string AeDomainTransferRegistrantResponse::AEDOM_EX_DATE_EXPR =
        "/e:epp/e:response/e:resData/aedom:rtrnData/aedom:exDate/text()";

AeDomainTransferRegistrantResponse::AeDomainTransferRegistrantResponse()
    : DataResponse(&rtrnType, &aedomType)
{
}

void AeDomainTransferRegistrantResponse::fromXML(XMLDocument* xmlDoc) throw (ParsingException)
{
    DataResponse::fromXML(xmlDoc);

    if (!(resultArray[0].succeeded())) {
        return;
    }

    name = xmlDoc->getNodeValue(AEDOM_NAME_EXPR);
    std::string exDateStr = xmlDoc->getNodeValue(AEDOM_EX_DATE_EXPR);
    exDate = std::auto_ptr<XMLGregorianCalendar>(
            EPPDateFormatter::fromXSDateTime(exDateStr));
}

