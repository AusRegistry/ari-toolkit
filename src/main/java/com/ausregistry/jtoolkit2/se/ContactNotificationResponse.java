package com.ausregistry.jtoolkit2.se;

/**
 * Notification data specific to contact objects.  Refer to
 * {@link com.ausregistry.jtoolkit2.se.NotificationResponse} for further
 * details.
 */
public class ContactNotificationResponse extends NotificationResponse {
    private static final long serialVersionUID = 4945036855203797229L;
    private static final String CON_ID_EXPR = exprReplace(IDENT_EXPR);
    private static final String CON_RESULT_EXPR = exprReplace(RESULT_EXPR);
    private static final String CON_CLTRID_EXPR = exprReplace(CLTRID_EXPR);
    private static final String CON_SVTRID_EXPR = exprReplace(SVTRID_EXPR);
    private static final String CON_PADATE_EXPR = exprReplace(PADATE_EXPR);

    public ContactNotificationResponse() {
        super(StandardObjectType.CONTACT);
    }

    private static String exprReplace(String expr) {
        return expr.replaceAll(OBJ, StandardObjectType.CONTACT.getName()
                ).replaceAll(IDENT, StandardObjectType.CONTACT.getIdentType());
    }

    protected String identifierExpr() {
        return CON_ID_EXPR;
    }

    protected String resultExpr() {
        return CON_RESULT_EXPR;
    }

    protected String cltridExpr() {
        return CON_CLTRID_EXPR;
    }

    protected String svtridExpr() {
        return CON_SVTRID_EXPR;
    }

    protected String padateExpr() {
        return CON_PADATE_EXPR;
    }
}
