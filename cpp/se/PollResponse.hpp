#ifndef __POLL_RESPONSE_HPP
#define __POLL_RESPONSE_HPP

#include "se/Response.hpp"
#include "se/ContactTransferResponse.hpp"
#include "se/DomainTransferResponse.hpp"
#include "se/ContactNotificationResponse.hpp"
#include "se/DomainNotificationResponse.hpp"
#include "se/ContactInfoResponse.hpp"
#include "se/DomainInfoResponse.hpp"
#include <xalanc/XalanDOM/XalanNode.hpp>
#include <xalanc/XalanDOM/XalanDOMString.hpp>
#include <xalanc/XalanDOM/XalanElement.hpp>
#include <memory>

#ifndef SWIG
XALAN_USING_XALAN(XalanElement)
#endif

/**
 * Use this to access poll response information, as provided in an EPP poll
 * response compliant with RFC 3730.  Such a service element is sent by an EPP
 * server in response to a poll service element.  If in response to a poll
 * request, the getResData, getContactTransferResponse or
 * getDomainTransferResponse methods may return object-specific information,
 * which is in addition to any message queue data potentially available via the
 * <code>getMessage</code> and related methods in the {@link Response} class.
 *
 * @see PollCommand
 * @see PollRequestCommand
 * @see PollAckCommand
 */
class PollResponse : public Response
{
public:
    PollResponse();
    ~PollResponse();
    
    const ContactTransferResponse* getContactTransferResponse()
        { return conTrnResponse; };
    
    const DomainTransferResponse* getDomainTransferResponse()
        { return domTrnResponse; };

    const ContactNotificationResponse* getContactNotificationResponse()
        { return conNtfnResponse; };
    
    const DomainNotificationResponse* getDomainNotificationResponse()
        { return domNtfnResponse; };

    const ContactInfoResponse* getContactInfoResponse()
        { return conInfoResponse; };
    
    const DomainInfoResponse* getDomainInfoResponse()
        { return domInfoResponse; };

    virtual void fromXML (XMLDocument *xmlDoc) throw (ParsingException);
            
private:
    PollResponse(const PollResponse&);
    PollResponse& operator=(const PollResponse&);

    static const std::string RES_DATA_EXPR;

    static const xalanc::XalanDOMString TRN_DATA();
    static const xalanc::XalanDOMString INF_DATA();
    static const xalanc::XalanDOMString PAN_DATA();
    
    // Non-owning pointer.
    const XalanElement* resData;
    
    // Owned data
    DomainTransferResponse* domTrnResponse;
    ContactTransferResponse* conTrnResponse;
    DomainNotificationResponse* domNtfnResponse;
    ContactNotificationResponse* conNtfnResponse;
    DomainInfoResponse* domInfoResponse;
    ContactInfoResponse* conInfoResponse;
};

#endif // __POLL_RESPONSE_HPP
