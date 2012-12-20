#include "se/PollOperation.hpp"

std::vector<const EnumType *> PollOperation::values;

const PollOperation* PollOperation::REQ()
{
	static const PollOperation po("req");
	return PollOperation::value("req");
}

const PollOperation* PollOperation::ACK()
{
	static const PollOperation po("ack");
	return PollOperation::value("ack");
}

PollOperation::PollOperation(const std::string &op)
    : EnumType(values, op)
{ }

const PollOperation* PollOperation::value (const std::string &name)
{
    return (const PollOperation *)EnumType::value (name, values);
}

void PollOperation::init()
{
    REQ();
    ACK();
}
