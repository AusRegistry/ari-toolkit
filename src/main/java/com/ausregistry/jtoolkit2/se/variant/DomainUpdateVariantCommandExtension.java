package com.ausregistry.jtoolkit2.se.variant;

import java.util.ArrayList;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.IdnaDomainVariant;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * <p>Extension for the EPP Domain Update command, representing the Update Variants aspect of the
 * Domain Name Variant extension (v1.0)</p>
 *
 * <p>Use this to add or remove variants for a domain as part of an EPP Domain Update
 * command compliant with RFC5730 and RFC5731. The response expected from a server
 * should be handled by a generic Response.</p>
 *
 * <p>This extension will throw an {@link IllegalArgumentException} if no variants
 * are added or removed as part of the "Update Variants" operation.</p>
 *
 * @see com.ausregistry.jtoolkit2.se.DomainUpdateCommand
 * @see com.ausregistry.jtoolkit2.se.Response
 */
public class DomainUpdateVariantCommandExtension implements CommandExtension {

    private static final long serialVersionUID = -3032129437463439207L;

    private final ArrayList<IdnaDomainVariant> addVariants = new ArrayList<IdnaDomainVariant>();
    private final ArrayList<IdnaDomainVariant> remVariants = new ArrayList<IdnaDomainVariant>();

    @Override
    public void addToCommand(final Command command) {
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

    /**
     * @throws IllegalArgumentException if any variants in the list are missing their name or user form.
     */
    public void addVariant(final IdnaDomainVariant... variant) {
        addVariantsToList(variant, addVariants);
    }

    /**
     * @throws IllegalArgumentException if any variants in the list are missing their name or user form.
     */
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
