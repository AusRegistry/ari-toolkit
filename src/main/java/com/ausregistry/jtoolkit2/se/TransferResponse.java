package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.EPPDateFormatter;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import java.util.GregorianCalendar;

import javax.xml.xpath.XPathExpressionException;

/**
 * Representation of the EPP transfer response, as defined in RFC5730.
 * Subclasses of this must specify the object to which the command is mapped.
 * Instances of this class provide an interface to access transfer response
 * data for the object identified in a {@link
 * com.ausregistry.jtoolkit2.se.TransferCommand}.  This relies on the instance
 * first being initialised by a suitable EPP transfer response using the method
 * fromXML.  For flexibility, this implementation extracts the data from the
 * response using XPath queries, the expressions for which are defined
 * statically.
 *
 * @see com.ausregistry.jtoolkit2.se.TransferCommand
 */
public abstract class TransferResponse extends DataResponse {

    protected static final String OBJ = "OBJ";
    protected static final String TR_STATUS_EXPR = RES_DATA_EXPR + "/OBJ:trnData/OBJ:trStatus/text()";
    protected static final String REID_EXPR = RES_DATA_EXPR + "/OBJ:trnData/OBJ:reID/text()";
    protected static final String REDATE_EXPR = RES_DATA_EXPR + "/OBJ:trnData/OBJ:reDate/text()";
    protected static final String ACID_EXPR = RES_DATA_EXPR + "/OBJ:trnData/OBJ:acID/text()";
    protected static final String ACDATE_EXPR = RES_DATA_EXPR + "/OBJ:trnData/OBJ:acDate/text()";

    private static final long serialVersionUID = 4700444182315651037L;

    private String trStatus;
    private String reID;
    private String acID;
    private GregorianCalendar reDate;
    private GregorianCalendar acDate;
    private String reDateStr;
    private String acDateStr;

    public TransferResponse(ObjectType objectType) {
        super(StandardCommandType.TRANSFER, objectType);
    }

    public String getTransferStatus() {
        return trStatus;
    }

    public String getRequestingClID() {
        return reID;
    }

    public GregorianCalendar getRequestDate() {
        return reDate;
    }

    public String getActioningClID() {
        return acID;
    }

    public GregorianCalendar getActionDate() {
        return acDate;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            trStatus = xmlDoc.getNodeValue(trStatusExpr());

            reID = xmlDoc.getNodeValue(reIDExpr());
            reDateStr = xmlDoc.getNodeValue(reDateExpr());
            if (reDateStr != null && reDateStr.length() > 0) {
                reDate = EPPDateFormatter.fromXSDateTime(reDateStr);
            }

            acID = xmlDoc.getNodeValue(acIDExpr());
            acDateStr = xmlDoc.getNodeValue(acDateExpr());
            if (acDateStr != null && acDateStr.length() > 0) {
                acDate = EPPDateFormatter.fromXSDateTime(acDateStr);
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }

    @Override
    public String toString() {
        String retval = super.toString();

        retval += "(transfer-data = (trStatus = " + trStatus
            + ")(requesting-client-id = " + reID
            + ")(request-date = " + reDateStr
            + ")(actioning-client-id = " + acID
            + ")(action-date = " + acDateStr + "))";

        return retval;
    }

    protected abstract String trStatusExpr();
    protected abstract String reIDExpr();
    protected abstract String reDateExpr();
    protected abstract String acIDExpr();
    protected abstract String acDateExpr();
}

