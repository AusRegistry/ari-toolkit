package com.ausregistry.jtoolkit2.se.idn;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * Use this to set IDN Domain extension properties for an EPP Domain Create
 * command.
 * 
 * @see DomainInfoIdnResponseExtension
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
