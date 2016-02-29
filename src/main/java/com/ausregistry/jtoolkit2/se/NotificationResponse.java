package com.ausregistry.jtoolkit2.se;

import java.util.GregorianCalendar;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * When offline processing of an action has been completed by the server
 * operator, a message is enqueued for the client who requested the action.
 * The NotificationResponse class models the pending action notification data
 * informing the client of the completion of offline processing.  A
 * NotificationResponse is always specific to a particular object mapping, and
 * so a subclass of this models the specific object data - such an object is
 * returned by methods in {@link com.ausregistry.jtoolkit2.se.PollResponse}.
 *
 * @see com.ausregistry.jtoolkit2.se.PollRequestCommand
 * @see com.ausregistry.jtoolkit2.se.PollResponse
 */
public abstract class NotificationResponse extends DataResponse {

    protected static final String IDENT = "IDENT";

    protected static final String PAN_DATA_EXPR = RES_DATA_EXPR + "/OBJ:panData";
    protected static final String IDENT_EXPR = PAN_DATA_EXPR + "/OBJ:IDENT/text()";
    protected static final String RESULT_EXPR = PAN_DATA_EXPR + "/OBJ:IDENT/@paResult";
    protected static final String CLTRID_EXPR = PAN_DATA_EXPR + "/OBJ:paTRID/e:clTRID/text()";
    protected static final String SVTRID_EXPR = PAN_DATA_EXPR + "/OBJ:paTRID/e:svTRID/text()";
    protected static final String PADATE_EXPR = PAN_DATA_EXPR + "/OBJ:paDate/text()";

    private static final long serialVersionUID = 8926602539143476957L;

    private String identifier;
    private boolean result;
    private String cltrid;
    private String svtrid;
    private GregorianCalendar paDate;

    protected NotificationResponse(ObjectType objectType) {
        super(StandardCommandType.POLL, objectType);
    }

    /**
     * The identifier of the object which is the subject of this Pending Action
     * Notification Data.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * A positive boolean value indicates that the request has been approved
     * and completed.  A negative boolean value indicates that the request has
     * been denied and the requested action has not been taken.
     */
    public boolean getResult() {
        return result;
    }

    /**
     * The client transaction identifier identifier returned with the original
     * response to process the command.  The client transaction identifier is
     * optional and will only be returned if the client provided an identifier
     * with the original associated command.
     */
    public String getPaClTrID() {
        return cltrid;
    }

    /**
     * The server transaction identifier identifier returned with the original
     * response to process the command.
     */
    public String getPaSvTrID() {
        return svtrid;
    }

    /**
     * The date and time describing when review of the requested action was
     * completed.
     */
    public GregorianCalendar getPaDate() {
        return paDate;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        debugLogger.finest("enter");

        super.fromXML(xmlDoc);

        try {
            identifier = xmlDoc.getNodeValue(identifierExpr());

            String resultStr = xmlDoc.getNodeValue(resultExpr());
            result = resultStr.equals("1");

            cltrid = xmlDoc.getNodeValue(cltridExpr());
            svtrid = xmlDoc.getNodeValue(svtridExpr());

            String paDateStr = xmlDoc.getNodeValue(padateExpr());
            paDate = EPPDateFormatter.fromXSDateTime(paDateStr);
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }

        debugLogger.finest("exit");
    }

    @Override
    public String toString() {
        String retval = super.toString();

        retval += "(panData = ("
            + getObjType().getIdentType() + " = " + getIdentifier()
            + ")(result = " + String.valueOf(getResult())
            + ")(clTRID = " + getPaClTrID()
            + ")(svTRID = " + getPaSvTrID()
                        + ")(paDate = " + getPaDate() + "))";

        return retval;
    }

    protected abstract String identifierExpr();
    protected abstract String resultExpr();
    protected abstract String cltridExpr();
    protected abstract String svtridExpr();
    protected abstract String padateExpr();
}

