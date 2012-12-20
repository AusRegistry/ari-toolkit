#include "se/DomainNotificationResponse.hpp"
#include "se/StandardObjectType.hpp"
#include "common/StringUtils.hpp"

using namespace std;

DomainNotificationResponse::DomainNotificationResponse()
    : NotificationResponse(StandardObjectType::DOMAIN())
{
}

string DomainNotificationResponse::exprReplace(const string& expr)
{
    return StringUtils::replaceAll(
            StringUtils::replaceAll(
                expr,
                DataResponse::OBJ(),
                StandardObjectType::DOMAIN()->getName()),
            NotificationResponse::IDENT(),
            StandardObjectType::DOMAIN()->getIdentType());
}

const string& DomainNotificationResponse::DOM_NAME_EXPR()
{
    static const string expr = DomainNotificationResponse::exprReplace(
            NotificationResponse::IDENT_EXPR());
    return expr;
}

const string& DomainNotificationResponse::DOM_RESULT_EXPR()
{
    static const string expr = DomainNotificationResponse::exprReplace(
            NotificationResponse::RESULT_EXPR());
    return expr;
}

const string& DomainNotificationResponse::DOM_CLTRID_EXPR()
{
    static const string expr = DomainNotificationResponse::exprReplace(
            NotificationResponse::CLTRID_EXPR());
    return expr;
}

const string& DomainNotificationResponse::DOM_SVTRID_EXPR()
{
    static const string expr = DomainNotificationResponse::exprReplace(
            NotificationResponse::SVTRID_EXPR());
    return expr;
}

const string& DomainNotificationResponse::DOM_PADATE_EXPR()
{
    static const string expr = DomainNotificationResponse::exprReplace(
            NotificationResponse::PADATE_EXPR());
    return expr;
}

