package com.ausregistry.jtoolkit2.se.tmch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.ausregistry.jtoolkit2.se.tmch.exception.*;
import com.ausregistry.jtoolkit2.xml.ParsingException;
import org.apache.commons.codec.DecoderException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TmchSmdIntegrationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TmchValidatingParser tmchValidatingParser;

    @Before
    public void setUp() throws Exception {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        InputStream crlInputStream = contextClassLoader.getResourceAsStream("tmch.crl");
        InputStream smdrlInputStream = contextClassLoader.getResourceAsStream("tmch-smdrl.csv");
        InputStream certInputStream = contextClassLoader.getResourceAsStream("tmch.crt");

        tmchValidatingParser = new TmchValidatingParser(crlInputStream, smdrlInputStream, certInputStream);
    }

    @Test
    public void shouldParseIcannSmdExample() throws IOException, ParsingException, DecoderException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("Trademark-Agent-Arabic-Active.smd");

        TmchXmlParser tmchXmlParser = new TmchXmlParser();

        byte[] encodedSmdPart = TmchXmlParser.extractBase64EncodedPartFromSmdFile(inputStream);

        SignedMarkData signedMarkData = tmchXmlParser.parseEncodedSignedMarkData(new ByteArrayInputStream
                (encodedSmdPart));

        assertEquals(signedMarkData.getId(), "0000001861373633632586-65535");
    }

    @Test
    public void shouldFailValidationIfCertificateIsRevoked() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("RevokedCertificateSMDData.txt");

        String expectedMessage = "Invalid Certificate in SignedMarkData. The issuing authority has revoked the " +
                "certificate used in SignedMarkData:\n" +
                "Certificate of serial number '7'";
        thrown.expect(TmchCertificateRevokedException.class);
        thrown.expectMessage(expectedMessage);
        tmchValidatingParser.validateAndParseEncodedSignedMarkData(inputStream);
    }

    @Test
    public void shouldFailValidationIfSmdIsRevoked() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("RevokedSMDData.txt");

        thrown.expect(TmchSmdRevokedException.class);
        thrown.expectMessage("SignedMarkData with ID: 1-2 has been revoked.");
        tmchValidatingParser.validateAndParseEncodedSignedMarkData(inputStream);
    }

    @Test
    public void shouldFailValidationIfSmdIsNotYetValid() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("NotYetValidSMDData.txt");

        thrown.expect(NotYetValidSignedMarkDataException.class);
        thrown.expectMessage("SignedMarkData is not valid before 2013-07-29T09:00:00Z.");
        tmchValidatingParser.validateAndParseEncodedSignedMarkData(inputStream);
    }

    @Test
    public void shouldFailValidationIfSmdHasExpired() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("ExpiredSMDData.txt");

        thrown.expect(ExpiredSignedMarkDataException.class);
        thrown.expectMessage("SignedMarkData is not valid after 2012-08-29T09:00:00Z.");
        tmchValidatingParser.validateAndParseEncodedSignedMarkData(inputStream);
    }

    @Test
    public void shouldFailValidationIfCertificateIsNotSignedByIcann() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("NotSignedByCASMDData.txt");

        thrown.expect(TmchCertificateNotSignedByIcannCAException.class);
        thrown.expectMessage("Invalid Certificate in SignedMarkData. The issuing authority that issued the " +
                "SignedMarkData is not a trusted entity.\nCertificate of serial number '5'");
        tmchValidatingParser.validateAndParseEncodedSignedMarkData(inputStream);
    }

    @Test
    public void shouldFailValidationIfSmdDoesNotHaveSignature() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("NoSignatureSmd.txt");

        thrown.expect(SmdSignatureMissingException.class);
        thrown.expectMessage("SignedMarkData provided is invalid as it is does not contain the Signature element.");
        tmchValidatingParser.validateAndParseEncodedSignedMarkData(inputStream);
    }

    @Test
    public void shouldFailValidationIfSignatureIsInvalid() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("InvalidSignature-Trademark-Agent-English-Active.smd");

        byte[] encodedSmdPart = tmchValidatingParser.extractBase64EncodedPartFromSmdFile(inputStream);

        thrown.expect(SmdSignatureInvalidException.class);
        thrown.expectMessage("Invalid Signature element in the SignedMarkData provided.");
        tmchValidatingParser.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream(encodedSmdPart));
    }

    @Test
    public void shouldFailWhenCertificateIsNotYetValid() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("ValidSMDData.txt");

        Date dateForValidation = DatatypeConverter.parseDate("2009-08-16T09:00:00.0Z").getTime();

        thrown.expect(TmchCertificateNotYetValidException.class);
        thrown.expectMessage("The certificate used in SignedMarkData is not valid before 2013-04-23T00:20:15Z.");
        tmchValidatingParser.validateAndParseEncodedSignedMarkData(inputStream, dateForValidation);
    }

    @Test
    public void shouldFailWhenCertificateHasExpired() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("ValidSMDData.txt");

        Date dateForValidation = DatatypeConverter.parseDate("2015-08-16T09:00:00.0Z").getTime();
        thrown.expect(TmchCertificateExpiredException.class);
        thrown.expectMessage("The certificate used in SignedMarkData is not valid after 2014-04-18T00:20:15Z.");
        tmchValidatingParser.validateAndParseEncodedSignedMarkData(inputStream, dateForValidation);
    }

    @Test
    public void shouldFailValidationIfTheSmdIsInvalid() throws Exception {
        thrown.expect(InvalidSignedMarkDataException.class);
        thrown.expectMessage("Invalid SignedMarkData provided.");
        tmchValidatingParser.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream("".getBytes()));
    }

    @Test
    public void shouldPassValidationWhenSmdIsValid() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("ValidSMDData.txt");
        SignedMarkData signedMarkData = tmchValidatingParser.validateAndParseEncodedSignedMarkData(inputStream);
        assertThat(signedMarkData.getId(), is("11-2"));
    }

    @Test
    public void shouldPassValidationForADateWhenSmdIsValid() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream
                ("ValidSMDData.txt");

        Date dateForValidation = DatatypeConverter.parseDate("2013-08-16T09:00:00.0Z").getTime();
        SignedMarkData signedMarkData = tmchValidatingParser.validateAndParseEncodedSignedMarkData(inputStream,
                dateForValidation);
        assertThat(signedMarkData.getId(), is("11-2"));
    }
}