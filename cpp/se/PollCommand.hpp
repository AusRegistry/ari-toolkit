#ifndef __POLL_COMMAND_HPP
#define __POLL_COMMAND_HPP

#include "xml/XStr.hpp"
#include "se/Command.hpp"
#include "se/StandardCommandType.hpp"
#include "se/PollOperation.hpp"
#include "common/ErrorPkg.hpp"

/**
 * Representation of the EPP poll command, as defined in RFC3730.  Subclasses
 * of this must internally specify the poll operation as either acknowledge
 * (ack) or request (req), without exposing the implementation of the poll
 * operation type to the user.
 */
class PollCommand : public Command
{
public:
    PollCommand(const PollOperation* op)
        : Command(StandardCommandType::POLL())
    {
		if (op == NULL)
		{
			throw ::IllegalArgException(
					ErrorPkg::getMessage("se.poll.op.missing"));
		}
        cmdElement->setAttribute(XStr("op").str(), XStr(op->toString()).str());
    }
};
        
#endif // __POLL_COMMAND_HPP
