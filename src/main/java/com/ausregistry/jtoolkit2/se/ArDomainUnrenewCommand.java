package com.ausregistry.jtoolkit2.se;

import java.util.GregorianCalendar;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Mapping of EPP urn:ar:params:xml:ns:arext-1.0 domainUnrenew command
 * specified by the AusRegistry EPP extensions document.
 * Use this class to generate an AusRegistry-compliant XML document, given
 * simple input parameters.  The toXML method in Command serialises this object
 * to XML.
 *
 * @see ArDomainUnrenewResponse
 */
public class ArDomainUnrenewCommand extends ProtocolExtensionCommand {
    private static final long serialVersionUID = -3723213074751854975L;

    private static final CommandType UNRENEW_COMMAND_TYPE = new ArUnrenewCommandType();

    /**
     * @throws IllegalArgumentException if {@code name} or {@code exDaten} is {@code null}.
     */
    public ArDomainUnrenewCommand(String name, GregorianCalendar exDate) {
        super(UNRENEW_COMMAND_TYPE, ExtendedObjectType.AR_DOMAIN, name);

        if (exDate == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.ar.unrenew.missing_arg"));
        }

        String exDateStr = EPPDateFormatter.toXSDate(exDate);
        xmlWriter.appendChild(objElement, "curExpDate").setTextContent(
                exDateStr);
    }

    @Override
    protected Extension getExtension() {
        return ExtensionImpl.AR;
    }
}

