package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * Use this to set IDNA Domain extension properties for an EPP Domain Create
 * command.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainIdnaResponseExtension
 */
public final class DomainCreateIdnaCommandExtension implements CommandExtension {
    private static final long serialVersionUID = -8945007354471832288L;
    private String userForm;
    private String language;

    /**
     * @throws IllegalArgumentException if {@code userForm} or {@code language} are {@code null} or empty.
     */
    public DomainCreateIdnaCommandExtension(final String userForm, final String language) {
        setUserForm(userForm);
        setLanguage(language);
    }

    public void addToCommand(final Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element createElement =
        xmlWriter.appendChild(extensionElement, "create", ExtendedObjectType.IDNA_DOMAIN.getURI());
        xmlWriter.appendChild(createElement, "userForm", userForm, "language", language);
    }

    private void setUserForm(final String userForm) {
        if (userForm == null || userForm.isEmpty()) {
            throw new IllegalArgumentException("User form must not be null or empty");
        }
        this.userForm = userForm;
    }

    private void setLanguage(final String language) {
        if (language == null || language.isEmpty()) {
            throw new IllegalArgumentException("Language must not be null or empty");
        }
        this.language = language;
    }
}
