package com.ausregistry.jtoolkit2.xml;

import java.util.logging.Logger;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.session.FactoryConfigurationError;

/**
 * Provides an implementation of DefaultHandler.  The default {@link
 * org.xml.sax.helpers.DefaultHandler} simply raises exceptions reported in
 * parsing and validation warnings and errors to the application after logging
 * them using the customer logging handler.
 *
 * Uses the support and user level loggers.
 */
public final class HandlerFactory {
    private static final String[] ERRMSG_ARR = new String[] {
        "<<line>>", "<<column>>", "<<message>>"
    };

    private static String pname;

    static {
        pname = HandlerFactory.class.getPackage().getName();
    }

    private HandlerFactory() {
        // intentionally do nothing, required by checkstyle
    }

    /**
     * Create a new DefaultHandler instance.  The implementation defaults to
     * org.xml.sax.helpers.DefaultHandler but may be overridden by setting
     * the system property com.ausregistry.jtoolkit2.errhandler.class to the
     * full name of the alternative class.
     */
    public static DefaultHandler newInstance()
        throws FactoryConfigurationError {

        String handlerClass = System.getProperty(
                "com.ausregistry.jtoolkit2.errhandler.class");

        if (handlerClass != null) {
            return newInstance(handlerClass);
        }

        return new DefaultHandler() {
            @Override
            public void error(SAXParseException saxpe)
                throws SAXException {

                userLogParseException(saxpe.getLineNumber(),
                        saxpe.getColumnNumber(), saxpe.getMessage());

                throw saxpe;
            }

            @Override
            public void warning(SAXParseException saxpe)
                throws SAXException {

                throw saxpe;
            }

            @Override
            public void fatalError(SAXParseException saxpe)
                throws SAXException {

                userLogParseException(saxpe.getLineNumber(),
                        saxpe.getColumnNumber(), saxpe.getMessage());

                Logger.getLogger(pname + ".support").warning(
                        saxpe.getMessage());

                throw saxpe;
            }

            private void userLogParseException(int line, int col, String msg) {
                Logger.getLogger(pname + ".user").warning(ErrorPkg.getMessage(
                            "xml.validation.error",
                            ERRMSG_ARR,
                            new String[] {
                                String.valueOf(line),
                                String.valueOf(col),
                                msg
                            }));
            }
        };
    }

    /**
     * Create a new DefaultHandler instance.  The implementation actually
     * loaded is determined by the class name provided.
     */
    public static DefaultHandler newInstance(String className)
        throws FactoryConfigurationError {

        DefaultHandler instance = null;

        try {
            Class providerClass = getProviderClass(className);

            instance = (DefaultHandler) providerClass.newInstance();
        } catch (ClassNotFoundException cnfe) {
            throw new FactoryConfigurationError(
                    "Class " + className + " not found", cnfe);
        } catch (Exception e) {
            throw new FactoryConfigurationError(
                    "Class " + className + " could not be instantiated: "
                    + e);
        }

        return instance;
    }

    private static Class getProviderClass(String className)
        throws ClassNotFoundException {

        ClassLoader cl = ClassLoader.getSystemClassLoader();
        if (cl == null) {
            throw new ClassNotFoundException();
        } else {
            Class c = cl.loadClass(className);
            return c;
        }
    }
}

