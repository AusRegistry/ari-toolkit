package com.ausregistry.jtoolkit2.se;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Provides access to EPP server parameters as published in an EPP greeting
 * service element.
 *
 */
public final class Greeting extends ReceiveSE {
    private static final long serialVersionUID = 4358903746268469400L;

    private static final String GREETING_EXPR = "/e:epp/e:greeting";
    private static final String DCP_EXPR = GREETING_EXPR + "/e:dcp";
    private static final String SVID_EXPR = GREETING_EXPR + "/e:svID/text()";
    private static final String SVDATE_EXPR = GREETING_EXPR + "/e:svDate/text()";
    private static final String VERSIONS_EXPR = GREETING_EXPR + "/e:svcMenu/e:version";
    private static final String LANGS_EXPR = GREETING_EXPR + "/e:svcMenu/e:lang";
    private static final String OBJ_URIS_EXPR = GREETING_EXPR + "/e:svcMenu/e:objURI";
    private static final String EXT_URIS_EXPR = GREETING_EXPR + "/e:svcMenu/e:svcExtension/e:extURI";
    private static final String ACCESS_EXPR = DCP_EXPR + "/e:access/*[1]";
    private static final String EXPIRY_EXPR = DCP_EXPR + "/e:expiry/*[1]";
    private static final String STMT_COUNT_EXPR = "count(" + DCP_EXPR + "/e:statement)";
    private static final String STMT_IND_EXPR = DCP_EXPR + "/e:statement[IDX]";
    private static final String PURPOSE_EXPR = "/e:purpose";
    private static final String RECIPIENT_EXPR = "/e:recipient";
    private static final String RETENTION_EXPR = "/e:retention/*[1]";

    private String svID;
    private GregorianCalendar svDate;
    private String[] versions;
    private String[] langs;
    private String[] objURIs;
    private String[] extURIs;
    private String dcpAccess;
    private String dcpExpiry;
    private DCPStatement[] dcpStatements;

    public Greeting() {
        super();
    }

    public String getServerID() {
        return svID;
    }

    public GregorianCalendar getServerDateTime() {
        return svDate;
    }

    public String[] getProtocolVersions() {
        return versions;
    }

    public String[] getLanguages() {
        return langs;
    }

    public String[] objURIs() {
        return objURIs;
    }

    public String[] extURIs() {
        return extURIs;
    }

    public String dcpAccess() {
        return dcpAccess;
    }

    public String dcpExpiry() {
        // Known limitation: actual expiry date or period is not available.
        return dcpExpiry;
    }

    public DCPStatement[] getDataCollectionPolicyStatements() {
        return dcpStatements;
    }

    @Override
    public String toString() {
        String versionString = arrayToString(versions, ",");
        String langString = arrayToString(langs, ",");
        String objURIString = arrayToString(objURIs, ",");

        String retval = "(svID = " + getServerID() + ")" + "(svDate = "
                + getServerDateTime() + ")" + "(versions = (" + versionString
                + "))" + "(languages = (" + langString + "))" + "(objURIs = ("
                + objURIString + "))";

        return retval;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        debugLogger.finest("enter");

        try {
            debugLogger.info(xmlDoc.toString());
            svID = xmlDoc.getNodeValue(SVID_EXPR);
            String svDateText = xmlDoc.getNodeValue(SVDATE_EXPR);
            svDate = EPPDateFormatter.fromXSDateTime(svDateText);
            versions = xmlDoc.getNodeValues(VERSIONS_EXPR);
            langs = xmlDoc.getNodeValues(LANGS_EXPR);
            objURIs = xmlDoc.getNodeValues(OBJ_URIS_EXPR);
            extURIs = xmlDoc.getNodeValues(EXT_URIS_EXPR);
            dcpAccess = xmlDoc.getNodeName(ACCESS_EXPR);
            dcpExpiry = xmlDoc.getNodeName(EXPIRY_EXPR);

            int dcpStmtCount = xmlDoc.getNodeCount(STMT_COUNT_EXPR);
            dcpStatements = new DCPStatement[dcpStmtCount];
            for (int i = 0; i < dcpStmtCount; i++) {
                String qry = ReceiveSE.replaceIndex(STMT_IND_EXPR, i + 1);
                dcpStatements[i] = new DCPStatement(xmlDoc.getChildNames(qry
                        + PURPOSE_EXPR), xmlDoc.getChildNames(qry
                        + RECIPIENT_EXPR), xmlDoc.getNodeName(qry
                        + RETENTION_EXPR));
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }

        debugLogger.info(toString());
        debugLogger.finest("exit");
    }

    /**
     * Provides access to the data collection policy statement parameter values
     * reported by an EPP server in a Greeting compliant with the greeting
     * specification in RFC5730.
     */
    public static final class DCPStatement implements Serializable {
        private static final long serialVersionUID = -589856890805744448L;
        private String[] purposes;
        private String[] recipients;
        private String retention;

        DCPStatement(String[] purposes, String[] recipients,
                String retentionPolicy) {

            this.purposes = purposes;
            this.recipients = recipients;
            this.retention = retentionPolicy;
        }

        public String[] getPurpose() {
            return purposes;
        }

        public String[] getRecipients() {
            // Known limitation: the recDesc child of 'ours' is not recorded.
            return recipients;
        }

        public String getRetentionPolicy() {
            return retention;
        }
    }
}
