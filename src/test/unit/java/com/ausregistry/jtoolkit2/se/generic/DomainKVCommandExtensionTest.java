package com.ausregistry.jtoolkit2.se.generic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.CommandExtension;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.DomainUpdateCommand;

public class DomainKVCommandExtensionTest {
    private static String registrantName = "AusRegistry";
    private static String eligibilityType = "Trademark";
    private static int policyReason = 1;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testUpdateSingleKVList() {
        final DomainKVCommandExtension extension = new DomainKVCommandExtension(
                CommandExtension.UPDATE);
        addSampleItemsToExtension(extension, "ae");

        final Command updateCommand = new DomainUpdateCommand("jtkutest.com.ae");
        updateCommand.appendExtension(extension);

        try {
            final String xml = updateCommand.toXML();
            assertEquals(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><update><update xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.ae</name></update></update><extension><update xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kvlist name=\"ae\"><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantName\">AusRegistry</item></kvlist></update></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                    xml);
        } catch (final SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testCreateSingleKVList() {
        final DomainKVCommandExtension extension = new DomainKVCommandExtension(
                CommandExtension.CREATE);
        addSampleItemsToExtension(extension, "ae");

        final Command createCommand = new DomainCreateCommand("jtkutest.com.ae", "jtkUT3st",
                "JTKCON", new String[] { "JTKCON2" });
        createCommand.appendExtension(extension);

        try {
            final String xml = createCommand.toXML();
            assertEquals(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.ae</name><registrant>JTKCON</registrant><contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kvlist name=\"ae\"><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantName\">AusRegistry</item></kvlist></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                    xml);
        } catch (final SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testMultipleKVList() {
        final DomainKVCommandExtension extension = new DomainKVCommandExtension(
                CommandExtension.CREATE);
        addSampleItemsToExtension(extension, "au");
        addSampleItemsToExtension(extension, "ae");

        final Command createCommand = new DomainCreateCommand("jtkutest.com.ae", "jtkUT3st",
                "JTKCON", new String[] { "JTKCON2" });
        createCommand.appendExtension(extension);

        try {
            final String xml = createCommand.toXML();
            assertEquals(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\"><name>jtkutest.com.ae</name><registrant>JTKCON</registrant><contact type=\"tech\">JTKCON2</contact><authInfo><pw>jtkUT3st</pw></authInfo></create></create><extension><create xmlns=\"urn:X-ar:params:xml:ns:kv-1.0\" xsi:schemaLocation=\"urn:X-ar:params:xml:ns:kv-1.0 kv-1.0.xsd\"><kvlist name=\"ae\"><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantName\">AusRegistry</item></kvlist><kvlist name=\"au\"><item key=\"eligibilityType\">Trademark</item><item key=\"policyReason\">1</item><item key=\"registrantName\">AusRegistry</item></kvlist></create></extension><clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>",
                    xml);
        } catch (final SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    private void addSampleItemsToExtension(final DomainKVCommandExtension extension, String listName) {
        extension.addItem(listName, "eligibilityType", eligibilityType);
        extension.addItem(listName, "policyReason", String.valueOf(policyReason));
        extension.addItem(listName, "registrantName", registrantName);
    }
}
