package com.ausregistry.jtoolkit2.se;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * This class models the period element specified in RFC5731, used to specify
 * domain validity periods.
 */
public class Period implements java.io.Serializable {
    private static final long serialVersionUID = -691482115228230089L;

    private final PeriodUnit unit;
    private final int period;

    /**
     * A validity period specified in the default unit, years.
     */
    public Period(int period) {
        this(PeriodUnit.YEARS, period);
    }

    /**
     * A validity period specified in the given unit.
     */
    public Period(PeriodUnit unit, int period) {
        this.unit = unit;
        this.period = period;
    }

    /**
     * Append an XML representation of this Period instance to the list of
     * child elements of the given parent Element.  The XMLWriter is used to
     * insert the new Element into the DOM tree in which the parent resides.
     *
     * @param xmlWriter The XMLWriter that maintains the DOM tree.  The work
     * of creating and inserting the new element is delegated to this object.
     *
     * @param parent The Element which will parent the new element.
     */
    public Element appendPeriod(XMLWriter xmlWriter, Element parent) {
        Element retval = xmlWriter.appendChild(
                parent,
                "period",
                new String[] {"unit"},
                new String[] {unit.toString()});
        retval.setTextContent(String.valueOf(period));

        return retval;
    }

    /**
     * @return period unit for the period
     */
    public PeriodUnit getUnit() {
        return unit;
    }

    /**
     * @return period value for the period
     */
    public int getPeriod() {
        return period;
    }
}

