package com.ausregistry.jtoolkit2.xml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ExtensionImpl;
import com.ausregistry.jtoolkit2.se.StandardObjectType;

/**
 * Provides URI to Source resolution service as specified by the URIResolver interface.
 *
 * Uses the debug level logger.
 */
public class EPPResolver implements URIResolver {
    private static Map<String, String> uriLocMap;

    private static String[] uris;

    static {
        uris = new String[] {
                "urn:ietf:params:xml:ns:eppcom-1.0",
                "urn:ietf:params:xml:ns:epp-1.0",
                StandardObjectType.HOST.getURI(),
                StandardObjectType.CONTACT.getURI(),
                StandardObjectType.DOMAIN.getURI(),
                ExtensionImpl.E164.getURI(),
                ExtensionImpl.AU_V1.getURI(),
                ExtensionImpl.AU.getURI(),
                ExtendedObjectType.AU_DOMAIN.getURI(),
                ExtensionImpl.AR.getURI(),
                ExtendedObjectType.AR_DOMAIN.getURI(),
                ExtensionImpl.AE.getURI(),
                ExtendedObjectType.AE_DOMAIN.getURI(),
                ExtendedObjectType.IDN.getURI(),
                ExtendedObjectType.IDNA_DOMAIN.getURI(),
                ExtendedObjectType.VARIANT.getURI(),
                ExtensionImpl.VIEXT.getURI(),
                ExtendedObjectType.SEC_DNS.getURI(),
                ExtendedObjectType.SYNC.getURI(),
                ExtendedObjectType.KV.getURI(),
                ExtensionImpl.REGISTRANT.getURI(),
                ExtendedObjectType.RESTORE.getURI(),
                ExtendedObjectType.LAUNCH.getURI()
        };

        final String[] localResources = new String[] {
                "eppcom-1.0.xsd",
                "epp-1.0.xsd",
                StandardObjectType.HOST.getSchemaDefintion(),
                StandardObjectType.CONTACT.getSchemaDefintion(),
                StandardObjectType.DOMAIN.getSchemaDefintion(),
                ExtensionImpl.E164.getSchemaDefinition(),
                ExtensionImpl.AU_V1.getSchemaDefinition(),
                ExtensionImpl.AU.getSchemaDefinition(),
                ExtendedObjectType.AU_DOMAIN.getSchemaDefinition(),
                ExtensionImpl.AR.getSchemaDefinition(),
                ExtendedObjectType.AR_DOMAIN.getSchemaDefinition(),
                ExtensionImpl.AE.getSchemaDefinition(),
                ExtendedObjectType.AE_DOMAIN.getSchemaDefinition(),
                ExtendedObjectType.IDN.getSchemaDefinition(),
                ExtendedObjectType.IDNA_DOMAIN.getSchemaDefinition(),
                ExtendedObjectType.VARIANT.getSchemaDefinition(),
                ExtensionImpl.VIEXT.getSchemaDefinition(),
                ExtendedObjectType.SEC_DNS.getSchemaDefinition(),
                ExtendedObjectType.RESTORE.getSchemaDefinition(),
                ExtendedObjectType.SYNC.getSchemaDefinition(),
                ExtendedObjectType.KV.getSchemaDefinition(),
                ExtensionImpl.REGISTRANT.getSchemaDefinition(),
                ExtendedObjectType.LAUNCH.getSchemaDefinition()
        };

        uriLocMap = new HashMap<String, String>();
        for (int i = 0; i < uris.length; i++) {
            uriLocMap.put(uris[i], localResources[i]);
        }
    }

    private final String pname = getClass().getPackage().getName();

    /**
     * List the namespace uniform resource identifiers for which this instance provides a mapping. These must be
     * ordered in such a way that the an XML schema identified by getResolvedURIs()[i] does not depend on an XML
     * schema identified by getResolvedURIs()[j] where j &gt; i.
     *
     * @return The URIs identifying XML schemas for which this instance provides URI to local resource mappings.
     */
    public String[] getResolvedURIs() {
        return uris;
    }

    /**
     * Resolve a public URI to a local system resource suitable for providing as input to creating a Schema object.
     *
     * @throws TransformerException
     *             The given href doesn't map to any available resource on the system.
     */
    @Override
    public Source resolve(final String href, final String base) throws TransformerException {
        final String filename = uriLocMap.get(href);

        Source source = null;

        Logger.getLogger(pname + ".debug").fine(
                ErrorPkg.getMessage("xml.validation.resolve.begin", new String[] { "<<href>>", "<<filename>>" },
                        new String[] { href, filename }));

        final InputStream in = getClass().getClassLoader().getResourceAsStream(filename);
        if (in == null) {
            throw new TransformerException(ErrorPkg.getMessage("xml.validation.source.nx", "<<filename>>", filename));
        }

        Logger.getLogger(pname + ".debug").fine(
                ErrorPkg.getMessage("xml.validation.resolve.end",
                        new String[] { "<<href>>", "<<filename>>", "<<file>>" },
                        new String[] { href, filename, filename }));
        source = new StreamSource(in);

        return source;
    }

}
