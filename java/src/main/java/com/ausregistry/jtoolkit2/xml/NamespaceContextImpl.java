package com.ausregistry.jtoolkit2.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.NamespaceContext;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ExtensionImpl;
import com.ausregistry.jtoolkit2.se.StandardObjectType;

/**
 * <p>
 * EPP specific implementation of a NamespaceContext, which maps prefixes to name space URIs and vice versa. Initially,
 * instances of this class support all of the standard EPP object name spaces, as defined in RFC5730-RFC5733, as well as
 * the name spaces of the e164epp, auext and aeext command/response extensions and the arext protocol extension. Support
 * for other extensions can be added at runtime by invoking the <a href="#put(String, String)">put</a> class method.
 * </p>
 *
 * <p>
 * Uses the debug level loggers.
 * </p>
 */
public final class NamespaceContextImpl implements NamespaceContext {
    private static final int DEFAULT_SIZE = 8;
    private static Map<List<String>, String> prefixUriMap;
    private static Map<String, List<String>> uriPrefixMap;

    static {
        prefixUriMap = new HashMap<List<String>, String>(DEFAULT_SIZE);
        uriPrefixMap = new HashMap<String, List<String>>(DEFAULT_SIZE);

        /*
         * No definite answer on why EPP has 2 prefixes, but it has been suggested that this may have to do with
         * compatibility with the old toolkits.
         */
        NamespaceContextImpl.put("e", "urn:ietf:params:xml:ns:epp-1.0");
        NamespaceContextImpl.put("epp", "urn:ietf:params:xml:ns:epp-1.0");
        NamespaceContextImpl.put(StandardObjectType.HOST.getName(), StandardObjectType.HOST.getURI());
        NamespaceContextImpl.put(StandardObjectType.CONTACT.getName(), StandardObjectType.CONTACT.getURI());
        NamespaceContextImpl.put(StandardObjectType.DOMAIN.getName(), StandardObjectType.DOMAIN.getURI());
        NamespaceContextImpl.put(ExtendedObjectType.AU_DOMAIN.getName(), ExtendedObjectType.AU_DOMAIN.getURI());
        NamespaceContextImpl.put(ExtendedObjectType.AR_DOMAIN.getName(), ExtendedObjectType.AR_DOMAIN.getURI());
        NamespaceContextImpl.put(ExtendedObjectType.AE_DOMAIN.getName(), ExtendedObjectType.AE_DOMAIN.getURI());
        NamespaceContextImpl.put(ExtendedObjectType.IDNA_DOMAIN.getName(), ExtendedObjectType.IDNA_DOMAIN.getURI());
        NamespaceContextImpl.put(ExtendedObjectType.VARIANT.getName(), ExtendedObjectType.VARIANT.getURI());
        NamespaceContextImpl.put(ExtendedObjectType.SEC_DNS.getName(), ExtendedObjectType.SEC_DNS.getURI());
        NamespaceContextImpl.put(ExtendedObjectType.RESTORE.getName(), ExtendedObjectType.RESTORE.getURI());
        NamespaceContextImpl.put(ExtendedObjectType.SYNC.getName(), ExtendedObjectType.SYNC.getURI());
        NamespaceContextImpl.put(ExtendedObjectType.KV.getName(), ExtendedObjectType.KV.getURI());
        NamespaceContextImpl.put(ExtendedObjectType.LAUNCH.getName(), ExtendedObjectType.LAUNCH.getURI());
        NamespaceContextImpl.put(ExtensionImpl.AE.getPrefix(), ExtensionImpl.AE.getURI());
        NamespaceContextImpl.put(ExtensionImpl.REGISTRANT.getPrefix(), ExtensionImpl.REGISTRANT.getURI());
        NamespaceContextImpl.put(ExtensionImpl.E164.getPrefix(), ExtensionImpl.E164.getURI());
        NamespaceContextImpl.put(ExtensionImpl.AU.getPrefix(), ExtensionImpl.AU.getURI());
        NamespaceContextImpl.put(ExtensionImpl.AU_V1.getPrefix(), ExtensionImpl.AU_V1.getURI());
        NamespaceContextImpl.put(ExtensionImpl.AR.getPrefix(), ExtensionImpl.AR.getURI());
        NamespaceContextImpl.put(ExtensionImpl.VIEXT.getPrefix(), ExtensionImpl.VIEXT.getURI());
    }

    /**
     * Add a prefix->URI mapping and a URI->prefix mapping between the given prefix and URI.
     */
    public static synchronized void put(String prefix, String uri) {
        boolean isPut = false;

        for (Map.Entry<List<String>, String> mapping : prefixUriMap.entrySet()) {
            if (mapping.getValue().equals(uri)) {
                mapping.getKey().add(prefix);
                isPut = true;
                break;
            }
        }

        if (!isPut) {
            final Vector<String> newList = new Vector<String>(3);
            newList.add(prefix);
            prefixUriMap.put(newList, uri);
        }

        if (uriPrefixMap.keySet().contains(uri)) {
            final List<String> prefixList = uriPrefixMap.get(uri);
            prefixList.add(prefix);
        } else {
            final List<String> newList = new Vector<String>(3);
            newList.add(prefix);
            uriPrefixMap.put(uri, newList);
        }
    }

    private final Logger debugLogger;

    {
        final String pname = getClass().getPackage().getName();
        debugLogger = Logger.getLogger(pname + ".debug");
    }

    /**
     * Create a NamespaceContextImpl with state as possibly adjusted by any previous calls to NamespaceContextImpl.put.
     */
    public NamespaceContextImpl() {
    }

    /**
     * Implemented using a map lookup of the given prefix.
     */
    @Override
    public String getNamespaceURI(String prefix) {
        for (Map.Entry<List<String>, String> mapping : prefixUriMap.entrySet()) {
            if (mapping.getKey().contains(prefix)) {
                if (debugLogger.isLoggable(Level.FINE)) {
                    debugLogger.fine("resolved prefix " + prefix + " to URI " + mapping.getValue());
                }
                return mapping.getValue();
            }
        }

        return null;
    }

    /**
     * Implemented using a map lookup of the given name space URI.
     */
    @Override
    public String getPrefix(String namespaceURI) {
        if (uriPrefixMap.keySet().contains(namespaceURI)) {
            return uriPrefixMap.get(namespaceURI).get(0);
        }

        return null;
    }

    /**
     * Lookup the List of prefixes for the given namespaceURI from the URI->prefix map, then return the iterator for
     * that List if the URI is in the map. Return null if there is no mapping for the given URI.
     */
    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        if (!uriPrefixMap.keySet().contains(namespaceURI)) {
            return null;
        }

        return uriPrefixMap.get(namespaceURI).iterator();
    }

}
