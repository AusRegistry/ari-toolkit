#include "common/Properties.hpp"
#include "config/boolean.h"
#include <sstream>
#include <cstdlib>

using namespace std;

Properties::Properties(const string &filename) 
	throw (PropertyConfigException)
	: theConfig(NULL), _initFailed(false)
{
	load(filename);
}

Properties::~Properties()
{
	if (theConfig)
	{
		config_destroy(theConfig);
	}
}

void Properties::load(const string& filename) 
		throw (PropertyConfigException)
{
	if (theConfig)
	{
		config_destroy(theConfig);
		theConfig = NULL;
	}
	
	if ((theConfig = config_open(filename.c_str())) == NULL)
	{
		_initFailed = true;
		throw PropertyConfigException("Properties::load: failed to open '" + filename + "'.");
	}
	_initFailed = false;
}

void Properties::store(const string &filename) const
		throw (PropertyConfigException, PropertyIoException)
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");

	if (config_save(theConfig, filename.c_str()) == FALSE)
		throw PropertyIoException("Could not write properties to file '" + filename + "'.");
}

string Properties::getProperty(const string &prop) const
		throw (PropertyNotFoundException, PropertyConfigException)
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");

	char *value = config_get_str(theConfig, prop.c_str());

	if (value)
	{
		string str_value (value);
		free(value);
		return str_value;
	}
	else
		throw PropertyNotFoundException(prop);
}

string Properties::getProperty(const string &prop, const string &def) const
	throw (PropertyConfigException)
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");
	try
	{
		return getProperty(prop);
	}
	catch (PropertyNotFoundException&)
	{
		return def;
	}
}

vector<pair<string,string> >
Properties::getProperties(const string &prop_prefix) const
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");
	vector<pair<string,string> > result;
	config_iter_t *i = config_iter_create(theConfig,prop_prefix.c_str());
	while(i->key!=NULL)
	{
		result.push_back(make_pair(i->key,i->value));
		config_iter_next(i);
	}
	config_iter_destroy(i);

	return result;
}
	

void Properties::setProperty(const string &prop,
							 const string &value)
		throw (PropertyConfigException, PropertyIoException)
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");
	if (config_put_str (theConfig, prop.c_str(), value.c_str()) == NULL)
		throw PropertyIoException("Could not set property '" + prop + "' to value '" + value + "'.");
}


bool Properties::getBooleanProperty(const string &prop) const
		throw (PropertyConfigException, PropertyNotFoundException)
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");
	int value;
	if (config_get_bool (theConfig, prop.c_str(), &value) == FALSE)
		throw PropertyNotFoundException(prop);

	return (value == TRUE ? true : false);
}

bool Properties::getBooleanProperty (const string &prop,
								     bool defaultValue) const
	throw (PropertyConfigException)
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");
	try
	{
		return getBooleanProperty(prop);
	}
	catch (PropertyNotFoundException)
	{
		return defaultValue;
	}
}


int Properties::getIntProperty (const string &prop) const
		throw (PropertyNotFoundException, PropertyConfigException)
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");
	int value;
	if (config_get_int(theConfig, prop.c_str(), &value) == FALSE)
		throw PropertyNotFoundException(prop);

	return value;
}


int Properties::getIntProperty(const string &prop,
							   int defaultValue) const
	throw (PropertyConfigException)
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");
	try
	{
		return getIntProperty(prop);
	}
	catch (PropertyNotFoundException)
	{
		return defaultValue;
	}
}


long Properties::getLongProperty (const string &prop) const
		throw (PropertyConfigException, PropertyNotFoundException)
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");
	long value;
	if (config_get_long (theConfig, prop.c_str(), &value) == FALSE)
		throw PropertyNotFoundException(prop);

	return value;
}


long Properties::getLongProperty (const string &prop,
								  long defaultValue) const
	throw (PropertyConfigException)
{
	if (theConfig == NULL)
		throw PropertyConfigException("Properties are not loaded.");
	try
	{
		return getLongProperty (prop);
	}
	catch (PropertyNotFoundException)
	{
		return defaultValue;
	}
}

#if 0
void Properties::splitString (const string &in,
							  char delim,
							  vector<string> &out)
{
	out.clear();

	istringstream iss(in);
	string token;
	while (getline (iss, token, delim))
		out.push_back (token);
}
#endif
