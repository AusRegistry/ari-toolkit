package com.ausregistry.jtoolkit2.launch;

import java.util.Calendar;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

public class DomainInfoApplicationResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = 5051313555726197553L;

    private static final String LAUNCH_PREFIX = ExtendedObjectType.LAUNCH.getName();
    private static final String LAUNCH_XPATH_PREFIX = ResponseExtension.EXTENSION_EXPR + "/" + LAUNCH_PREFIX
                + ":RESPONSE_TYPE/" + LAUNCH_PREFIX;

    private static final String APPLICATION_ID = LAUNCH_XPATH_PREFIX + ":id/text()";
    private static final String PHASE_ID = LAUNCH_XPATH_PREFIX + ":phase/text()";
    private static final String STATUS_EXPR = LAUNCH_XPATH_PREFIX + ":status/@s";
    private static final String CREATE_DATE = LAUNCH_XPATH_PREFIX + ":crDate/text()";
    private static final String UPDATE_DATE = LAUNCH_XPATH_PREFIX + ":upDate/text()";

    private String applicationId;
    private String phase;
    private String status;
    private Calendar createDate;
    private Calendar updateDate;
    private String responseType;
    private boolean initialised;

    public DomainInfoApplicationResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        
        applicationId = xmlDoc.getNodeValue(replaceResponseType(APPLICATION_ID, responseType));
        phase = xmlDoc.getNodeValue(replaceResponseType(PHASE_ID, responseType));
        status = xmlDoc.getNodeValue(replaceResponseType(STATUS_EXPR, responseType));

        String creDate = xmlDoc.getNodeValue(replaceResponseType(CREATE_DATE, responseType));
        createDate = EPPDateFormatter.fromXSDateTime(creDate);

        String updDate = xmlDoc.getNodeValue(replaceResponseType(UPDATE_DATE, responseType));
        updateDate = EPPDateFormatter.fromXSDateTime(updDate);

        initialised = applicationId!= null && phase != null && status != null && createDate != null; 
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

    public String getStatus() {
        return status;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

}
