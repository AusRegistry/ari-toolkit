package com.ausregistry.jtoolkit2.se.variant;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.IdnaDomainVariant;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * This class models the &lt;update&gt; element as documented in 'Variant Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)'.
 */
public class DomainUpdateVariantCommandExtension implements CommandExtension {

    private static final long serialVersionUID = -3032129437463439207L;

    private final ArrayList<IdnaDomainVariant> addVariants = new ArrayList<IdnaDomainVariant>();
    private final ArrayList<IdnaDomainVariant> remVariants = new ArrayList<IdnaDomainVariant>();

    public DomainUpdateVariantCommandExtension() {
    }

    @Override
    public void addToCommand(final Command command) throws Exception {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element updateElement =
                xmlWriter.appendChild(extensionElement, "update",
                        ExtendedObjectType.VARIANT.getURI());

        if (addVariants.size() == 0 && remVariants.size() == 0) {
            throw new IllegalStateException("No variants found");
        }

        writeVariantsToXml(xmlWriter, updateElement, "add", addVariants);
        writeVariantsToXml(xmlWriter, updateElement, "rem", remVariants);
    }

    public void addVariant(final IdnaDomainVariant... variant) {
        addVariantsToList(variant, addVariants);
    }

    public void remVariant(final IdnaDomainVariant... variant) {
        addVariantsToList(variant, remVariants);
    }

    private void addVariantsToList(final IdnaDomainVariant[] variantsToAdd,
            final ArrayList<IdnaDomainVariant> variantList) {

        for (final IdnaDomainVariant variant : variantsToAdd) {
            if (variant == null) {
                throw new IllegalArgumentException(
                        ErrorPkg.getMessage("se.domain.update.idna.variant.missing"));
            }

            if (variant.getName() == null) {
                throw new IllegalArgumentException(
                        ErrorPkg.getMessage("se.domain.update.idna.variant.name.missing"));
            }

            if (variant.getUserForm() == null) {
                throw new IllegalArgumentException(
                        ErrorPkg.getMessage("se.domain.update.idna.variant.userForm.missing"));
            }

            variantList.add(variant);
        }
    }

    private void writeVariantsToXml(final XMLWriter xmlWriter, final Element updateElement,
            final String updateType, final ArrayList<IdnaDomainVariant> variants) {
        if (variants.size() > 0) {
            final Element element = xmlWriter.appendChild(updateElement, updateType);

            for (final IdnaDomainVariant variant : variants) {
                xmlWriter.appendChild(element, "variant", variant.getName(), "userForm",
                        variant.getUserForm());
            }
        }
    }
}
