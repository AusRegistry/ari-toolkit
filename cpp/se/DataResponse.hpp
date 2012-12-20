#ifndef __DATARESPONSE_HPP
#define __DATARESPONSE_HPP

#include "se/Response.hpp"
#include "se/ObjectType.hpp"

class CommandType;

/**
 * The base class of all response classes which provide more information than
 * the standard Response class.  Such classes model EPP responses having a
 * resData element.  Subclasses must specify the command and object
 * types to which the response applies.
 */
class DataResponse : public Response
{
public:
    DataResponse (const CommandType* commandType, const ObjectType* objectType);

protected:
    static const std::string OBJ();
    static const std::string RES_DATA_EXPR();
 
    const CommandType &getCmdType() const;
    const ObjectType  &getObjType() const;

private:
    const CommandType *cmdType;
    const ObjectType  *objType;
};


#endif // __DATARESPONSE_HPP
