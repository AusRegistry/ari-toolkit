package com.ausregistry.jtoolkit2.session;

import java.io.IOException;

/**
 * This factory provides instances of SessionManager implementations.  The
 * default SessionManager implementation is
 * com.ausregistry.jtoolkit2.session.SessionManagerImpl.  Alternative
 * implementations may be loaded as described in the newInstance
 * descriptions.
 */
public final class SessionManagerFactory {

    private SessionManagerFactory() {
        // intentionally do nothing, required by checkstyle
    }

    /**
     * Create a new SessionManager instance.  The implementation defaults to
     * SessionManagerImpl but may be overridden by setting the system
     * property com.ausregistry.jtoolkit2.sessionManager.class to the full
     * name of the alternative class.
     *
     * @param propertiesFile The location of a properties file to be used to
     * configure the new SessionManager.  This method attempts to load a new
     * SessionManagerProperties object from this file before creating the
     * SessionManager.
     *
     * @throws IOException The properties object was unable to load properties
     * from the named file.  The exception may indicate the cause via
     * getCause().
     *
     * @throws ConfigurationException The SessionManager was not successfully
     * configured based on the properties loaded from the given file.  The
     * cause of the failure should be available via getCause().
     */
    public static SessionManager newInstance(String propertiesFile)
            throws ConfigurationException, IOException {

        SessionManagerProperties properties = new SessionManagerPropertiesImpl(
                propertiesFile);

        return newInstance(properties);
    }

    /**
     * Create a new SessionManager instance.  The implementation defaults to
     * SessionManagerImpl but may be overridden by setting the system
     * property com.ausregistry.jtoolkit2.sessionManager.class to the full
     * name of the alternative class.
     *
     * @param props SessionManager properties that have already been loaded.
     * This method assumes that the properties are already loaded and therefore
     * does not attempt to do so.
     *
     * @throws ConfigurationException The SessionManager was not successfully
     * configured based on the given properties.  The cause of the failure
     * should be available via getCause().
     */
    public static SessionManager newInstance(SessionManagerProperties props)
            throws ConfigurationException {

        String managerClass = System.getProperty("com.ausregistry.jtoolkit2.sessionManager.class");
        if (managerClass != null) {
            try {
                return newInstance(props, managerClass);
            } catch (Exception e) {
                // not guaranteed to have a logger or other better means to log this error.
                e.printStackTrace();
            }
        }

        return new SessionManagerImpl(props);
    }

    /**
     * Create a new SessionManager instance.  The implementation actually
     * loaded is determined by the class name provided.
     *
     * @param props SessionManager properties that have already been loaded.
     * This method assumes that the properties are already loaded and therefore
     * does not attempt to do so.
     *
     * @throws ConfigurationException The SessionManager was not successfully
     * configured based on the given properties.  The cause of the failure
     * should be available via getCause().
     */
    public static SessionManager newInstance(SessionManagerProperties props,
            String className) throws FactoryConfigurationError,
            ConfigurationException {

        SessionManager instance = null;

        try {
            Class providerClass = getProviderClass(className);

            instance = (SessionManager) providerClass.newInstance();
        } catch (ClassNotFoundException cnfe) {
            throw new FactoryConfigurationError("Class " + className
                    + " not found", cnfe);
        } catch (Exception e) {
            throw new FactoryConfigurationError("Class " + className
                    + " could not be instantiated: " + e);
        }

        instance.configure(props);
        return instance;
    }

    private static Class getProviderClass(String className)
        throws ClassNotFoundException {

        ClassLoader cl = SessionManagerFactory.class.getClassLoader();
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
