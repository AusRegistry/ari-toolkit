package com.ausregistry.jtoolkit2.se.generic;

import javax.xml.xpath.XPathExpressionException;
import java.util.Set;
import java.util.TreeMap;

import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Extension of the domain mapping of the EPP info response, as defined in
 * RFC5730 and RFC5731, to generic domain names, the specification of which is
 * in the XML schema definition urn:X-ar:params:xml:ns:kv-1.1. Instances of this
 * class provide an interface to access all of the information available through
 * EPP for a generic domain name. This relies on the instance first being
 * initialised by a suitable EPP domain info response using the method
 * fromXML().
 *
 * For flexibility, this implementation extracts the data from the response
 * using XPath queries.
 */

public final class DomainInfoKV11ResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = -3759160844459220532L;

    private static final String KVLIST_EXPR = ResponseExtension.EXTENSION_EXPR
            + "/kvV1_1:infData/kvV1_1:kvlist";

    private final TreeMap<String, TreeMap<String, String>> kvLists = new TreeMap<String, TreeMap<String, String>>();

    private boolean initialised = false;

    /**
     * Retrieves the names of all key-value lists that have been added to the
     * object.
     *
     * @return the set of list names
     */
    public Set<String> getLists() {
        return kvLists.keySet();
    }

    /**
     * Retrieves the names of all item keys, for a specified key-value list
     * name.
     *
     * @param listName
     *            the name of the key-value list
     *
     * @return the set of item keys
     */
    public Set<String> getListItems(final String listName) {
        TreeMap<String, String> list = kvLists.get(listName);

        if (list == null) {
            return null;
        }

        return list.keySet();
    }

    /**
     * Retrieves the value of a given key-value item.
     *
     * @param listName
     *            the name of the key-value list
     * @param key
     *            the key of the item
     *
     * @return the value of the item
     */
    public String getItem(final String listName, final String key) {
        TreeMap<String, String> list = kvLists.get(listName);

        if (list == null) {
            return null;
        }

        return list.get(key);
    }

    @Override
    public void fromXML(final XMLDocument xmlDoc) throws XPathExpressionException {
        final NodeList kvlistNodes = xmlDoc.getElements(KVLIST_EXPR);

        if (kvlistNodes == null) {
            initialised = false;
        } else {
            for (int i = 0; i < kvlistNodes.getLength(); i++) {
                final Element currentKVList = (Element) kvlistNodes.item(i);
                final String kvListName = currentKVList.getAttribute("name");
                final NodeList kvListItems = currentKVList.getChildNodes();
                kvLists.put(kvListName, createKVList(kvListItems));
            }
            initialised = (kvlistNodes.getLength() > 0);
        }
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    private TreeMap<String, String> createKVList(final NodeList kvListItems) {
        final TreeMap<String, String> newKVList = new TreeMap<String, String>();

        for (int i = 0; i < kvListItems.getLength(); i++) {
            Node item = kvListItems.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                final Element kvlistElement = (Element) item;
                final String key = kvlistElement.getAttribute("key");
                final String value = kvlistElement.getTextContent();

                newKVList.put(key, value);
            }
        }

        return newKVList;
    }
}
