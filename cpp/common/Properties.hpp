#ifndef __PROPERTIES_HPP
#define __PROPERTIES_HPP

#include "config/config.h"
#include "common/EPPException.hpp"

#include <stdio.h>
#include <string>
#include <vector>


/// This is throw when a Properties instance can not be loaded or saved
// or when a property is accessed prior to the instance being initialised.
class PropertyConfigException : public EPPException
{
public:
	PropertyConfigException()
		: EPPException("Property configuration error.")
	{ }
	PropertyConfigException(const std::string &msg)
		: EPPException(msg) { }
	EPP_EXCEPTION(PropertyConfigException);
};

/// Indicates that the request property name can not be found.
class PropertyNotFoundException : public EPPException
{
public:
	PropertyNotFoundException(const std::string &property)
		: EPPException("Property not found: " + property) { }
	EPP_EXCEPTION(PropertyNotFoundException);
};

/// Thrown when a Properties file encounters an error when writing changes
// to the underlying property file.
class PropertyIoException : public EPPException
{
public:
	PropertyIoException(const std::string &msg)
		: EPPException(msg) { }
	EPP_EXCEPTION(PropertyIoException);
};

/**
 * Instances of these class manage a file containing 'name=value' lines.
 * A number of function are available with can allow the value to be
 * transformed into a specific type, or for a default value to be returned
 * if the 'name' can not be found (or of the value is malformed).
 */
class Properties
{
public:

	Properties() : theConfig(NULL), _initFailed(false) { };
	Properties(const std::string &filename) throw (PropertyConfigException);
	~Properties();

	bool isInitialised() const { return (theConfig != NULL); }
	bool initFailed() const { return _initFailed; }

	/// This must be called prior to accessing, setting or storing properties.
	void load(const std::string &filename) throw (PropertyConfigException);

	void store(const std::string &filename) const 
			throw (PropertyConfigException, PropertyIoException);

	std::string getProperty(const std::string &prop) const
			throw (PropertyNotFoundException, PropertyConfigException);

	std::string getProperty(const std::string &prop, const std::string& def) const
			throw (PropertyConfigException);

	std::vector<std::pair<std::string, std::string> >
	getProperties(const std::string& prop_prefix) const;

	bool getBooleanProperty(const std::string &prop) const
			throw (PropertyConfigException, PropertyNotFoundException);

	bool getBooleanProperty(const std::string &prop,
							bool defaultValue) const
			throw (PropertyConfigException);

	int getIntProperty(const std::string &prop) const
			throw (PropertyConfigException, PropertyNotFoundException);

	int getIntProperty(const std::string &prop,
						int defaultValue) const
			throw (PropertyConfigException);

	long getLongProperty(const std::string &prop) const
			throw (PropertyNotFoundException, PropertyConfigException);

	long getLongProperty(const std::string &prop,
						 long defaultValue) const
			throw (PropertyConfigException);

	void setProperty(const std::string &prop,
					 const std::string &value)
			throw (PropertyIoException, PropertyConfigException);

private:
	config_t *theConfig;
	bool _initFailed;

	static void splitString(const std::string &in,
					         char delim,
							 std::vector<std::string> & out);
};

#endif // __PROPERTIES_HPP
