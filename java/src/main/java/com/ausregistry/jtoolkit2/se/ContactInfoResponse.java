package com.ausregistry.jtoolkit2.se;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Use this to access contact object information as provided in an EPP contact
 * info response compliant with RFC5730 and RFC5733.  Such a service element is
 * sent by a compliant EPP server in response to a valid contact info command,
 * implemented by the ContactInfoCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.ContactInfoCommand
 */
public class ContactInfoResponse extends InfoResponse {
    private static final long serialVersionUID = 8856905477910465383L;

    private static final String CON_ROID_EXPR = exprReplace(ROID_EXPR);
    private static final String CON_CR_ID_EXPR = exprReplace(CR_ID_EXPR);
    private static final String CON_UP_ID_EXPR = exprReplace(UP_ID_EXPR);
    private static final String CON_CL_ID_EXPR = exprReplace(CL_ID_EXPR);
    private static final String CON_CR_DATE_EXPR = exprReplace(CR_DATE_EXPR);
    private static final String CON_UP_DATE_EXPR = exprReplace(UP_DATE_EXPR);
    private static final String CON_TR_DATE_EXPR = exprReplace(TR_DATE_EXPR);
    private static final String CON_STATUS_COUNT_EXPR =
        exprReplace(STATUS_COUNT_EXPR);
    private static final String CON_STATUS_EXPR = exprReplace(STATUS_EXPR);

    private static final String CON_INF_DATA_EXPR = exprReplace(INF_DATA_EXPR);
    private static final String CON_ID_EXPR =
        CON_INF_DATA_EXPR + "/contact:id/text()";
    private static final String CON_PW_EXPR =
        CON_INF_DATA_EXPR + "/contact:authInfo/contact:pw/text()";

    private static final String CON_PINFO_INT_EXPR =
        CON_INF_DATA_EXPR + "/contact:postalInfo[@type='int']";
    private static final String CON_PINFO_INT_NAME_EXPR =
        CON_PINFO_INT_EXPR + "/contact:name/text()";
    private static final String CON_PINFO_INT_ORG_EXPR =
        CON_PINFO_INT_EXPR + "/contact:org/text()";
    private static final String CON_PINFO_INT_STREET_EXPR =
        CON_PINFO_INT_EXPR + "/contact:addr/contact:street";
    private static final String CON_PINFO_INT_CITY_EXPR =
        CON_PINFO_INT_EXPR + "/contact:addr/contact:city/text()";
    private static final String CON_PINFO_INT_SP_EXPR =
        CON_PINFO_INT_EXPR + "/contact:addr/contact:sp/text()";
    private static final String CON_PINFO_INT_PC_EXPR =
        CON_PINFO_INT_EXPR + "/contact:addr/contact:pc/text()";
    private static final String CON_PINFO_INT_CC_EXPR =
        CON_PINFO_INT_EXPR + "/contact:addr/contact:cc/text()";

    private static final String CON_PINFO_LOC_EXPR =
        CON_INF_DATA_EXPR + "/contact:postalInfo[@type='loc']";
    private static final String CON_PINFO_LOC_NAME_EXPR =
        CON_PINFO_LOC_EXPR + "/contact:name/text()";
    private static final String CON_PINFO_LOC_ORG_EXPR =
        CON_PINFO_LOC_EXPR + "/contact:org/text()";
    private static final String CON_PINFO_LOC_STREET_EXPR =
        CON_PINFO_LOC_EXPR + "/contact:addr/contact:street";
    private static final String CON_PINFO_LOC_CITY_EXPR =
        CON_PINFO_LOC_EXPR + "/contact:addr/contact:city/text()";
    private static final String CON_PINFO_LOC_SP_EXPR =
        CON_PINFO_LOC_EXPR + "/contact:addr/contact:sp/text()";
    private static final String CON_PINFO_LOC_PC_EXPR =
        CON_PINFO_LOC_EXPR + "/contact:addr/contact:pc/text()";
    private static final String CON_PINFO_LOC_CC_EXPR =
        CON_PINFO_LOC_EXPR + "/contact:addr/contact:cc/text()";

    private static final String CON_VOICE_EXPR =
        CON_INF_DATA_EXPR + "/contact:voice/text()";
    private static final String CON_VOICEX_EXPR =
        CON_INF_DATA_EXPR + "/contact:voice/@x";
    private static final String CON_FAX_EXPR =
        CON_INF_DATA_EXPR + "/contact:fax/text()";
    private static final String CON_FAXX_EXPR =
        CON_INF_DATA_EXPR + "/contact:fax/@x";
    private static final String CON_EMAIL_EXPR =
        CON_INF_DATA_EXPR + "/contact:email/text()";
    private static final String CON_DISCLOSE_EXPR =
        CON_INF_DATA_EXPR + "/contact:disclose";
    private static final String CON_DISCLOSE_COUNT_EXPR =
        "count(" + CON_DISCLOSE_EXPR + "/*)";
    private static final String CON_DISCLOSE_FLAG_EXPR =
        CON_DISCLOSE_EXPR + "/@flag";
    private static final String CON_DISCLOSE_CHILD_EXPR =
        CON_DISCLOSE_EXPR + "/*[IDX]";
    private static final String CON_DISCLOSE_NAME_EXPR = "/local-name()";
    private static final String CON_DISCLOSE_TYPE_EXPR = "/@type";

    private String id;
    private IntPostalInfo intPostalInfo;
    private LocalPostalInfo locPostalInfo;
    private String voice;
    private int voiceX;
    private String fax;
    private int faxX;
    private String email;
    private String pw;
    private boolean discloseFlag;
    private DiscloseItem[] items;

