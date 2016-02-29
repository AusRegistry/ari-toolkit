package com.ausregistry.jtoolkit2.se.secdns;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Processes the &lt;create&gt; element as documented in RFC5910.
 */
public final class SecDnsDomainCreateCommandExtension implements CommandExtension {

    private static final long serialVersionUID = -1153065031531409436L;

    private DSOrKeyType createData;

    @Override
    public void addToCommand(final Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();

        final Element createElement = SecDNSXMLUtil.createElement(xmlWriter, extensionElement, "create");

        if (this.createData == null
                || ((this.createData.getDsDataList() == null || this.createData.getDsDataList().size() == 0)
                    && (this.createData.getKeyDataList() == null || this.createData.getKeyDataList().size() == 0))) {
            return;
        }

        createData.createXMLElement(xmlWriter, createElement);

    }

    public DSOrKeyType getCreateData() {
        return createData;
    }

    public void setCreateData(DSOrKeyType createData) {
        this.createData = createData;
    }

}
