package com.ausregistry.jtoolkit2.se.blocked;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Extension for the EPP Domain Renew response, representing the Block Domain Renew
 * aspects of the Blocked Domain Name extension.</p>
 *
 * <p>Use this to access renew data for a domain block as provided in an EPP Domain Renew response compliant
 * with RFC5730 and RFC5731. Such a service element is sent by a compliant EPP server in response to a valid
 * Domain Renew command with the Blocked Domain Name extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewCommand
 * @see com.ausregistry.jtoolkit2.se.blocked.DomainRenewBlockCommandExtension
 * @see <a href="http://ausregistry.github.io/doc/blocked-1.0/blocked-1.0.html">Blocked Domain Name
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainRenewBlockResponseExtension extends ResponseExtension {
    private static final long serialVersionUID = -4403413192868009866L;

    private static final String BLO_PREFIX = ExtendedObjectType.BLOCKED.getName();
    private static final String BLO_XPATH_PREFIX = ResponseExtension.EXTENSION_EXPR + "/" + BLO_PREFIX
            + ":RESPONSE_TYPE/" + BLO_PREFIX;
    private static final String ID = BLO_XPATH_PREFIX + ":id/text()";
    private static final String RESPONSE_TYPE = ResponseExtension.RENEW;

    private String id;
    private boolean initialised;

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        id = xmlDoc.getNodeValue(replaceResponseType(ID, RESPONSE_TYPE));
        initialised = id != null;
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public String getId() {
        return id;
    }
}