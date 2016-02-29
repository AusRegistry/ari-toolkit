package com.ausregistry.jtoolkit2.se.rgp;

import java.util.List;

import com.ausregistry.jtoolkit2.se.Response;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Representation of the EPP Domain Update response with the Redemption Status aspect of the
 * Registry Grace Period extension.</p>
 *
 * <p>Use this to access the Redemption Grace Period status as provided in an EPP Domain Update response
 * compliant with RFC5730, RFC5731 and RFC3915. Such a service element is sent by a compliant EPP server in response
 * to a valid Domain Update command with the Registry Grace Period extension.</p>
 *
 * <p>For flexibility, this implementation extracts the data from the response using XPath queries, the expressions
 * for which are defined statically.</p>
 *
 * @see DomainRestoreRequestCommand
 * @see DomainRestoreReportCommand
 * @see <a href="http://tools.ietf.org/html/rfc3915">Domain Registry Grace Period Mapping for the
 * Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainRestoreResponse extends Response {
    private static final long serialVersionUID = -5724827272682186647L;

    private final DomainInfoRgpResponseExtension restoreExtension = new DomainInfoRgpResponseExtension(
            ResponseExtension.UPDATE);

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        registerExtension(restoreExtension);
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }
    }

    public List<RgpStatus> getRgpStatuses() {
        return restoreExtension.getRgpStatuses();
    }
}
