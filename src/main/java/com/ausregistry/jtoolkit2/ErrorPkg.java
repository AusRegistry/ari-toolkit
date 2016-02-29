package com.ausregistry.jtoolkit2;

import javax.xml.bind.DatatypeConverter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * A flexible library message reporting facility which supports customisation
 * of messages using a plain text messages file.  See the description of init
 * for usage information.
 *
 * Uses the debug, support and user level loggers.
 */
public final class ErrorPkg {
    private static Properties properties;
    private static String pname;

    static {
        try {
            init();
        } catch (Exception e) {
            Logger.getLogger(pname + ".user").severe(
                    "Fatal error: failed to load error messages file");
            throw new ConfigurationError(e);
        }
    }

    private ErrorPkg() {
        // intentionally do nothing, make checkstyle happy
    }

    /**
     * Initialise the ErrorPkg class from a messages file.  This is
     * automatically called at the time the class is loaded.  Ths messages
     * definition file must be in the classpath.
     *
     * @throws IOException The messages couldn't be loaded from a message file
     * into the internal Properties object.
     *
     * @throws FileNotFoundException The messages file couldn't be found.
     */
    public static void init() throws IOException, FileNotFoundException {

        pname = ErrorPkg.class.getPackage().getName();

        String filename = findMessageFile();
        Logger.getLogger(pname + ".debug").fine(
                "Loading messages file: " + filename);

        properties = new Properties();
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                    filename);
            if (in == null) {
                throw new FileNotFoundException(filename);
            }

            properties.load(in);
            Logger.getLogger(pname + ".user").fine(
                    "Loaded messages file: " + filename);
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * Get the message for a given message identifier/name.
     *
     * @param name The identifier of a message, as specified as a key in the
     * messages file.
     *
     * @return The value in the loaded messages file corresponding to the key
     * identified by the <code>name</code> parameter.
     */
    public static String getMessage(String name) {
        if (properties == null) {
            try {
                ErrorPkg.init();
            } catch (Exception e) {
                Logger.getLogger(pname + ".user").severe(
                        "Fatal error: failed to load error messages file");
                throw new ConfigurationError(e);
            }
        }
        return properties.getProperty(name);
    }

    /**
     * Get the message for a given message identifier/name, replacing the
     * specified token identified by <code>arg</code> with the value specified
     * by <code>val</code>.
     *
     * @param name The identifier of a message, as specified as a key in the
     * messages file.
     *
     * @param arg The token identifier to replace.
     *
     * @param val The value to replace the token identifier with.
     *
     * @return The value in the loaded messages file corresponding to the key
     * identified by the <code>name</code> parameter, with the token name
     * replaced by the given value.
     */
    public static String getMessage(String name, String arg, String val) {
        String msg = getMessage(name);

        Logger.getLogger(pname + ".debug").finer(name + ": " + arg + "=" + val);

        if (msg == null) {
            Logger.getLogger(pname + ".support").warning(
                    "Message definition not found for name: " + name);
            return null;
        }

        return msg.replace(arg, val);
    }

    /**
     * Get the message for a given message identifier/name, replacing each
     * token specified in <code>args</code> with the corresponding value
     * specified in <code>vals</code>.
     *
     * @param name The identifier of a message, as specified as a key in the
     * messages file.
     * @param args The array of token identifiers to replace.
     * @param vals The array of values to replace the token identifiers with.
     * @return The value in the loaded messages file corresponding to the key
     * identified by the <code>name</code> parameter, with the token
     * identifiers replaced by the given value.
     */
    public static String getMessage(String name, String[] args, String[] vals) {

        String msg = getMessage(name);

        if (msg == null) {
            Logger.getLogger(pname + ".support").warning(
                    "Message definition not found for name: " + name);
            return null;
        }

        for (int i = 0; i < args.length && i < vals.length; i++) {
            Logger.getLogger(pname + ".debug").finer("replace: " + args[i]);
            Logger.getLogger(pname + ".debug").finer("with:    " + vals[i]);
            msg = msg.replace(args[i], vals[i]);
        }

        return msg;
    }

    /**
     * Returns the message.
     *
     * @param name The identifier of a message, as specified as a key in the
     * messages file.
     * @param args The array of token identifiers to replace.
     * @param vals The array of values to replace the token identifiers with.
     * @return The value in the loaded messages file corresponding to the key
     * identified by the <code>name</code> parameter, with the token
     * identifiers replaced by the given value.
     */
    public static String getMessage(String name, String[] args, int[] vals) {
        String[] strVals = new String[args.length];

        for (int i = 0; i < vals.length; i++) {
            strVals[i] = String.valueOf(vals[i]);
        }

        return getMessage(name, args, strVals);
    }

    private static String findMessageFile() {
        return "messages.properties";
    }

    public static String getMessage(String name, String arg, Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);

        return getMessage(name, arg, DatatypeConverter.printDateTime(calendar));
    }

    public static String getMessage(String name, String arg, X509Certificate certificate) {
        StringBuilder messageBuilder = new StringBuilder("Certificate");
        messageBuilder.append(" of serial number '");
        messageBuilder.append(certificate.getSerialNumber());
        messageBuilder.append("'");
        String commonName = certificate.getSubjectDN().getName();
        if (commonName != null) {
            messageBuilder.append(" and DN '");
            messageBuilder.append(commonName);
            messageBuilder.append("'");
        }
        String signerCommonName = certificate.getIssuerDN().getName();
        if (signerCommonName != null) {
            messageBuilder.append(" and \nsigner DN '");
            messageBuilder.append(signerCommonName);
            messageBuilder.append("'\n");
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Date notBeforeDate = certificate.getNotBefore();
        if (notBeforeDate != null) {
            calendar.setTime(notBeforeDate);
            messageBuilder.append("valid from '");
            messageBuilder.append(DatatypeConverter.printDateTime(calendar));
            messageBuilder.append("'");
        }

        Date notAfterDate = certificate.getNotAfter();
        if (notAfterDate != null) {
            calendar.setTime(notAfterDate);
            messageBuilder.append(" and valid to '");
            messageBuilder.append(DatatypeConverter.printDateTime(calendar));
            messageBuilder.append("'");
        }
        return getMessage(name, arg, messageBuilder.toString());
    }
}
