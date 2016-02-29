package com.ausregistry.jtoolkit2.se;

/**
 * Notification data specific to domain objects.  Refer to
 * {@link com.ausregistry.jtoolkit2.se.NotificationResponse} for further
 * details.
 */
public class DomainNotificationResponse extends NotificationResponse {
    private static final long serialVersionUID = 6750726645375059027L;

    private static final String DOM_NAME_EXPR = exprReplace(IDENT_EXPR);
    private static final String DOM_RESULT_EXPR = exprReplace(RESULT_EXPR);
    private static final String DOM_CLTRID_EXPR = exprReplace(CLTRID_EXPR);
    private static final String DOM_SVTRID_EXPR = exprReplace(SVTRID_EXPR);
    private static final String DOM_PADATE_EXPR = exprReplace(PADATE_EXPR);

    public DomainNotificationResponse() {
        super(StandardObjectType.DOMAIN);
    }

    private static String exprReplace(String expr) {
        return expr.replaceAll(OBJ, StandardObjectType.DOMAIN.getName()
                ).replaceAll(IDENT, StandardObjectType.DOMAIN.getIdentType());
    }

    protected String identifierExpr() {
        return DOM_NAME_EXPR;
    }

    protected String resultExpr() {
        return DOM_RESULT_EXPR;
    }

    protected String cltridExpr() {
        return DOM_CLTRID_EXPR;
    }

    protected String svtridExpr() {
        return DOM_SVTRID_EXPR;
    }

    protected String padateExpr() {
        return DOM_PADATE_EXPR;
    }
}

