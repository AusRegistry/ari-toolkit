package com.ausregistry.jtoolkit2.se.launch;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Extension for the EPP Domain Create response, representing the Application Create aspect of the Domain Name
 * Launch extension.</p>
 *
 * <p>Use this to access create domain application data for a domain as provided in an EPP Domain Create response
 * compliant with RFC5730 and RFC5731. Such a service element is sent by a compliant EPP server in response to a valid
 * Domain Create command with the Domain Name Launch extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.launch.DomainCreateLaunchCommandExtension
 * @see <a href="https://tools.ietf.org/html/draft-ietf-eppext-launchphase-07">Domain Name Launch
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCreateApplicationLaunchResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -6007874008986690757L;

    private static final String LAUNCH_PREFIX = ExtendedObjectType.LAUNCH.getName();

    private static final String LAUNCH_XPATH_PREFIX = ResponseExtension.EXTENSION_EXPR + "/" + LAUNCH_PREFIX
            + ":creData/" + LAUNCH_PREFIX;
    private static final String APP_ID_EXPR = LAUNCH_XPATH_PREFIX + ":applicationID/text()";
    private static final String PHASE_EXPR = LAUNCH_XPATH_PREFIX + ":phase/text()";
    private static final String PHASE_NAME_EXPR = LAUNCH_XPATH_PREFIX + ":phase/@name";

    private boolean initialised = false;

    private String id;
    private String phaseName;
    private String phaseType;


    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        id = xmlDoc.getNodeValue(APP_ID_EXPR);
        phaseName = xmlDoc.getNodeValue(PHASE_NAME_EXPR);
        phaseType = xmlDoc.getNodeValue(PHASE_EXPR);
        initialised = (id != null && phaseType != null);
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getId() {
        return id;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public String getPhaseType() {
        return phaseType; }
}
