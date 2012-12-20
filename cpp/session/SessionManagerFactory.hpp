#ifndef __SESSION_MANAGER_FACTORY_HPP
#define __SESSION_MANAGER_FACTORY_HPP

#include "session/ConfigurationException.hpp"
#include "session/EPPIOException.hpp"

#include "session/SessionManagerProperties.hpp"
#include "session/SessionManager.hpp"

/**
 * This factory provides instances of SessionManager implementations.  The
 * default SessionManager implementation is
 * com.ausregistry.jtoolkit2.session.SessionManagerImpl.  Alternative
 * implementations may be loaded as described in the newInstance
 * descriptions.
 */
class SessionManagerFactory
{
public:

#if 0
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
	static SessionManager* newInstance(const std::string &propertiesFile)
			throw (ConfigurationException, EPPIOException);
#endif

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
	 *
	 * Upon success, assumes ownership of props.
	 */
	static SessionManager* newInstance(SessionManagerProperties* props)
			throw (ConfigurationException);

#if 0
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
	 *
	 * Upon success, assumes ownership of props.
	 */
	static SessionManager * newInstance (SessionManagerProperties* props,
										 const std::string &className)
			// throw (FactoryConfigurationError, ConfigurationException);
			throw (ConfigurationException);
#endif
};

#endif // __SESSION_MANAGER_FACTORY_HPP
