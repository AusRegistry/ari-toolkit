package com.ausregistry.jtoolkit2.se.app;


import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Extension for the EPP Domain Create response, representing the Application Create aspect of the Domain Name
 * Application extension.</p>
 *
 * <p>Use this to access create domain application data for a domain as provided in an EPP Domain Create response
 * compliant with RFC5730 and RFC5731. Such a service element is sent by a compliant EPP server in response to a valid
 * Domain Create command with the Domain Name Application extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 * @see DomainCreateApplicationCommandExtension
 * @see <a href="http://ausregistry.github.io/doc/application-1.0/application-1.0.html">Domain Name Application
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCreateApplicationResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -6007874008986690757L;

    private static final String APP_PREFIX = ExtendedObjectType.APP.getName();

    private static final String APP_XPATH_PREFIX = ResponseExtension.EXTENSION_EXPR + "/" + APP_PREFIX
            + ":RESPONSE_TYPE/" + APP_PREFIX;
    private static final String APP_ID_EXPR = APP_XPATH_PREFIX + ":id/text()";
    private static final String APP_PHASE_EXPR = APP_XPATH_PREFIX + ":phase/text()";
    private static final String REPONSE_TYPE = ResponseExtension.CREATE;

    private boolean initialised = false;

    private String id;
    private String phase;



    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        id = xmlDoc.getNodeValue(replaceResponseType(
                APP_ID_EXPR, REPONSE_TYPE));
        phase = xmlDoc.getNodeValue(replaceResponseType(
                APP_PHASE_EXPR, REPONSE_TYPE));

        initialised = (id != null && phase != null);
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getId() {
        return id;
    }

    public String getPhase() {
        return phase;
    }
}
