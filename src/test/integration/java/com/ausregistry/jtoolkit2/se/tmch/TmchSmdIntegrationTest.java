package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ausregistry.jtoolkit2.xml.ParsingException;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;

public class TmchSmdIntegrationTest {
    @Test
    public void shouldParseIcannSmdExample() throws IOException, ParsingException, DecoderException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("Trademark-Agent-Arabic-Active.smd");

        byte[] encodedSmdPart = TmchXMLUtil.extractBase64EncodedPartFromSmdFile(inputStream);

        SignedMarkData signedMarkData = TmchXMLUtil.parseEncodedSignedMarkData(new ByteArrayInputStream
                (encodedSmdPart));

        assertEquals(signedMarkData.getId(), "0000001861373633632586-65535");
    }
}
