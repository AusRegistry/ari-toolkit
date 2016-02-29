package com.ausregistry.jtoolkit2.se;

import java.util.HashMap;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Representation of the EPP check response, as defined in RFC5730. Subclasses
 * of this must specify the object to which the command is mapped. Instances of
 * this class provide an interface to access availability information for each
 * object identified in a {@link com.ausregistry.jtoolkit2.se.CheckCommand}.
 * This relies on the instance first being initialised by a suitable EPP check
 * response using the method fromXML. For flexibility, this implementation
 * extracts the data from the response using XPath queries, the expressions for
 * which are defined statically.
 *
 * @param <I> the type of object identifier used in this check response
 *
 * @see com.ausregistry.jtoolkit2.se.CheckCommand
 */
public abstract class CheckResponse<I> extends DataResponse {

    protected static final String CHKDATA_COUNT_EXPR = "count(" + RES_DATA_EXPR + "/OBJ:chkData/*)";
    protected static final String CHKDATA_IND_EXPR = RES_DATA_EXPR + "/OBJ:chkData/OBJ:cd[IDX]";
    protected static final String CHKDATA_IDENT_EXPR = "/OBJ:IDENT/text()";
    protected static final String CHKDATA_AVAIL_EXPR = "/OBJ:IDENT/@avail";
    protected static final String CHKDATA_REASON_EXPR = "/OBJ:reason/text()";

    private static final long serialVersionUID = 7769699662780402541L;

    private HashMap<I, Availability> availMap;

    private boolean[] availArray;
    private String[] reasonArray;

    public CheckResponse(ObjectType objectType) {
        super(StandardCommandType.CHECK, objectType);
        availMap = new HashMap<I, Availability>();
    }

    public boolean isAvailable(final I nameID) {
        return (availMap.get(nameID)).isAvail();
    }

    public String getReason(final I nameID) {
        return (availMap.get(nameID)).getReason();
    }

    public String getReason(int index) {
        return reasonArray[index];
    }

    public Set<I> getNameIDs() {
        return availMap.keySet();
    }

    public boolean[] getAvailableList() {
        return availArray;
    }

    public String[] getReasonList() {
        return reasonArray;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            int cdCount = xmlDoc.getNodeCount(chkDataCountExpr());
            availArray = new boolean[cdCount];
            reasonArray = new String[cdCount];

            for (int i = 0; i < cdCount; i++) {
                String qry = replaceIndex(chkDataIndexExpr(), i + 1);
                final I key = getKey(xmlDoc, qry);
                String availStr = xmlDoc.getNodeValue(qry + chkDataAvailExpr());
                boolean avail = (availStr.equals("1") ? true : false);
                availArray[i] = avail;
                String reason = xmlDoc.getNodeValue(qry + chkDataReasonExpr());
                if (reason != null && reason.length() > 0) {
                    reasonArray[i] = reason;
                }

                availMap.put(key, new Availability(avail, reason));
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }

    /**
     * Returns an object identifier given an XPath expression for a check data element.
     * @param xmlDoc the EPP check response message
     * @param qry the XPath query expression
     * @return an object identifier
     * @throws XPathExpressionException
     */
    protected abstract I getKey(final XMLDocument xmlDoc, final String qry)
            throws XPathExpressionException;

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(super.toString());
        for (I name : availMap.keySet()) {
            boolean avail = availMap.get(name).isAvail();
            String reason = availMap.get(name).getReason();
            buf.append("(name = " + name + ")(available = " + avail
                    + ")(reason = " + reason + ")");
        }
        return buf.toString();
    }

    protected abstract String chkDataCountExpr();

    protected abstract String chkDataIndexExpr();

    protected abstract String chkDataTextExpr();

    protected abstract String chkDataAvailExpr();

    protected abstract String chkDataReasonExpr();
}