    public ContactInfoResponse() {
        super(StandardObjectType.CONTACT);
        voiceX = -1;
        faxX = -1;
    }

    public String getID() {
        return id;
    }

    public IntPostalInfo getIntPostalInfo() {
        return intPostalInfo;
    }

    public LocalPostalInfo getLocPostalInfo() {
        return locPostalInfo;
    }

    public String getVoice() {
        return voice;
    }

    public int getVoiceExtension() {
        return voiceX;
    }

    public String getFax() {
        return fax;
    }

    public int getFaxExtension() {
        return faxX;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return pw;
    }

    public DiscloseItem[] getDiscloseItems() {
        return items;
    }

    /**
     * If disclosure information is not present in the response, then this will
     * return null; otherwise it will indicate whether the disclose items in
     * getDiscloseItems() should be disclosed or not.
     *
     * @return a Boolean representation of the flag attribute of the disclose
     * element (if present), or null if the disclose element is not present in
     * the response.
     */
    public Boolean isDisclosed() {
        if (items != null) {
            return Boolean.valueOf(discloseFlag);
        } else {
            return null;
        }
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        debugLogger.finest("enter");
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            id = xmlDoc.getNodeValue(CON_ID_EXPR);

            String iName = xmlDoc.getNodeValue(CON_PINFO_INT_NAME_EXPR);
            String iOrg = xmlDoc.getNodeValue(CON_PINFO_INT_ORG_EXPR);
            String[] iStreet = xmlDoc.getNodeValues(CON_PINFO_INT_STREET_EXPR);
            String iCity = xmlDoc.getNodeValue(CON_PINFO_INT_CITY_EXPR);
            String iSP = xmlDoc.getNodeValue(CON_PINFO_INT_SP_EXPR);
            String iPC = xmlDoc.getNodeValue(CON_PINFO_INT_PC_EXPR);
            String iCC = xmlDoc.getNodeValue(CON_PINFO_INT_CC_EXPR);

            intPostalInfo = new IntPostalInfo(iName, iOrg, iStreet, iCity,
                    iSP, iPC, iCC);

            String lName = xmlDoc.getNodeValue(CON_PINFO_LOC_NAME_EXPR);

            if (lName != null && lName.length() > 0) {
                String lOrg = xmlDoc.getNodeValue(CON_PINFO_LOC_ORG_EXPR);
                String[] lStreet = xmlDoc.getNodeValues(CON_PINFO_LOC_STREET_EXPR);
                String lCity = xmlDoc.getNodeValue(CON_PINFO_LOC_CITY_EXPR);
                String lSP = xmlDoc.getNodeValue(CON_PINFO_LOC_SP_EXPR);
                String lPC = xmlDoc.getNodeValue(CON_PINFO_LOC_PC_EXPR);
                String lCC = xmlDoc.getNodeValue(CON_PINFO_LOC_CC_EXPR);

                locPostalInfo = new LocalPostalInfo(lName, lOrg, lStreet, lCity,
                        lSP, lPC, lCC);
            }

            voice = xmlDoc.getNodeValue(CON_VOICE_EXPR);
            String voiceXStr = xmlDoc.getNodeValue(CON_VOICEX_EXPR);
            if (voiceXStr != null && voiceXStr.length() > 0) {
                voiceX = Integer.parseInt(voiceXStr);
            }
            fax = xmlDoc.getNodeValue(CON_FAX_EXPR);
            String faxXStr = xmlDoc.getNodeValue(CON_FAXX_EXPR);
            if (faxXStr != null && faxXStr.length() > 0) {
                faxX = Integer.parseInt(faxXStr);
            }
            email = xmlDoc.getNodeValue(CON_EMAIL_EXPR);
            pw = xmlDoc.getNodeValue(CON_PW_EXPR);
            String flagStr = xmlDoc.getNodeValue(CON_DISCLOSE_FLAG_EXPR);
            if (flagStr != null) {
                discloseFlag = flagStr.equals("1") ? true : false;
            }
            int count = xmlDoc.getNodeCount(CON_DISCLOSE_COUNT_EXPR);
            if (count > 0) {
                items = new DiscloseItem[count];

                for (int i = 0; i < count; i++) {
                    String qry = ReceiveSE.replaceIndex(CON_DISCLOSE_CHILD_EXPR, i + 1);
                    String childName =
                        xmlDoc.getNodeValue(qry + CON_DISCLOSE_NAME_EXPR);
                    String childType =
                        xmlDoc.getNodeValue(qry + CON_DISCLOSE_TYPE_EXPR);

                    if (childType == null || childType.length() == 0) {
                        items[i] = new DiscloseItem(childName);
                    } else {
                        items[i] = new DiscloseItem(childName, childType);
                    }
                }
            } else {
                items = null;
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.info(xpee.getMessage());
        }

        debugLogger.finest("exit");
    }

    protected String roidExpr() {
        return CON_ROID_EXPR;
    }

    protected String crIDExpr() {
        return CON_CR_ID_EXPR;
    }

    protected String upIDExpr() {
        return CON_UP_ID_EXPR;
    }

    protected String clIDExpr() {
        return CON_CL_ID_EXPR;
    }

    protected String crDateExpr() {
        return CON_CR_DATE_EXPR;
    }

    protected String upDateExpr() {
        return CON_UP_DATE_EXPR;
    }

    protected String trDateExpr() {
        return CON_TR_DATE_EXPR;
    }

    protected String statusExpr() {
        return CON_STATUS_EXPR;
    }

    protected String statusCountExpr() {
        return CON_STATUS_COUNT_EXPR;
    }

    protected static String exprReplace(String expr) {
        return expr.replaceAll(OBJ,
                StandardObjectType.CONTACT.getName());
    }
}

