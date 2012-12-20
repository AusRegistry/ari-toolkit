package com.ausregistry.jtoolkit2.launch;

import java.util.Calendar;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

public class LaunchDomainCreateResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -2134370036980424435L;

    private static final String LAUNCH_PREFIX = ExtendedObjectType.LAUNCH.getName();

    private static final String APPLICATION_ID = ResponseExtension.EXTENSION_EXPR + "/" + LAUNCH_PREFIX
            + ":RESPONSE_TYPE/" + LAUNCH_PREFIX + ":id/text()";
    private static final String CREATE_DATE = ResponseExtension.EXTENSION_EXPR + "/" + LAUNCH_PREFIX
            + ":RESPONSE_TYPE/" + LAUNCH_PREFIX + ":crDate/text()";

    private String applicationId;
    private Calendar createDate;
    private String responseType;
    private boolean initialised;

    public LaunchDomainCreateResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    /**
     * @see com.ausregistry.jtoolkit2.se.ResponseExtension#fromXML(XMLDocument)
     */
    @Override
    public void fromXML(final XMLDocument xmlDoc) throws XPathExpressionException {
        applicationId = xmlDoc.getNodeValue(replaceResponseType(APPLICATION_ID, responseType));
        String creDate = xmlDoc.getNodeValue(replaceResponseType(CREATE_DATE, responseType));
        createDate = EPPDateFormatter.fromXSDateTime(creDate);

        initialised = (applicationId != null && createDate != null);
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

}
