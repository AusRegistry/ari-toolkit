package com.ausregistry.jtoolkit2.se.secdns;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Processes the &lt;update&gt; element as documented in RFC5910.
 */
public class SecDnsDomainUpdateCommandExtension implements CommandExtension {

    private static final long serialVersionUID = -2441248857298156911L;

    private boolean urgent;
    private DSOrKeyType addData;
    private RemType remData;
    private ChgType chgData;

    @Override
    public void addToCommand(final Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element updateElement = SecDNSXMLUtil.createElement(xmlWriter, extensionElement, "update");
        if (urgent) {
            updateElement.setAttribute("urgent", "true");
        }
        handleRemove(xmlWriter, updateElement);
        handleAdd(xmlWriter, updateElement);
        handleChange(xmlWriter, updateElement);
    }

    private void handleChange(final XMLWriter xmlWriter, final Element updateElement) {
        if (chgData != null && chgData.getMaxSigLife() != null && chgData.getMaxSigLife().getMaxSigLife() > 0) {
            final Element changeElement = SecDNSXMLUtil.createElement(xmlWriter, updateElement, "chg");
            chgData.createXMLElement(xmlWriter, changeElement);
        }
    }

    private void handleRemove(final XMLWriter xmlWriter, final Element updateElement) {
        if (this.remData == null
                || ((remData.getDsDataList() == null || remData.getDsDataList().size() == 0)
                        && (remData.getKeyDataList() == null || remData.getKeyDataList().size() == 0) && !remData
                        .isRemoveAll())) {
            return;
        }

        final Element removeElement = SecDNSXMLUtil.createElement(xmlWriter, updateElement, "rem");
        this.remData.createXMLElement(xmlWriter, removeElement);
    }

    private void handleAdd(final XMLWriter xmlWriter, final Element updateElement) {
        if (this.addData == null
                || ((this.addData.getDsDataList() == null || this.addData.getDsDataList().size() == 0) && (this.addData
                        .getKeyDataList() == null || this.addData.getKeyDataList().size() == 0))) {
            return;
        }

        final Element addElement = SecDNSXMLUtil.createElement(xmlWriter, updateElement, "add");
        addData.createXMLElement(xmlWriter, addElement);

    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(final boolean urgentArg) {
        this.urgent = urgentArg;
    }

    public final ChgType getChgData() {
        return chgData;
    }

    public final void setChgData(ChgType chgData) {
        this.chgData = chgData;
    }

    public final DSOrKeyType getAddData() {
        return addData;
    }

    public final void setAddData(DSOrKeyType addData) {
        this.addData = addData;
    }

    public final RemType getRemData() {
        return remData;
    }

    public final void setRemData(RemType remData) {
        this.remData = remData;
    }

}
