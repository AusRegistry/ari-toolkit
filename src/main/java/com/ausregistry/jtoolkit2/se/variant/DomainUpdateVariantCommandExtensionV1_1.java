package com.ausregistry.jtoolkit2.se.variant;

import java.util.ArrayList;

import com.ausregistry.jtoolkit2.se.IdnDomainVariant;
import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * This class models the &lt;update&gt; element as documented in 'Variant Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)'.
 */
public class DomainUpdateVariantCommandExtensionV1_1 implements CommandExtension {

    private static final long serialVersionUID = -3032129437463439207L;

    private final ArrayList<IdnDomainVariant> addVariants = new ArrayList<IdnDomainVariant>();
    private final ArrayList<IdnDomainVariant> remVariants = new ArrayList<IdnDomainVariant>();

    public void addVariant(final IdnDomainVariant... variant) {
        addVariantsToList(variant, addVariants);
    }

    public void remVariant(final IdnDomainVariant... variant) {
        addVariantsToList(variant, remVariants);
    }

    @Override
    public void addToCommand(final Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element updateElement =
                xmlWriter.appendChild(extensionElement, "update",
                        ExtendedObjectType.VARIANT_V1_1.getURI());

        if (addVariants.size() == 0 && remVariants.size() == 0) {
            throw new IllegalStateException("No variants found");
        }

        writeVariantsToXml(xmlWriter, updateElement, "rem", remVariants);
        writeVariantsToXml(xmlWriter, updateElement, "add", addVariants);
    }

    private void addVariantsToList(final IdnDomainVariant[] variantsToAdd,
                                   final ArrayList<IdnDomainVariant> variantList) {

        for (final IdnDomainVariant variant : variantsToAdd) {
            if (variant == null) {
                throw new IllegalArgumentException(
                        ErrorPkg.getMessage("se.domain.update.idna.variant.missing"));
            }

            if (variant.getName() == null) {
                throw new IllegalArgumentException(
                        ErrorPkg.getMessage("se.domain.update.idna.variant.name.missing"));
            }

            variantList.add(variant);
        }
    }

    private void writeVariantsToXml(final XMLWriter xmlWriter, final Element updateElement,
                                    final String updateType, final ArrayList<IdnDomainVariant> variants) {
        if (variants.size() > 0) {
            final Element element = xmlWriter.appendChild(updateElement, updateType);

            for (final IdnDomainVariant variant : variants) {
                xmlWriter.appendChild(element, "variant").setTextContent(variant.getName());
            }
        }
    }
}
