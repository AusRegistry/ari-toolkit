package com.ausregistry.jtoolkit2.se.secdns;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Models the &lt;maxSigLife&gt; element as documented in RFC5910.
 */
public class MaxSigLifeType {

    private int maxSigLife;

    /**
     * Returns the max sig life.
     *
     * @return the max sig life
     */
    public final int getMaxSigLife() {
        return maxSigLife;
    }

    /**
     * Sets the max sig life and validates the value is a positive number.
     *
     * @param maxSigLifeArg the new max sig life
     * @throws IllegalArgumentException if {@code maxSigLifeArg} is less than one.
     */
    public final void setMaxSigLife(int maxSigLifeArg) {
        if (maxSigLifeArg < 1) {
            throw new IllegalArgumentException();
        }

        this.maxSigLife = maxSigLifeArg;
    }

    /**
     * Creates the XML element.
     *
     * @param xmlWriter the XML writer
     * @param changeElement the change element
     */
    public void createXMLElement(final XMLWriter xmlWriter, final Element changeElement) {
        SecDNSXMLUtil.appendChildElement(xmlWriter, changeElement, "maxSigLife", maxSigLife);
    }

}
