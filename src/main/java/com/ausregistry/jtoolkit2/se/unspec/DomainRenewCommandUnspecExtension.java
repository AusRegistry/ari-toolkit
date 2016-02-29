package com.ausregistry.jtoolkit2.se.unspec;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Renew command, representing the Restore aspects of the EPP.</p>
 *
 * <p>Use this to command to restore a domain name in Redemption. The response expected from a
 * server should be handled by a Domain Renew Response.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewCommand
 */
public class DomainRenewCommandUnspecExtension implements CommandExtension {
    private final RestoreReasonCode restoreReasonCode;
    private final String restoreComment;
    private final boolean trueData;
    private final boolean validUse;

    public DomainRenewCommandUnspecExtension(RestoreReasonCode restoreReasonCode, String restoreComment,
                                             boolean trueData, boolean validUse) {
        this.restoreReasonCode = restoreReasonCode;
        this.restoreComment = restoreComment;
        this.trueData = trueData;
        this.validUse = validUse;
    }

    @Override
    public void addToCommand(Command command) {
            final XMLWriter xmlWriter = command.getXmlWriter();
            final Element extensionElement = command.getExtensionElement();
            final Element renewElement = xmlWriter.appendChild(extensionElement, "extension",
                    ExtendedObjectType.UNSPEC.getURI());

            xmlWriter.appendChild(renewElement, "unspec", ExtendedObjectType.UNSPEC.getURI()).setTextContent(
                    "RestoreReasonCode=" + restoreReasonCode.getValue()
                            + " RestoreComment=" + restoreComment
                            + " TrueData=" + (trueData ? "Y" : "N")
                            + " ValidUse=" + (validUse ? "Y" : "N"));
    }
}
