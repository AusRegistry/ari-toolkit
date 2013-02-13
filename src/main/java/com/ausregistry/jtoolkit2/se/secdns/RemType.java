package com.ausregistry.jtoolkit2.se.secdns;

import java.util.ArrayList;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Models the &lt;rem&gt; element as documented in RFC5910.
 */
public class RemType {

    private ArrayList<DSData> dsDataList;
    private ArrayList<KeyData> keyDataList;
    private boolean removeAll;

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
     * Checks if the request is to remove all the DNSSEC data.
     *
     * @return true, if is removes the all
     */
    public final boolean isRemoveAll() {
        return removeAll;
    }

    /**
     * Sets remove all DNSSEC data.
     *
     * @param removeAll the new removes the all
     */
    public final void setRemoveAll(boolean removeAll) {
        this.removeAll = removeAll;
    }

    /**
     * Creates the XML element.
     *
     * @param xmlWriter the XML writer
     * @param removeElement the remove element
     */
    public void createXMLElement(final XMLWriter xmlWriter, final Element removeElement) {

        if (isRemoveAll()) {
            SecDNSXMLUtil.appendChildElement(xmlWriter, removeElement, "all", isRemoveAll());
        }

        if (getDsDataList() != null) {
            for (DSData dsData : getDsDataList()) {
                DSData.appendDsDataElement(xmlWriter, removeElement, dsData);
            }
        }

        if (getKeyDataList() != null) {
            for (KeyData keyData : this.getKeyDataList()) {
                KeyData.appendKeyDataElement(xmlWriter, removeElement, keyData);
            }
        }
    }

}
