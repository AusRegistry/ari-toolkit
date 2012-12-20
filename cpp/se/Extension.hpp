#ifndef __EXTENSION_H
#define __EXTENSION_H

#include <string>

/**
 * Represent features of EPP extensions of interest to the
 * toolkit library.  This is implemented as an abstract class
 * instead of interface because an interface can't declare
 * static (class) methods. 
 */ 
class Extension
{
public:
#ifndef SWIG
	virtual ~Extension(void) = 0;

    /**
     * Get the globally unique namespace URI which identifies this extension.
     */
    virtual std::string& getURI() const = 0;
    /**
     * Get the location hint for the XML schema used to validate EPP service
     * element instances using this extension.
     */
    virtual std::string& getSchemaLocation() const = 0;
#endif /* SWIG */
};

#ifndef SWIG
inline Extension::~Extension(void)
{
    return;
}
#endif /* SWIG */

#endif // __EXTENSION_H
