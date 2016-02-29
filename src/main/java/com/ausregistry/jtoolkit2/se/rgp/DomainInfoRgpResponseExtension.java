package com.ausregistry.jtoolkit2.se.rgp;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <p>Extension for the EPP Domain Info response, representing the RGP Info aspect of the Registry Grace Period
 * extension.</p>
 *
 * <p>Use this to access the registry grace period statuses for a domain as provided in an EPP Domain Info response
 * compliant with RFC5730, RFC5731 and RFC3915. Such a service element is sent by a compliant EPP server in response
 * to a valid Domain Info command.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 * @see com.ausregistry.jtoolkit2.se.DomainInfoResponse
 * @see <a href="http://tools.ietf.org/html/rfc3915">Domain Registry Grace Period Mapping for the
 * Extensible Provisioning Protocol (EPP)</a>
 */
public final class DomainInfoRgpResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -821812925617413583L;

    private final String restoreDomainPrefix = ExtendedObjectType.RESTORE.getName();
    private final String responseExtension;

    private final String rgpStatusesExpr = ResponseExtension.EXTENSION_EXPR
            + "/"
            + restoreDomainPrefix
            + ":RESPONSE_TYPE/"
            + restoreDomainPrefix + ":rgpStatus";

    private List<RgpStatus> rgpStatuses;
    private boolean initialised;

    public DomainInfoRgpResponseExtension(String responseExtension) {
        this.responseExtension = responseExtension;
    }

    /**
     * @see com.ausregistry.jtoolkit2.se.ResponseExtension#fromXML(com.ausregistry.jtoolkit2.xml.XMLDocument)
     */
    @Override
public void fromXML(final XMLDocument xmlDoc)
            throws XPathExpressionException {
        final NodeList rgpStatusNodes = xmlDoc.getElements(replaceResponseType(rgpStatusesExpr, responseExtension));

        if (rgpStatusNodes == null) {
            initialised = false;
        } else {
            rgpStatuses = new ArrayList<RgpStatus>();
            for (int i = 0; i < rgpStatusNodes.getLength(); i++) {
                final Element currentRgpStatus = (Element) rgpStatusNodes.item(i);
                final String status = currentRgpStatus.getAttribute("s");
                final String language = currentRgpStatus.getAttribute("lang");
                final String message = currentRgpStatus.getTextContent();
                rgpStatuses.add(new RgpStatus(status, language, message));
            }
            initialised = (rgpStatusNodes.getLength() > 0);
        }
    }

    public List<RgpStatus> getRgpStatuses() {
        return rgpStatuses;
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }
}
