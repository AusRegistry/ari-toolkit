package com.ausregistry.jtoolkit2.se.tmch;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Extension for the EPP Domain Info response, representing the Trademark Clearing House Info aspect
 * of the Domain Name Application extension.</p>
 *
 * <p>Use this to access domain Trademark Clearing House data for a domain
 * as provided in an EPP Domain Info response compliant with RFC???.
 * Such a service element is sent by a compliant EPP server in response to a valid Domain Info command
 * with the Trademark Clearing House extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 * @see com.ausregistry.jtoolkit2.se.tmch.TmchDomainInfoResponseExtension
 * @see <a href="http://ausregistry.github.io/doc/tmch-1.0/tmch-1.0.html">Domain Name Trademark Clearing House
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class TmchDomainInfoResponseExtension extends ResponseExtension {
    private static final long serialVersionUID = 3270326812560507483L;

    private static final String TMCH_PREFIX = ExtendedObjectType.TMCH.getName();
    private static final String TMCH_XPATH_PREFIX = ResponseExtension.EXTENSION_EXPR + "/" + TMCH_PREFIX
                + ":RESPONSE_TYPE/" + TMCH_PREFIX;

    private static final String ENCODED_SIGNED_MARK_DATA = TMCH_XPATH_PREFIX + ":smd/text()";

    private String encodedSignedMarkData;
    private String responseType;
    private boolean initialised;

    public TmchDomainInfoResponseExtension(String responseType) {
        this.responseType = responseType;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {

        encodedSignedMarkData = xmlDoc.getNodeValue(replaceResponseType(ENCODED_SIGNED_MARK_DATA, responseType));

        initialised = encodedSignedMarkData != null;
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getEncodedSignedMarkData() {
        return encodedSignedMarkData;
    }
}
