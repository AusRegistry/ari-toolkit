package com.ausregistry.jtoolkit2.se;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Use this to set domain extension properties relating to variants for an EPP
 * Domain Info command.
 * 
 * @see com.ausregistry.jtoolkit2.se.DomainVariantResponseExtension
 */
public final class DomainInfoVariantCommandExtension implements CommandExtension {

    private static final long serialVersionUID = -2441248857298156911L;
    private String variants;

    public void addToCommand(final Command command) throws Exception {
		XMLWriter xmlWriter = command.getXmlWriter();
		Element extensionElement = command.getExtensionElement();
		Element infoElement = xmlWriter.appendChild(
				extensionElement, "info",
				ExtendedObjectType.VARIANT.getURI());
        if (variants != null) {
			infoElement.setAttribute("variants", variants);
		}
    }

    public String getVariants() {
        return variants;
    }

    public void setVariants(final String variantsArg) {
        this.variants = variantsArg;
    }

}
