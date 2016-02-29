package com.ausregistry.jtoolkit2.session;

/**
 * This factory provides instances of Session implementations.  The default
 * Session implementation is com.ausregistry.jtoolkit2.session.TLSSession.
 * Alternative implementations may be loaded as described in the newInstance
 * descriptions.
 */
public final class SessionFactory {

    private SessionFactory() {
        // intentionally do nothing, required by checkstyle
    }

    /**
     * Create a new Session instance.  The implementation defaults to
     * TLSSession but may be overridden by setting the system property
     * com.ausregistry.jtoolkit2.session.class to the full name of the
     * alternative class.
     */
    public static Session newInstance(SessionProperties props)
        throws SessionConfigurationException {

        String sessionClass = System.getProperty(
                "com.ausregistry.jtoolkit2.session.class");
        if (sessionClass != null) {
            return newInstance(props, sessionClass);
        }

        return new TLSSession(props);
    }

    /**
     * Create a new Session instance.  The implementation actually loaded is
     * determined by the class name provided.
     */
    public static Session newInstance(SessionProperties p, String className)
        throws FactoryConfigurationError, SessionConfigurationException {

        Session instance = null;

        try {
            Class providerClass = getProviderClass(className);

            instance = (Session) providerClass.newInstance();
        } catch (ClassNotFoundException cnfe) {
            throw new FactoryConfigurationError(
                    "Class " + className + " not found", cnfe);
        } catch (Exception e) {
            throw new FactoryConfigurationError(
                    "Class " + className + " could not be instantiated: "
                    + e);
        }

        instance.configure(p);
        return instance;
    }

    private static Class getProviderClass(String className)
        throws ClassNotFoundException {

        ClassLoader cl = SessionFactory.class.getClassLoader();
        Class c = null;

        if (cl == null) {
            throw new ClassNotFoundException();
        }

        try {
            c = cl.loadClass(className);
        } catch (ClassNotFoundException cnfe) {
            cl = ClassLoader.getSystemClassLoader();
            c = cl.loadClass(className);
        }

        return c;
    }
}

