package com.ausregistry.jtoolkit2.se.idn.ietf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

public class DomainCreateIetfIdnCommandExtensionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void testDomainCreateIdnCommandWithTableAndUname() {
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        DomainCreateIetfIdnCommandExtension idnExt = new DomainCreateIetfIdnCommandExtension("es", "uname");
        cmd.appendExtension(idnExt);

        String expectedExtension = new StringBuilder("<data xmlns=\"urn:ietf:params:xml:ns:idn-1.0\">")
                .append("<table>es</table>")
                .append("<uname>uname</uname>")
                .append("</data>")
                .toString();
        try {
            String xml = cmd.toXML();
            assertEquals(expectedXmlWithExtension(expectedExtension), xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void testDomainCreateIdnCommandWithTableButWithoutUname() {
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        DomainCreateIetfIdnCommandExtension idnExt = new DomainCreateIetfIdnCommandExtension("es", null);
        cmd.appendExtension(idnExt);
        String expectedExtension = new StringBuilder("<data xmlns=\"urn:ietf:params:xml:ns:idn-1.0\">")
                .append("<table>es</table>")
                .append("</data>")
                .toString();
        try {
            String xml = cmd.toXML();
            assertEquals(expectedXmlWithExtension(expectedExtension), xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }

    @Test
    public void failWhenTableNotProvided() throws SAXException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Table must not be null or empty.");
        Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        DomainCreateIetfIdnCommandExtension idnExt = new DomainCreateIetfIdnCommandExtension(null, null);
        cmd.appendExtension(idnExt);
        cmd.toXML();
    }

    private String expectedXmlWithExtension(String extensionSection) {
        return new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"")
                .append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")
                .append(" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">")
                .append("<command>")
                .append("<create>")
                .append("<create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">")
                .append("<name>jtkutest.com.au")
                .append("</name>")
                .append("<authInfo>")
                .append("<pw>jtkUT3st")
                .append("</pw>")
                .append("</authInfo>")
                .append("</create>")
                .append("</create>")
                .append("<extension>")
                .append(extensionSection)
                .append("</extension>")
                .append("<clTRID>JTKUTEST.20070101.010101.0")
                .append("</clTRID>")
                .append("</command>")
                .append("</epp>")
                .toString();
    }
}

