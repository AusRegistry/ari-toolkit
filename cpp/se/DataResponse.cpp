#include "se/DataResponse.hpp"
#include "se/CommandType.hpp"

const std::string DataResponse::OBJ()
{
	static std::string expr = "OBJ";
	return expr;
}

const std::string DataResponse::RES_DATA_EXPR()
{
	static std::string expr = Response::RESPONSE_EXPR() + "/e:resData";
	return expr;
}

DataResponse::DataResponse (const CommandType* commandType, 
                            const ObjectType* objectType)
    : cmdType(commandType),
      objType(objectType)
{
}

const CommandType& DataResponse::getCmdType() const
{
    return *cmdType;
}

const ObjectType& DataResponse::getObjType() const
{
    return *objType;
}
