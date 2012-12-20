#include "se/ContactNotificationResponse.hpp"
#include "se/StandardObjectType.hpp"
#include "common/StringUtils.hpp"

using namespace std;

ContactNotificationResponse::ContactNotificationResponse()
    : NotificationResponse(StandardObjectType::CONTACT())
{
}

string ContactNotificationResponse::exprReplace(const string& expr)
{
    return StringUtils::replaceAll(
            StringUtils::replaceAll(
                expr,
                DataResponse::OBJ(),
                StandardObjectType::CONTACT()->getName()),
            NotificationResponse::IDENT(),
            StandardObjectType::CONTACT()->getIdentType());
}

const string& ContactNotificationResponse::CON_ID_EXPR()
{
    static const string expr = ContactNotificationResponse::exprReplace(
            NotificationResponse::IDENT_EXPR());
    return expr;
}

const string& ContactNotificationResponse::CON_RESULT_EXPR()
{
    static const string expr = ContactNotificationResponse::exprReplace(
            NotificationResponse::RESULT_EXPR());
    return expr;
}

const string& ContactNotificationResponse::CON_CLTRID_EXPR()
{
    static const string expr = ContactNotificationResponse::exprReplace(
            NotificationResponse::CLTRID_EXPR());
    return expr;
}

const string& ContactNotificationResponse::CON_SVTRID_EXPR()
{
    static const string expr = ContactNotificationResponse::exprReplace(
            NotificationResponse::SVTRID_EXPR());
    return expr;
}

const string& ContactNotificationResponse::CON_PADATE_EXPR()
{
    static const string expr = ContactNotificationResponse::exprReplace(
            NotificationResponse::PADATE_EXPR());
    return expr;
}

