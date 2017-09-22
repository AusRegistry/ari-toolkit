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
 * <p>This extension is also used to specify the UIN for renew in the .travel zone.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewCommand
 */
public class DomainRenewCommandUnspecExtension implements CommandExtension {
    private RestoreReasonCode restoreReasonCode;
    private String restoreComment;
    private boolean trueData;
    private boolean validUse;
    private String uin;

    public DomainRenewCommandUnspecExtension(String uin) {
        this.uin = uin;
    }

    public DomainRenewCommandUnspecExtension(RestoreReasonCode restoreReasonCode, String restoreComment,
                                             boolean trueData, boolean validUse) {
        this.restoreReasonCode = restoreReasonCode;
        this.restoreComment = restoreComment;
        this.trueData = trueData;
        this.validUse = validUse;
    }

    @Override
    public void addToCommand(Command command) {
        if (restoreReasonCode != null) {
            addUnspecWithContent(command, "RestoreReasonCode=" + restoreReasonCode.getValue()
                    + " RestoreComment=" + restoreComment
                    + " TrueData=" + (trueData ? "Y" : "N")
                    + " ValidUse=" + (validUse ? "Y" : "N"));
        } else if (uin != null) {
            addUnspecWithContent(command, "UIN=" + uin);
        }
    }

    private void addUnspecWithContent(Command command, String content) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element renewElement = xmlWriter.appendChild(extensionElement, "extension",
                ExtendedObjectType.UNSPEC.getURI());

        xmlWriter.appendChild(renewElement, "unspec", ExtendedObjectType.UNSPEC.getURI()).setTextContent(
                content);
    }
}
