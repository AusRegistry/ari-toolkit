#include "common/SystemProperties.hpp"
#include <iostream>

using namespace std;

namespace {
	Properties properties;
}

void SystemProperties::init(const string& file)
{
	//static string name = "com.ausregistry.cpptoolkit";
	properties.load(file);
}

string SystemProperties::getProperty(const string &prop)
{
	return properties.getProperty(prop);
}


string SystemProperties::getProperty(const string& prop, const string& def)
{
	return properties.getProperty(prop, def);
}

vector<pair<string,string> > SystemProperties::getProperties(const string& prefix)
{
	return properties.getProperties(prefix);
}

bool SystemProperties::getBooleanProperty(const string& prop, bool def)
{
	return properties.getBooleanProperty(prop, def);
}

