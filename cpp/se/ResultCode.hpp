#ifndef __RESULTCODE_HPP
#define __RESULTCODE_HPP

/**
 * This class defines a set of constants mapped to the EPP result codes.  These
 * can be used to provide symbolic names for handling of result codes, rather
 * than magic numbers.
 */
class ResultCode
{
public:
    static const int SUCCESS = 1000;
    static const int SUCCESS_ACT_PEND = 1001;
    static const int SUCCESS_NO_MSG = 1300;
    static const int SUCCESS_ACK = 1301;
    static const int SUCCESS_LOGOUT = 1500;
    static const int CMD_UNKNOWN = 2000;
    static const int CMD_SYNTAX_ERR = 2001;
    static const int CMD_USE_ERR = 2002;
    static const int PARAM_MISSING = 2003;
    static const int PARAM_VAL_RANGE_ERR = 2004;
    static const int PARAM_VAL_SYNTAX_ERR = 2005;
    static const int UNIMPL_PROTO_VERS = 2100;
    static const int UNIMPL_CMD = 2101;
    static const int UNIMPL_OPT = 2102;
    static const int UNIMPL_EXT = 2103;
    static const int BILLING_FAILURE = 2104;
    static const int OBJ_NOT_ELIG_RENEW = 2105;
    static const int OBJ_NOT_ELIG_TXFR = 2106;
    static const int AUTHENT_ERR = 2200;
    static const int AUTHRZN_ERR = 2201;
    static const int INVALID_AUTH_INFO = 2202;
    static const int OBJ_PEND_TXFR = 2300;
    static const int OBJ_NOT_PEND_TXFR = 2301;
    static const int YX_OBJ = 2302;
    static const int NX_OBJ = 2303;
    static const int OBJ_STATUS_PROHIB_OP = 2304;
    static const int OBJ_ASSOC_PROHIB_OP = 2305;
    static const int PARAM_VAL_POL_ERR = 2306;
    static const int UNIMPL_OBJ_SVC = 2307;
    static const int DATA_MGMT_POL_VIOLATION = 2308;
    static const int CMD_FAILED = 2400;
    static const int CMD_FAILED_CLOSING = 2500;
    static const int AUTHENT_ERROR_CLOSING = 2501;
    static const int SESS_LIM_EXCEEDED_CLOSING = 2502;

};

#endif // __RESULTCODE_HPP
