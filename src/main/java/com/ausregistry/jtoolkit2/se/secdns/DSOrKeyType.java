package com.ausregistry.jtoolkit2.se.secdns;

import java.util.ArrayList;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Models the &lt;add&gt;, &lt;infData&gt; and &lt;create&gt; elements as documented in RFC5910.
 */
public class DSOrKeyType {

    private ArrayList<DSData> dsDataList;
    private ArrayList<KeyData> keyDataList;
    private MaxSigLifeType maxSigLife;

    /**
     * Adds the to DS data to a list.
     *
     * @param dsData the DS data
     */
    public void addToDsData(final DSData dsData) {
        if (this.dsDataList == null) {
            this.dsDataList = new ArrayList<DSData>();
        }
        this.dsDataList.add(dsData);
    }

    /**
     * Adds the to key data to a list.
     *
     * @param keyData the key data
     */
    public void addToKeyData(final KeyData keyData) {
        if (this.keyDataList == null) {
            this.keyDataList = new ArrayList<KeyData>();
        }
        this.keyDataList.add(keyData);
    }

    /**
     * Returns the DS data list.
     *
     * @return the DS data list
     */
    public final ArrayList<DSData> getDsDataList() {
        return dsDataList;
    }

    /**
     * Sets the DS data list.
     *
     * @param dsDataList the new DS data list
     */
    public final void setDsDataList(ArrayList<DSData> dsDataList) {
        this.dsDataList = dsDataList;
    }

    /**
     * Returns the key data list.
     *
     * @return the key data list
     */
    public final ArrayList<KeyData> getKeyDataList() {
        return keyDataList;
    }

    /**
     * Sets the key data list.
     *
     * @param keyDataList the new key data list
     */
    public final void setKeyDataList(ArrayList<KeyData> keyDataList) {
        this.keyDataList = keyDataList;
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
     * @param maxSigLife the new max sig life
     */
    public final void setMaxSigLife(MaxSigLifeType maxSigLife) {
        this.maxSigLife = maxSigLife;
    }

    /**
     * Creates the XML element.
     *
     * @param xmlWriter the XML writer
     * @param addElement the add element
     */
    public void createXMLElement(final XMLWriter xmlWriter, final Element addElement) {

        if (this.maxSigLife != null) {
            this.maxSigLife.createXMLElement(xmlWriter, addElement);
        }

        if (this.getDsDataList() != null) {
            for (DSData dsData : this.getDsDataList()) {
                DSData.appendDsDataElement(xmlWriter, addElement, dsData);
            }
        }

        if (this.getKeyDataList() != null) {
            for (KeyData keyData : this.getKeyDataList()) {
                KeyData.appendKeyDataElement(xmlWriter, addElement, keyData);
            }
        }
    }

}
