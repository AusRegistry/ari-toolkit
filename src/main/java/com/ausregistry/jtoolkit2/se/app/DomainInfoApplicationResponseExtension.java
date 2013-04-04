package com.ausregistry.jtoolkit2.se.app;

import javax.xml.xpath.XPathExpressionException;
import java.util.Arrays;
import java.util.List;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Use this to access info data for a domain as provided in an EPP domain
 * info response compliant with RFC5731.  Such a service element
 * is sent by a compliant EPP server in response to a valid domain info
 * command with domain info application extension.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 * @see DomainInfoApplicationCommandExtension
 */
public class DomainInfoApplicationResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = 5051313555726197553L;

    private static final String APP_PREFIX = ExtendedObjectType.APP.getName();
    private static final String APP_XPATH_PREFIX = ResponseExtension.EXTENSION_EXPR + "/" + APP_PREFIX
                + ":RESPONSE_TYPE/" + APP_PREFIX;

    private static final String APPLICATION_ID = APP_XPATH_PREFIX + ":id/text()";
    private static final String PHASE_ID = APP_XPATH_PREFIX + ":phase/text()";
    private static final String STATUS_EXPR = APP_XPATH_PREFIX + ":status/@s";
    private static final String CREATE_DATE = APP_XPATH_PREFIX + ":crDate/text()";
    private static final String UPDATE_DATE = APP_XPATH_PREFIX + ":upDate/text()";

    private String applicationId;
    private String phase;
    private List<String> statuses;
    private String responseType;
    private boolean initialised;

    public DomainInfoApplicationResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        
        applicationId = xmlDoc.getNodeValue(replaceResponseType(APPLICATION_ID, responseType));
        phase = xmlDoc.getNodeValue(replaceResponseType(PHASE_ID, responseType));
        statuses = Arrays.asList(xmlDoc.getNodeValues(replaceResponseType(STATUS_EXPR, responseType)));

        initialised = applicationId!= null && phase != null && statuses != null;
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getPhase() {
        return phase;
    }

    public List<String> getStatuses() {
        return statuses;
    }

}
