package com.ausregistry.jtoolkit2.se.idn;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Create command, representing the Create IDN Domain aspect of the
 * Internationalized Domain Names extension.</p>
 *
 * <p>Use this to set the language tag to be used for an IDN as part of an EPP Domain Create command
 * compliant with RFC5730 and RFC5731. The response expected from a server should be
 * handled by a Domain Create Response.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.se.DomainCreateResponse
 * @see <a href="http://ausregistry.github.io/doc/idn-1.0/idn-1.0.html">Internationalized Domain Name Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public final class DomainCreateIdnCommandExtension implements CommandExtension {
    private static final long serialVersionUID = -8945007354471832288L;
    private String language;

    /**
     * @param language The IDN language. Required.
     * @throws IllegalArgumentException if {@code language} is {@code null} or empty.
     */
    public DomainCreateIdnCommandExtension(final String language) {
        setLanguage(language);
    }

    public void addToCommand(final Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement = xmlWriter.appendChild(extensionElement,
                "create", ExtendedObjectType.IDN.getURI());
        xmlWriter.appendChild(createElement, "languageTag").setTextContent(language);
    }

    private void setLanguage(final String language) {
        if (language == null || language.isEmpty()) {
            throw new IllegalArgumentException("Language must not be null or empty");
        }
        this.language = language;
    }
}
