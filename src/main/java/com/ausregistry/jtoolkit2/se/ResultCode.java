package com.ausregistry.jtoolkit2.se;

/**
 * This class defines a set of constants mapped to the EPP result codes.  These
 * can be used to provide symbolic names for handling of result codes, rather
 * than magic numbers.
 */
public final class ResultCode implements java.io.Serializable {

    public static final int SUCCESS = 1000;
    public static final int SUCCESS_ACT_PEND = 1001;
    public static final int SUCCESS_NO_MSG = 1300;
    public static final int SUCCESS_ACK = 1301;
    public static final int SUCCESS_LOGOUT = 1500;
    public static final int CMD_UNKNOWN = 2000;
    public static final int CMD_SYNTAX_ERR = 2001;
    public static final int CMD_USE_ERR = 2002;
    public static final int PARAM_MISSING = 2003;
    public static final int PARAM_VAL_RANGE_ERR = 2004;
    public static final int PARAM_VAL_SYNTAX_ERR = 2005;
    public static final int UNIMPL_PROTO_VERS = 2100;
    public static final int UNIMPL_CMD = 2101;
    public static final int UNIMPL_OPT = 2102;
    public static final int UNIMPL_EXT = 2103;
    public static final int BILLING_FAILURE = 2104;
    public static final int OBJ_NOT_ELIG_RENEW = 2105;
    public static final int OBJ_NOT_ELIG_TXFR = 2106;
    public static final int AUTHENT_ERR = 2200;
    public static final int AUTHRZN_ERR = 2201;
    public static final int INVALID_AUTH_INFO = 2202;
    public static final int OBJ_PEND_TXFR = 2300;
    public static final int OBJ_NOT_PEND_TXFR = 2301;
    public static final int YX_OBJ = 2302;
    public static final int NX_OBJ = 2303;
    public static final int OBJ_STATUS_PROHIB_OP = 2304;
    public static final int OBJ_ASSOC_PROHIB_OP = 2305;
    public static final int PARAM_VAL_POL_ERR = 2306;
    public static final int UNIMPL_OBJ_SVC = 2307;
    public static final int DATA_MGMT_POL_VIOLATION = 2308;
    public static final int CMD_FAILED = 2400;
    public static final int CMD_FAILED_CLOSING = 2500;
    public static final int AUTHENT_ERROR_CLOSING = 2501;
    public static final int SESS_LIM_EXCEEDED_CLOSING = 2502;

    private static final long serialVersionUID = -2548064806215120636L;

    private ResultCode() {
        // intentionally do nothing, make checkstyle happy
    }
}

