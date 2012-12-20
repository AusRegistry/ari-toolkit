#ifndef __SYSTEM_PROPERTIES_HPP
#define __SYSTEM_PROPERTIES_HPP

#include "common/Properties.hpp"

/**
 * Provides a simple interface to a system properties file.
 */
class SystemProperties
{
public:

	/// Initialise the SystemProperties from the the given configuration file.
	/// @param configFile The path to the configuration file.
	static void init(const std::string& configFile);

	/// Get a single property value with the given name.
	/// @param prop The property to retrieve.
	static std::string getProperty(const std::string& prop);

	/// Get a single property value with the given name.
	/// @param prop The property to retrieve.
	static std::string getProperty(const std::string& prop, const std::string& def);


	/// Get a set of proerties based upon a key prefix.
	/// @param prop The property prefix.
	/// @returns The matching property key and value pairs, or an empty array
	///  if no key matched.
	static std::vector<std::pair<std::string,std::string> >
		getProperties(const std::string& prefix);


	/// Get a boolean property.  i.e. One that has a value of either "TRUE" or "FALSE"
	/// @param prop The property.
	/// @return The sense of the property.
	static bool getBooleanProperty(const std::string& prop, bool def);

private:
	// Must not be instantiated.
	SystemProperties();
};

#endif // __SYSTEM_PROPERTIES_HPP
