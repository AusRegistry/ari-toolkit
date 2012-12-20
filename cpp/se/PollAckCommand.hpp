#ifndef __POLL_ACK_COMMAND_HPP
#define __POLL_ACK_COMMAND_HPP

#include "se/PollCommand.hpp"
#include "xml/XStr.hpp"

#include <sstream>

/**
 * Use this command to acknowledge receipt of a service message previously
 * retrieved via a poll request.  This dequeues the message and makes the next
 * message available for retrieval.  From RFC 3730:
 * "After a messages has been received by the client, the client MUST respond to
 * the message with an explicit acknowledgement to confirm that the message has
 * been received.  A server MUST dequeue the message and decrement the queue
 * counter after receiving acknowledgement from the client, making the next
 * message in the queue (if any) available for retrieval."
 */         
class PollAckCommand : public PollCommand
{
public:
    PollAckCommand (int msgID)
        : PollCommand (PollOperation::ACK())
    {
        std::ostringstream intval;
        intval << msgID;
        cmdElement->setAttribute(XStr("msgID").str(), XStr(intval.str()).str());
    };
};

#endif // __POLL_ACK_COMMAND_HPP
