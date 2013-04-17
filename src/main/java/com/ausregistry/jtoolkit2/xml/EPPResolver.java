package com.ausregistry.jtoolkit2.xml;

import java.io.InputStream;
import java.util.*;
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
        List<String> uriList = new ArrayList<String>();
        List<String> localResources = new ArrayList<String>();

        uriList.add("urn:ietf:params:xml:ns:eppcom-1.0");
        uriList.add("urn:ietf:params:xml:ns:epp-1.0");
        uriList.add("http://www.w3.org/2000/09/xmldsig#");
        uriList.add("urn:ietf:params:xml:ns:mark-1.0");
        uriList.add("urn:ietf:params:xml:ns:signedMark-1.0");

        localResources.add("eppcom-1.0.xsd");
        localResources.add("epp-1.0.xsd");
        localResources.add("xmldsig-core-schema.xsd");
        localResources.add("mark-1.0.xsd");
        localResources.add("signedMark-1.0.xsd");

        for (StandardObjectType objectType : StandardObjectType.values()) {
            uriList.add(objectType.getURI());
            localResources.add(objectType.getSchemaDefintion());
        }
        for (ExtensionImpl extension : ExtensionImpl.values()) {
            uriList.add(extension.getURI());
            localResources.add(extension.getSchemaDefinition());
        }
        for (ExtendedObjectType extendedObjectType : ExtendedObjectType.values()) {
            uriList.add(extendedObjectType.getURI());
            localResources.add(extendedObjectType.getSchemaDefinition());
        }
        uris = uriList.toArray(new String[]{});
        uriLocMap = new HashMap<String, String>();
        for (int i = 0; i < uris.length; i++) {
            uriLocMap.put(uris[i], localResources.get(i));
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
