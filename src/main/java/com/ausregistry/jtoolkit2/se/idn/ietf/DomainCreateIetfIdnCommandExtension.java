package com.ausregistry.jtoolkit2.se.idn.ietf;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Create command, representing the Create IDN Domain aspect of the
 * Internationalized Domain Names extension.</p>
 *
 * <p>Use this to set the table element to be used for an IDN as part of an EPP Domain Create command
 * compliant with RFC3735 and RFC5891. The response expected from a server should be
 * handled by a Domain Create Response.</p>
 *
 * @see DomainCreateCommand
 * @see DomainCreateResponse
 * @see <a href="https://tools.ietf.org/html/draft-obispo-epp-idn-04">Internationalized Domain Name
 * Mapping Extension for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCreateIetfIdnCommandExtension implements CommandExtension {

    private final String table;
    private final String uname;

    /**
     * @param table The IDN language. Required.
     * @param uname the domain name in Unicode format, optional parameter.
     * @throws IllegalArgumentException if {@code table} is {@code null} or empty.
     */
    public DomainCreateIetfIdnCommandExtension(String table, String uname) {
        if (table == null || table.isEmpty()) {
            throw new IllegalArgumentException(ErrorPkg.getMessage("se.ar.domain.create.ietf.idn.table"));
        }
        this.table = table;
        this.uname = uname;
    }

    @Override
    public void addToCommand(Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element dataElement = xmlWriter.appendChild(extensionElement,
                "data", ExtendedObjectType.IETF_IDN.getURI());
        xmlWriter.appendChild(dataElement, "table").setTextContent(table);
        if (uname != null) {
          xmlWriter.appendChild(dataElement, "uname").setTextContent(uname);
        }
    }
}
