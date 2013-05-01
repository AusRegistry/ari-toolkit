package com.ausregistry.jtoolkit2.se.app;


import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

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
