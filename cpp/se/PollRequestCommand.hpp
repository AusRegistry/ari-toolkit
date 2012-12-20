#ifndef __POLL_REQUEST_COMMAND_HPP
#define __POLL_REQUEST_COMMAND_HPP

#include "se/PollCommand.hpp"

/**
 * Use this command to poll the EPP server for the first message in the
 * client's message queue maintained by the server.  From RFC 3730:
 * "The EPP <poll> command is used to discover and retrieve service
 * messages queued by a server for individual clients.  If the message queue is
 * not empty, a successful resposne to a <poll> command MUST return the
 * first message from the message queue.  Each response returned from the
 * server includes a server-unique message identifier that MUST be provided to
 * acknowledge receipt of the message, and a counter that indicates the number
 * of messages in the queue.
 * </blockquote>
 */     
class PollRequestCommand : public PollCommand
{
public:
    PollRequestCommand ()
        : PollCommand (PollOperation::REQ()) { }
};
#endif // __POLL_REQUEST_COMMAND_HPP
