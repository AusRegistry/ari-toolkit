#ifndef CONTACT_NOTIFICATION_RESPONSE
#define CONTACT_NOTIFICATION_RESPONSE

#include "se/NotificationResponse.hpp"

using namespace std;

/**
 * Notification data specific to contact objects.  Refer to
 * {@link NotificationResponse} for further details.
 */
class ContactNotificationResponse : public NotificationResponse
{
    public:
        ContactNotificationResponse();

    protected:
        const string& identifierExpr() const { return CON_ID_EXPR(); };
        const string& resultExpr() const { return CON_RESULT_EXPR(); };
        const string& cltridExpr() const { return CON_CLTRID_EXPR(); };
        const string& svtridExpr() const { return CON_SVTRID_EXPR(); };
        const string& padateExpr() const { return CON_PADATE_EXPR(); };

        static string exprReplace(const string &expr);

    private:
        static const string& CON_ID_EXPR();
        static const string& CON_RESULT_EXPR();
        static const string& CON_CLTRID_EXPR();
        static const string& CON_SVTRID_EXPR();
        static const string& CON_PADATE_EXPR();
};

#endif /* CONTACT_NOTIFICATION_RESPONSE */

