package com.ausregistry.jtoolkit2.se.rgp;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.StandardObjectType;
import com.ausregistry.jtoolkit2.se.UpdateCommand;
import org.w3c.dom.Element;

/**
 * <p>Representation of the EPP Domain Update command with the Domain Restore aspect of the Registry Grace Period
 * extension.</p>
 *
 * <p>Use this to post a Domain Restoration Request for restoring a domain from a "redemption period" RGP state
 * as part of an EPP Domain Update command compliant with RFC5730, RFC5731 and RFC3915. The response expected
 * from a server should be handled by a Domain Restore Response.</p>
 *
 * @see DomainRestoreResponse
 * @see <a href="http://tools.ietf.org/html/rfc3915">Domain Registry Grace Period Mapping for the
 * Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainRestoreRequestCommand extends UpdateCommand {

    private static final long serialVersionUID = 2409916920503111390L;

    /**
     * <p>Constructor taking in a domain name for the restore request.</p>
     */
    public DomainRestoreRequestCommand(String name) {
        super(StandardObjectType.DOMAIN, name);
        xmlWriter.appendChild(objElement, "chg");

        extension = xmlWriter.appendChild(command, "extension");

        final Element updateElement = xmlWriter.appendChild(extension,
                "update", ExtendedObjectType.RESTORE.getURI());

        xmlWriter.appendChild(updateElement, "restore", "op", "request");
    }
}
