#include "se/TransferOp.hpp"

// Static member initialisation.
std::vector<const EnumType *> TransferOp::values;

const TransferOp* TransferOp::QUERY()
{
	static TransferOp op("query");
	return &op;
}

const TransferOp* TransferOp::REQUEST()
{
	static TransferOp op("request");
	return &op;
}

const TransferOp* TransferOp::CANCEL()
{
	static TransferOp op("cancel");
	return &op;
}

const TransferOp* TransferOp::APPROVE()
{
	static TransferOp op("approve");
	return &op;
}

const TransferOp* TransferOp::REJECT()
{
	static TransferOp op("reject");
	return &op;
}


TransferOp::TransferOp (const std::string &op)
    : EnumType (values, op)
{ }

const TransferOp* TransferOp::value (const std::string &name)
{
    return (const TransferOp *)EnumType::value (name, values);
}

void TransferOp::init()
{
    QUERY();
    REQUEST();
    CANCEL();
    APPROVE();
    REJECT();
}
