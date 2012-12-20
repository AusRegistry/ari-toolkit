#include "se/StandardCommandType.hpp"

using namespace std;

vector<const EnumType *>& StandardCommandType::values()
{
	static vector<const EnumType *> v;
	return v;
}

const StandardCommandType* StandardCommandType::LOGIN()
{
	static const StandardCommandType expr("login");
	return &expr;
}

const StandardCommandType* StandardCommandType::LOGOUT()
{
	static const StandardCommandType expr("logout");
	return &expr;
}

const StandardCommandType* StandardCommandType::POLL()
{
	static const StandardCommandType expr("poll");
	return &expr;
}

const StandardCommandType* StandardCommandType::CHECK()
{
	static const StandardCommandType expr("check");
	return &expr;
}

const StandardCommandType* StandardCommandType::INFO()
{
	static const StandardCommandType expr("info");
	return &expr;
}

const StandardCommandType* StandardCommandType::CREATE()
{
	static const StandardCommandType expr("create");
	return &expr;
}

const StandardCommandType* StandardCommandType::DELETE()
{
	static const StandardCommandType expr("delete");
	return &expr;
}

const StandardCommandType* StandardCommandType::UPDATE()
{
	static const StandardCommandType expr("update");
	return &expr;
}

const StandardCommandType* StandardCommandType::TRANSFER()
{
	static const StandardCommandType expr("transfer");
	return &expr;
}

const StandardCommandType* StandardCommandType::RENEW()
{
	static const StandardCommandType expr("renew");
	return &expr;
}


StandardCommandType::StandardCommandType(const string &name)
    : EnumType (values(), name), 
      CommandType (getCommandName())
{
}

const StandardCommandType* StandardCommandType::value 
    (const string &name)
{
    return (const StandardCommandType *)EnumType::value (name, values());
}

void StandardCommandType::init()
{
	LOGIN();
	LOGOUT();
	POLL();
	CHECK();
	INFO();
	CREATE();
	DELETE();
	UPDATE();
	TRANSFER();
	RENEW();
}
