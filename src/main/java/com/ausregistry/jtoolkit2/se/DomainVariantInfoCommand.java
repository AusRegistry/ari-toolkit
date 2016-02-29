package com.ausregistry.jtoolkit2.se;

/**
 * Used to check which variants of domain objects would be provisioned in a
 * Registry if a specific domain was created. Instances of this class generate
 * Domain Variant Info command service elements via the toXML() method,
 * consistent with the AusRegistry documents 'Variant Extension Domain Mapping
 * for the Extensible Provisioning Protocol' and 'Variant Extension for the
 * Extensible Provisioning Protocol'.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainVariantInfoResponse
 */
public class DomainVariantInfoCommand extends ProtocolExtensionCommand {

    private static final long serialVersionUID = 43233444461228919L;

    private static final CommandType VARIANT_INFO_COMMAND_TYPE = new VariantInfoCommandType();

    /**
     * @param name the domain name
     * @param language the language associated with the domain, can be null
     */
    public DomainVariantInfoCommand(final String name, final String language) {
        super(VARIANT_INFO_COMMAND_TYPE, ExtendedObjectType.VARIANT,
                name, "language", language);
    }

    @Override
    protected Extension getExtension() {
        return ExtensionImpl.VIEXT;
    }
}
