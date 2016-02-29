package com.ausregistry.jtoolkit2.se;

import java.io.Serializable;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Implementors of this interface provide a mechanism for building the part of
 * the service element DOM tree that they represent.  This should only be
 * implemented by developers wishing to extend the command / response framework
 * of EPP.
 */
public interface Appendable extends Serializable {
    /**
     * Used internally for building a DOM representation of a service element.
     * This really should not be exposed to the end user, but Java has no
     * package-visible interface type.
     */
    Element appendToElement(XMLWriter xmlWriter, Element parent);
}

