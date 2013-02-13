package com.ausregistry.jtoolkit2.se.secdns;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Models the &lt;chg&gt; element as documented in RFC5910.
 */
public class ChgType {

    private MaxSigLifeType maxSigLife;

    /**
     * Creates the XML element.
     *
     * @param xmlWriter the XML writer
     * @param changeElement the change element
     */
    public void createXMLElement(final XMLWriter xmlWriter, final Element changeElement) {
        if (maxSigLife != null) {
            maxSigLife.createXMLElement(xmlWriter, changeElement);
        }
    }

    /**
     * Returns the max sig life.
     *
     * @return the max sig life
     */
    public final MaxSigLifeType getMaxSigLife() {
        return maxSigLife;
    }

    /**
     * Sets the max sig life.
     *
     * @param maxSigLifeArg the new max sig life
     */
    public final void setMaxSigLife(MaxSigLifeType maxSigLifeArg) {
        this.maxSigLife = maxSigLifeArg;
    }

}
