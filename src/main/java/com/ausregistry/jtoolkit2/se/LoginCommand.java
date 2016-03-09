package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;

import org.w3c.dom.Element;

/**
 * Use this to open an EPP session in order to perform commands only permitted
 * from within the context of a session.  Instances of this class generate, via
 * the toXML method, login service elements compliant with the login
 * specification in RFC5730.
 *
 * @see com.ausregistry.jtoolkit2.se.Greeting For services available to be used
 * in the login command on the chosen EPP server.
 *
 * @see com.ausregistry.jtoolkit2.se.LogoutCommand To end a session opened
 * using LoginCommand.
 */
public final class LoginCommand extends Command {
    private static final long serialVersionUID = -704642499677315944L;

    private static final String DEFAULT_VERSION = "1.0";
    private static final String DEFAULT_LANG = "en";

    public LoginCommand(String clID, String password) {
        this(clID, password, StandardObjectType.getStandardURIs(), null);
    }

    public LoginCommand(String clID, String password, String newPW) {
        this(clID, password, newPW, DEFAULT_VERSION, DEFAULT_LANG,
                StandardObjectType.getStandardURIs(), null);
    }

    public LoginCommand(String clID, String password,
            String[] objURIs, String[] extURIs) {
        this(clID, password, DEFAULT_VERSION, DEFAULT_LANG, objURIs, extURIs);
    }

    public LoginCommand(String clID, String password,
            String version, String lang,
            String[] objURIs, String[] extURIs) {
        this(clID, password, null, version, lang, objURIs, extURIs);
    }

    /**
     *
     * @param clID Required.
     * @param password Required.
     * @param newPassword Optional.
     * @param version Required.
     * @param lang
     * @param objURIs Required.
     * @param extURIs Optional.
     * @throws IllegalArgumentException if {@code clID}, {@code password} or {@code version} are {@code null}.
     */
    public LoginCommand(String clID, String password, String newPassword,
            String version, String lang,
            String[] objURIs, String[] extURIs) {
        super(StandardCommandType.LOGIN);

        if (clID == null || password == null || version == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.login.missing_arg"));
        }

        xmlWriter.appendChild(cmdElement, "clID").setTextContent(clID);
        xmlWriter.appendChild(cmdElement, "pw").setTextContent(password);
        if (newPassword != null) {
            xmlWriter.appendChild(
                    cmdElement, "newPW").setTextContent(newPassword);
        }
        Element options = xmlWriter.appendChild(cmdElement, "options");
        xmlWriter.appendChild(options, "version").setTextContent(version);
        xmlWriter.appendChild(options, "lang").setTextContent(lang);
        Element svcs = xmlWriter.appendChild(cmdElement, "svcs");
        for (String objURI : objURIs) {
            xmlWriter.appendChild(svcs, "objURI").setTextContent(objURI);
        }

        if (extURIs == null || extURIs.length == 0) {
            return;
        }

        Element svcExtension = xmlWriter.appendChild(svcs, "svcExtension");
        for (String extURI : extURIs) {
            xmlWriter.appendChild(
                    svcExtension, "extURI").setTextContent(extURI);
        }
    }
}
