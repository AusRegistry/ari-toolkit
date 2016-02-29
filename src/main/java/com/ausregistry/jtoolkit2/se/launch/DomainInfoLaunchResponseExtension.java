package com.ausregistry.jtoolkit2.se.launch;

import static com.ausregistry.jtoolkit2.se.ExtendedObjectType.MARK;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.se.tmch.MarksList;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Element;

/**
 * <p>Representation of the EPP Domain Info response for the Domain Name Launch extension.</p>
 *
 * <p>Use this to get information about an application.
 * Such a service element is sent by a compliant EPP server in response
 * to a valid Domain Info command with the Domain Name Launch extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see DomainInfoLaunchResponseExtension
 * @see <a href="https://tools.ietf.org/html/draft-ietf-eppext-launchphase-07">Domain Name Launch
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainInfoLaunchResponseExtension extends ResponseExtension {

    private static final String LAUNCH_INF_DATA_EXPRESSION = EXTENSION_EXPR + "/launch:infData/";
    private static final String CHKDATA_COUNT_EXPR = "count(" + LAUNCH_INF_DATA_EXPRESSION + "*)";
    private static final String CHKDATA_PHASE_EXPR = LAUNCH_INF_DATA_EXPRESSION + "launch:phase";
    private static final String CHKDATA_PHASE_EXISTS_EXPR = "count(" + LAUNCH_INF_DATA_EXPRESSION + "launch:phase)";
    private static final String CHKDATA_PHASE_NAME_EXPR = "/@name";
    private static final String CHKDATA_APPLICATION_ID_EXPR = LAUNCH_INF_DATA_EXPRESSION
            + "launch:applicationID/text()";
    private static final String CHKDATA_APPLICATION_ID_EXISTS_EXPR = "count(" + LAUNCH_INF_DATA_EXPRESSION
            + "launch:applicationID)";

    private static final String CHKDATA_STATUS_EXPR = LAUNCH_INF_DATA_EXPRESSION
            + "launch:status/@s";
    private static final String CHKDATA_STATUS_NAME_EXPR = LAUNCH_INF_DATA_EXPRESSION
            + "launch:status/@name";
    private static final String CHKDATA_STATUS_EXISTS_EXPR = "count(" + LAUNCH_INF_DATA_EXPRESSION
            + "launch:status)";

    private static final String MARK_EXPR = LAUNCH_INF_DATA_EXPRESSION + MARK.getName() + ":mark";
    private static final String MARK_STATUS_EXISTS_EXPR = "count(" + MARK_EXPR + ")";

    private boolean isInitialised;
    private String phaseType;
    private String phaseName;
    private String applicationID;
    private String status;
    private String statusName;
    private MarksList marksList;

    /**
     * @param xmlDoc the XML to be processed
     */
    @Override
    public final void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        int elementCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);

        if (xmlDoc.getNodeCount(CHKDATA_PHASE_EXISTS_EXPR) > 0) {
            processPhaseElement(xmlDoc);
        }

        if (xmlDoc.getNodeCount(CHKDATA_APPLICATION_ID_EXISTS_EXPR) > 0) {
            applicationID = xmlDoc.getNodeValue(CHKDATA_APPLICATION_ID_EXPR);
        }

        if (xmlDoc.getNodeCount(CHKDATA_STATUS_EXISTS_EXPR) > 0) {
            status = xmlDoc.getNodeValue(CHKDATA_STATUS_EXPR);
            statusName = xmlDoc.getNodeValue(CHKDATA_STATUS_NAME_EXPR);
        }

        if (xmlDoc.getNodeCount(MARK_STATUS_EXISTS_EXPR) > 0) {
            Element markElement = (Element) xmlDoc.getElement(MARK_EXPR);
            if (markElement != null) {
                marksList = new MarksList();
                marksList.fromXML(new XMLDocument(markElement));
            }
        }

        if (elementCount > 0) {
            isInitialised = true;
        }
    }

    @Override
    public boolean isInitialised() {
        return isInitialised;
    }

    /**
     * @return phaseType the phase in the original request
    */
    public String getPhaseType() {
        return phaseType;
    }

    private void processPhaseElement(XMLDocument xmlDoc) throws XPathExpressionException {
        phaseType = xmlDoc.getNodeValue(CHKDATA_PHASE_EXPR);
        phaseName = xmlDoc.getNodeValue(CHKDATA_PHASE_EXPR + CHKDATA_PHASE_NAME_EXPR);
    }

    public String getApplicationID() {
        return applicationID;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }

    public MarksList getMarksList() {
        return marksList;
    }
}
