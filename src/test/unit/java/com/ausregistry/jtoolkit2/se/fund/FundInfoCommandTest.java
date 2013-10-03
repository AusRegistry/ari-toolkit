package com.ausregistry.jtoolkit2.se.fund;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import org.junit.Test;
import org.xml.sax.SAXException;

public class FundInfoCommandTest {


    @Test
    public void testFundInfoCommand() {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
        Command cmd = new FundInfoCommand();
        try {
            String xml = cmd.toXML();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\"" +
                        " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                        " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">" +
                        "<command>" +
                            "<info>" +
                                "<info xmlns=\"urn:ar:params:xml:ns:fund-1.0\"" +
                                " xsi:schemaLocation=\"urn:ar:params:xml:ns:fund-1.0 fund-1.0.xsd\"/>" +
                            "</info>" +
                        "<clTRID>JTKUTEST.20070101.010101.0</clTRID>" +
                        "</command>" +
                    "</epp>", xml);
        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }
    }
}
