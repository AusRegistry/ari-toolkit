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
 * Extension of the domain mapping of the EPP info response for registry grace periods, as
 * defined in RFC 3915, the specification which is found in XML schema definition
 * urn:ietf:params:xml:ns:rgp-1.0.
 *
 * Instances of this class provide an interface to access info data for registry grace periods
 * as provided in an EPP domain info response. This relies on the instance first being initialised
 * by a suitable EPP domain info response using the method fromXML. Such a service element is
 * sent by a EPP server in response to a valid domain info command as implemented by the
 * DomainInfoCommand class, with RGP extensions as implemented by RGPDomainInfoCommandExtension class.
 *
 * For flexibility, this implementation extracts the data from the response
 * using XPath queries, the expressions for which are defined statically.
 *
 * @see com.ausregistry.jtoolkit2.se.ResponseExtension
 */
public final class DomainInfoRgpResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -821812925617413583L;

    private final String restoreDomainPrefix = ExtendedObjectType.RESTORE
            .getName();
    private final String responseExtension;

    private final String rgpStatusesExpr = ResponseExtension.EXTENSION_EXPR
            + "/"
            + restoreDomainPrefix
            + ":RESPONSE_TYPE/"
            + restoreDomainPrefix + ":rgpStatus";

    List<RgpStatus> rgpStatuses;
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
                final Element currentRgpStatus= (Element) rgpStatusNodes.item(i);
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
