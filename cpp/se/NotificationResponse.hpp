#ifndef NOTIFICATION_RESPONSE
#define NOTIFICATION_RESPONSE

#include <string>

#include "se/DataResponse.hpp"

using namespace std;

/**
 * When offline processing of an action has been completed by the server
 * operator, a message is enqueued for the client who requested the action.
 * The NotificationResponse class models the pending action notification data
 * informing the client of the completion of offline processing.  A
 * NotificationResponse is always specific to a particular object mapping, and
 * so a subclass of this models the specific object data - such an object is
 * returned by methods in {@link PollResponse}.
 *
 * @see PollRequestCommand
 * @see PollResponse
 */
class NotificationResponse : public DataResponse
{
    public:
        /**
         * The identifier of the object which is the subject of this Pending Action
         * Notification Data.
         */
        string& getIdentifier() { return identifier; };

        /**
         * A positive boolean value indicates that the request has been approved
         * and completed.  A negative boolean value indicates that the request has
         * been denied and the requested action has not been taken.
         */
        bool getResult() { return result; };

        /**
         * The client transaction identifier identifier returned with the original
         * response to process the command.  The client transaction identifier is
         * optional and will only be returned if the client provided an identifier
         * with the original associated command.
         */
        string getPaClTrID() { return cltrid; };

        /**
         * The server transaction identifier identifier returned with the original
         * response to process the command.
         */
        string& getPaSvTrID() { return svtrid; };

        /**
         * The date and time describing when review of the requested action was
         * completed.
         */
        XMLGregorianCalendar* getPaDate() { return paDate.get(); };

        virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
        virtual string toString(void);

    protected:
        NotificationResponse(const ObjectType* objectType);

        static const string IDENT();

        static const string IDENT_EXPR();
        static const string RESULT_EXPR();
        static const string CLTRID_EXPR();
        static const string SVTRID_EXPR();
        static const string PADATE_EXPR();

        virtual const string& identifierExpr() const = 0;
        virtual const string& resultExpr() const = 0;
        virtual const string& cltridExpr() const = 0;
        virtual const string& svtridExpr() const = 0;
        virtual const string& padateExpr() const = 0;

    private:
        static const string PAN_DATA_EXPR();

        string identifier;
        bool result;
        string cltrid;
        string svtrid;
	string padateStr;
        auto_ptr<XMLGregorianCalendar> paDate;
};

#endif /* NOTIFICATION_RESPONSE */

