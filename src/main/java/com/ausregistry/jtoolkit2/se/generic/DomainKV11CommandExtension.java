package com.ausregistry.jtoolkit2.se.generic;

import java.util.Map.Entry;
import java.util.TreeMap;

import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLWriter;
import org.w3c.dom.Element;

/**
 * This class models the command elements as specified in the XML schema
 * definition urn:X-ar:params:xml:ns:kv-1.1.
 */
public final class DomainKV11CommandExtension implements CommandExtension {

    private static final long serialVersionUID = -334068823995826478L;
    private static final ExtendedObjectType EXTENSION = ExtendedObjectType.KVV11;

    private final TreeMap<String, TreeMap<String, String>> kvLists;
    private final String commandType;

    /**
     * Creates a key-value domain extension for specified command type
     *
     * @param commandType
     *            the type of command
     *
     * @see CommandExtension#CREATE
     * @see CommandExtension#UPDATE
     * @see CommandExtension#RENEW
     * @see CommandExtension#TRANSFER
     */
    public DomainKV11CommandExtension(final String commandType) {
        kvLists = new TreeMap<String, TreeMap<String, String>>();
        this.commandType = commandType;
    }

    public void addItem(final String listName, final String key, final String value) {
        TreeMap<String, String> kvList = kvLists.get(listName);

        if (kvList == null) {
            kvList = new TreeMap<String, String>();
            kvLists.put(listName, kvList);
        }

        kvList.put(key, value);
    }

    public void addToCommand(final Command command) {
        final XMLWriter xmlWriter = command.getXmlWriter();
        final Element extensionElement = command.getExtensionElement();
        final Element commandElement = createCommandElement(xmlWriter, extensionElement);
        createKVListElements(xmlWriter, commandElement);
    }

    private Element createCommandElement(final XMLWriter xmlWriter, final Element extensionElement) {
        final Element commandElement = xmlWriter.appendChild(extensionElement, commandType, EXTENSION.getURI());

        commandElement.setAttribute("xsi:schemaLocation", EXTENSION.getSchemaLocation());
        return commandElement;
    }

    private void createKVListElements(final XMLWriter xmlWriter, final Element commandElement) {
        for (final String kvListName : kvLists.keySet()) {
            final Element kvlistElement = xmlWriter.appendChild(commandElement, "kvlist", EXTENSION.getURI());
            kvlistElement.setAttribute("name", kvListName);

            addItemsToList(xmlWriter, kvListName, kvlistElement);
        }
    }

    private void addItemsToList(final XMLWriter xmlWriter, final String kvListName,
            final Element kvlistElement) {
        Element element;

        for (final Entry<String, String> item : kvLists.get(kvListName).entrySet()) {
            element = xmlWriter.appendChild(kvlistElement, "item");
            element.setAttribute("key", item.getKey());
            element.setTextContent(item.getValue());
        }
    }
}
