package com.ausregistry.jtoolkit2.se.generic;

import java.util.GregorianCalendar;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandType;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.Period;
import com.ausregistry.jtoolkit2.xml.XmlOutputConfig;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * In cases where the legal registrant of a domain name has changed, this
 * class should be used to request a transfer of registrant.  This is a
 * different action to correcting extension data which was originally specified
 * incorrectly, and should only be used in the situation described.
 *
 * Use this class to generate a standards-compliant XML document, given simple
 * input parameters.  The {@code toXML()} method in Command serialises this object to
 * XML.
 */
public final class DomainRegistrantTransferCommand extends Command {

    private static final long serialVersionUID = 6623456616110752095L;
    private static final CommandType TRANSFER_COMMAND_TYPE = new DomainRegistrantTransferCommandType();

    private TreeMap<String, String> kvList;
    private final Element kvListElement;

    public DomainRegistrantTransferCommand(final String name, final GregorianCalendar curExpDate,
            final String kvListName, final String explanation) {

        this(name, curExpDate, null, kvListName, explanation);
    }

    /**
     * Request that the domain name be transferred to the legal entity
     * specified by the extension data that is provided in the key-value list.
     *
     * @param name The domain name to transfer.
     *
     * @param curExpDate The current expiry of the identified domain name. This is
     *                   required in order to prevent repeated transfer of the name due
     *                   to protocol transmission failures.
     *
     * @param period The desired new validity period, starting from the time the
     *               transfer completes successfully. Optional.
     *
     * @param kvListName The name under which the list of key-value items are aggregated.
     *
     * @param explanation An explanation of how the transfer was effected.
     */
    public DomainRegistrantTransferCommand(final String name, final GregorianCalendar curExpDate,
            final Period period, final String kvListName, final String explanation) {

        super(TRANSFER_COMMAND_TYPE);

        xmlWriter.appendChild(
                cmdElement, ExtendedObjectType.REGISTRANT.getIdentType()).setTextContent(name);


        if (curExpDate == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.domain.registrantTransfer.registrant.missing_arg"));
        }

        final String curExpDateStr = EPPDateFormatter.toXSDate(curExpDate);
        xmlWriter.appendChild(cmdElement, "curExpDate").setTextContent(curExpDateStr);

        if (period != null) {
            period.appendPeriod(xmlWriter, cmdElement);
        }

        kvListElement = xmlWriter.appendChild(cmdElement, "kvlist", ExtendedObjectType.KV.getURI());
        kvListElement.setAttribute("name", kvListName);

        xmlWriter.appendChild(cmdElement, "explanation").setTextContent(explanation);

        kvList = new TreeMap<String, String>();
    }

    /**
     * Adds a key-value item into the list, to be included in the command when
     * the XML is generated.
     */
    public void addItem(final String key, final String value) {
        kvList.put(key, value);
    }

    @Override
    protected String toXMLImpl(XmlOutputConfig xmlOutputConfig) throws SAXException {
        Element itemElement;

        for (final Entry<String, String> item : kvList.entrySet()) {
            itemElement = xmlWriter.appendChild(kvListElement, "item");
            itemElement.setAttribute("key", item.getKey());
            itemElement.setTextContent(item.getValue());
        }

        return super.toXMLImpl(xmlOutputConfig);
    }

    @Override
    protected void initCmdElement() {
        command = xmlWriter.appendChild(xmlWriter.appendChild(
                xmlWriter.getRoot(), "extension"), "command",
                ExtendedObjectType.REGISTRANT.getURI());
    }
}
