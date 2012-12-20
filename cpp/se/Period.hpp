#ifndef __PERIOD_HPP
#define __PERIOD_HPP

#include "se/PeriodUnit.hpp"

#include <xercesc/dom/DOMElement.hpp>

class XMLWriter;

/**
 * This class models the period element specified in RFC3731, used to specify
 * domain validity periods.
 */
class Period
{
public:
    /**
     * A validity period specified in the given unit (default years).
     */
    Period(int period, const PeriodUnit* unit = PeriodUnit::YEARS())
        : unit(unit), period(period)
	{ }

    /**
     * Append an xml representation of this Period instance to the list of
     * child elements of the given parent Element.  The XMLWriter is used to
     * insert the new Element into the DOM tree in which the parent resides.
     *
     * @param xmlWriter The XMLWriter that maintains the DOM tree.  The work
     * of creating and inserting the new element is delegated to this object.
     *
     * @param parent The Element which will parent the new element.
     */
    xercesc::DOMElement* appendPeriod(
		XMLWriter *xmlWriter, xercesc::DOMElement *parent) const;

private:
    const PeriodUnit* unit;
    int period;
};

#endif // __PERIOD_HPP
