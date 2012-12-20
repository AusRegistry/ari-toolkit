#ifndef DOMAIN_NOTIFICATION_RESPONSE
#define DOMAIN_NOTIFICATION_RESPONSE

#include "se/NotificationResponse.hpp"

using namespace std;

/**
 * Notification data specific to domain objects.  Refer to
 * {@link NotificationResponse} for further details.
 */
class DomainNotificationResponse : public NotificationResponse
{
    public:
        DomainNotificationResponse();

    protected:
        const string& identifierExpr() const { return DOM_NAME_EXPR(); };
        const string& resultExpr() const { return DOM_RESULT_EXPR(); };
        const string& cltridExpr() const { return DOM_CLTRID_EXPR(); };
        const string& svtridExpr() const { return DOM_SVTRID_EXPR(); };
        const string& padateExpr() const { return DOM_PADATE_EXPR(); };

        static string exprReplace(const string &expr);

    private:
        static const string& DOM_NAME_EXPR();
        static const string& DOM_RESULT_EXPR();
        static const string& DOM_CLTRID_EXPR();
        static const string& DOM_SVTRID_EXPR();
        static const string& DOM_PADATE_EXPR();
};

#endif /* DOMAIN_NOTIFICATION_RESPONSE */
