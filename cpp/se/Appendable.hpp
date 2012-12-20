#ifndef __APPENDABLE_HPP
#define __APPENDABLE_HPP

#include <xercesc/dom/DOMElement.hpp>
class XMLWriter;

/**
 * Implementors of this interface provide a mechanism for building the part of
 * the service element DOM tree that they represent.  This should only be
 * implemented by developers wishing to extend the command / response framework
 * of EPP.
 */
class Appendable
{
public:
	virtual ~Appendable(void) { }
	/**
	 * Used internally for building a DOM representation of a service element.
	 * This really should not be exposed to the end user, but Java has no
	 * package-visible interface type.
	 */
    virtual xercesc::DOMElement* appendToElement(
			XMLWriter* xmlWriter, 
			xercesc::DOMElement* parent) const = 0;
};

#endif // __APPENDABLE_HPP
