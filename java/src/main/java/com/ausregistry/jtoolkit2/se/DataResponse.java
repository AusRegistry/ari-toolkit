package com.ausregistry.jtoolkit2.se;

/**
 * The base class of all response classes which provide more information than
 * the standard Response class.  Such classes model EPP responses having a
 * {@code resData} element.  Subclasses must specify the command and object
 * types to which the response applies.
 */
public abstract class DataResponse extends Response {
    protected static final String OBJ = "OBJ";
    protected static final String RES_DATA_EXPR = RESPONSE_EXPR + "/e:resData";

    private CommandType cmdType;
    private ObjectType objType;

    protected DataResponse(CommandType commandType, ObjectType objectType) {
        cmdType = commandType;
        objType = objectType;
    }

    protected CommandType getCmdType() {
        return cmdType;
    }

    protected ObjectType getObjType() {
        return objType;
    }
}

