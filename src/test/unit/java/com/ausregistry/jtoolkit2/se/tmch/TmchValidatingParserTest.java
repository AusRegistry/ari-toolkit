package com.ausregistry.jtoolkit2.se.tmch;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import javax.xml.bind.DatatypeConverter;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.security.cert.*;
import java.util.Arrays;
import java.util.Date;

import com.ausregistry.jtoolkit2.se.tmch.exception.*;
import com.ausregistry.jtoolkit2.test.infrastructure.ReflectionUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TmchValidatingParser.class, XMLSignatureFactory.class})
public class TmchValidatingParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TmchValidatingParser tmchXMLUtil;

    @Mock private CRL mockCrl;
    @Mock private CertificateFactory mockCertificateFactory;
    @Mock private CertPath mockCertPath;
    @Mock private CertPathValidator mockCertPathValidator;
    @Mock private X509Certificate mockSmdCertificate;
    @Mock private XPath mockXpath;
    @Mock private XMLSignatureFactory mockXmlSignatureFactory;
    @Mock private XMLSignature mockXmlSignature;

    @Before
    public void setUp() throws Exception {
        mockStatic(CertificateFactory.class);
        mockStatic(CertPathValidator.class);
        mockStatic(XPathFactory.class);
        mockStatic(XMLSignatureFactory.class);

        InputStream mockInputStream = mock(InputStream.class);
        InputStream mockInputStreamTwo = mock(InputStream.class);
        Certificate mockTmchCertificate = mock(X509Certificate.class);
        Node mockNode = mock(Node.class);
        PublicKey mockKey = mock(PublicKey.class);
        BufferedReader mockReader = mock(BufferedReader.class);
        Document mockDocument = mock(Document.class);
        Element mockElement = mock(Element.class);

        when(CertificateFactory.getInstance("X.509")).thenReturn(mockCertificateFactory);
        when(mockCertificateFactory.generateCRL(mockInputStream)).thenReturn(mockCrl);
        when(mockCertificateFactory.generateCertificate(mockInputStreamTwo)).thenReturn(mockTmchCertificate);
        when(CertPathValidator.getInstance("PKIX")).thenReturn(mockCertPathValidator);
        when(mockCertificateFactory.generateCertPath(Arrays.asList(mockSmdCertificate))).thenReturn(mockCertPath);
        when(XMLSignatureFactory.getInstance()).thenReturn(mockXmlSignatureFactory);

        whenNew(BufferedReader.class).withArguments(any(InputStreamReader.class))
                .thenReturn(mockReader);

        tmchXMLUtil = new TmchValidatingParser(mockInputStream, mockInputStream, mockInputStreamTwo);

        ReflectionUtils.setPropertyOnInstance(tmchXMLUtil, "xPath", mockXpath);

        when(mockXpath.evaluate(eq("/smd:signedMark/ds:Signature"), any(Document.class), eq(XPathConstants.NODE)))
                .thenReturn(mockNode);
        when(mockNode.getOwnerDocument()).thenReturn(mockDocument);
        when(mockDocument.getDocumentElement()).thenReturn(mockElement);
        when(mockXmlSignatureFactory.unmarshalXMLSignature(any(DOMValidateContext.class))).thenReturn(mockXmlSignature);
        when(mockXmlSignature.validate(any(DOMValidateContext.class))).thenReturn(true);

        when(mockCertificateFactory.generateCertificate(any(InputStream.class))).thenReturn(mockSmdCertificate);
        when(mockSmdCertificate.getPublicKey()).thenReturn(mockKey);
    }

    @Test
    public void shouldThrowExceptionWhenSmdCannotBeParsed() throws Exception {
        thrown.expect(InvalidSignedMarkDataException.class);
        tmchXMLUtil.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream("".getBytes()));
    }

    @Test
    public void shouldThrowExceptionWithRightMessageWhenCertificateIsRevoked() throws Exception {
        String dummyEncodedSmd = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5k" +
                "YWxvbmU9Im5vIj8+PHNpZ25lZE1hcms6c2lnbmVkTWFyayB4bWxuczpzaWduZWRNYXJr" +
                "PSJ1cm46aWV0ZjpwYXJhbXM6eG1sOm5zOnNpZ25lZE1hcmstMS4wIiBpZD0ic2lnbmVk" +
                "TWFyayI+PHNpZ25lZE1hcms6aWQ+MS0yPC9zaWduZWRNYXJrOmlkPjwvc2lnbmVkTWFy" +
                "azpzaWduZWRNYXJrPg==";

        TmchCertificateRevokedException mockException = mock(TmchCertificateRevokedException.class);

        when(mockCrl.isRevoked(mockSmdCertificate)).thenReturn(true);
        whenNew(TmchCertificateRevokedException.class).withArguments(mockSmdCertificate)
                .thenReturn(mockException);

        thrown.expect(is(mockException));
        tmchXMLUtil.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream(dummyEncodedSmd.getBytes()));
    }

    @Test
    public void shouldThrowExeceptionWhenCertificateIsInvalid() throws Exception {
        String dummyEncodedSmdWithId = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5k" +
                "YWxvbmU9Im5vIj8+PHNpZ25lZE1hcms6c2lnbmVkTWFyayB4bWxuczpzaWduZWRNYXJr" +
                "PSJ1cm46aWV0ZjpwYXJhbXM6eG1sOm5zOnNpZ25lZE1hcmstMS4wIiBpZD0ic2lnbmVk" +
                "TWFyayI+PHNpZ25lZE1hcms6aWQ+MS0yPC9zaWduZWRNYXJrOmlkPjwvc2lnbmVkTWFy" +
                "azpzaWduZWRNYXJrPg==";
        CertPathValidatorException certPathValidatorException = mock(CertPathValidatorException.class);

        when(mockCertPathValidator.validate(eq(mockCertPath), any(PKIXParameters.class)))
                .thenThrow(certPathValidatorException);

        TmchInvalidCertificateException mockException = mock(TmchInvalidCertificateException.class);
        whenNew(TmchInvalidCertificateException.class).withArguments(mockSmdCertificate, certPathValidatorException)
                .thenReturn(mockException);

        thrown.expect(is(mockException));
        tmchXMLUtil.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream(dummyEncodedSmdWithId.getBytes()));
    }

    @Test
    public void shouldThrowInvalidSignedMarkDataExceptionIfUnmarshallingSignatureFails() throws Exception {
        String dummyEncodedSmdWithId = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5k" +
                "YWxvbmU9Im5vIj8+PHNpZ25lZE1hcms6c2lnbmVkTWFyayB4bWxuczpzaWduZWRNYXJr" +
                "PSJ1cm46aWV0ZjpwYXJhbXM6eG1sOm5zOnNpZ25lZE1hcmstMS4wIiBpZD0ic2lnbmVk" +
                "TWFyayI+PHNpZ25lZE1hcms6aWQ+MS0yPC9zaWduZWRNYXJrOmlkPjwvc2lnbmVkTWFy" +
                "azpzaWduZWRNYXJrPg==";

        MarshalException mockMarshalException = mock(MarshalException.class);
        when(mockXmlSignatureFactory.unmarshalXMLSignature(any(DOMValidateContext.class)))
                .thenThrow(mockMarshalException);

        InvalidSignedMarkDataException mockException = mock(InvalidSignedMarkDataException.class);
        whenNew(InvalidSignedMarkDataException.class).withArguments(mockMarshalException).thenReturn(mockException);

        thrown.expect(is(mockException));
        tmchXMLUtil.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream(dummyEncodedSmdWithId.getBytes()));
    }

    @Test
    public void shouldThrowExceptionWhenXmlSignatureIsInvalid() throws Exception {
        String dummyEncodedSmdWithId = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5k" +
                "YWxvbmU9Im5vIj8+PHNpZ25lZE1hcms6c2lnbmVkTWFyayB4bWxuczpzaWduZWRNYXJr" +
                "PSJ1cm46aWV0ZjpwYXJhbXM6eG1sOm5zOnNpZ25lZE1hcmstMS4wIiBpZD0ic2lnbmVk" +
                "TWFyayI+PHNpZ25lZE1hcms6aWQ+MS0yPC9zaWduZWRNYXJrOmlkPjwvc2lnbmVkTWFy" +
                "azpzaWduZWRNYXJrPg==";

        when(mockXmlSignature.validate(any(DOMValidateContext.class))).thenReturn(false);

        thrown.expect(SmdSignatureInvalidException.class);
        tmchXMLUtil.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream(dummyEncodedSmdWithId.getBytes()));
    }

    @Test
    public void shouldThrowExceptionIfCertificateIsNotOfTypeX509() throws Exception {
        String dummyEncodedSmdWithId = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5k" +
                "YWxvbmU9Im5vIj8+PHNpZ25lZE1hcms6c2lnbmVkTWFyayB4bWxuczpzaWduZWRNYXJr" +
                "PSJ1cm46aWV0ZjpwYXJhbXM6eG1sOm5zOnNpZ25lZE1hcmstMS4wIiBpZD0ic2lnbmVk" +
                "TWFyayI+PHNpZ25lZE1hcms6aWQ+MS0yPC9zaWduZWRNYXJrOmlkPjwvc2lnbmVkTWFy" +
                "azpzaWduZWRNYXJrPg==";

        Certificate mockIncorrectTypeCert = mock(Certificate.class);
        when(mockCertificateFactory.generateCertificate(any(InputStream.class))).thenReturn(mockIncorrectTypeCert);

        thrown.expect(TmchCertificateInvalidTypeException.class);
        thrown.expectMessage("The certificate used in SignedMarkData is not of valid type, expected a certificate " +
                "of type X509Certificate, got a certificate of type: Certificate");
        tmchXMLUtil.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream(dummyEncodedSmdWithId.getBytes()));
    }

    @Test
    public void shouldThrowExceptionIfSmdDoesNotHaveSignature() throws Exception {
        String dummyEncodedSmdWithId = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5k" +
                "YWxvbmU9Im5vIj8+PHNpZ25lZE1hcms6c2lnbmVkTWFyayB4bWxuczpzaWduZWRNYXJr" +
                "PSJ1cm46aWV0ZjpwYXJhbXM6eG1sOm5zOnNpZ25lZE1hcmstMS4wIiBpZD0ic2lnbmVk" +
                "TWFyayI+PHNpZ25lZE1hcms6aWQ+MS0yPC9zaWduZWRNYXJrOmlkPjwvc2lnbmVkTWFy" +
                "azpzaWduZWRNYXJrPg==";

        when(mockXpath.evaluate(eq("/smd:signedMark/ds:Signature"), any(Document.class), eq(XPathConstants.NODE)))
                .thenReturn(null);
        thrown.expect(SmdSignatureMissingException.class);
        tmchXMLUtil.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream(dummyEncodedSmdWithId.getBytes()));
    }

    @Test
    public void shouldThrowExceptionForExpiredSmdWhenCallingTheValidationMethodWithoutADate() throws Exception {
        String dummyEncodedSmdWithId = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5k" +
                "YWxvbmU9Im5vIj8+PHNpZ25lZE1hcms6c2lnbmVkTWFyayB4bWxuczpzaWduZWRNYXJr" +
                "PSJ1cm46aWV0ZjpwYXJhbXM6eG1sOm5zOnNpZ25lZE1hcmstMS4wIiBpZD0ic2lnbmVk" +
                "TWFyayI+PHNpZ25lZE1hcms6aWQ+MS0yPC9zaWduZWRNYXJrOmlkPjwvc2lnbmVkTWFy" +
                "azpzaWduZWRNYXJrPg==";

        Date dateForValidation = DatatypeConverter.parseDate("2015-08-16T09:00:00.0Z").getTime();
        whenNew(Date.class).withNoArguments().thenReturn(dateForValidation);

        when(mockXpath.evaluate(eq("/smd:signedMark/smd:notBefore"), any(Document.class)))
                .thenReturn("2009-08-16T09:00:00.0Z");
        when(mockXpath.evaluate(eq("/smd:signedMark/smd:notAfter"), any(Document.class)))
                .thenReturn("2013-08-16T09:00:00.0Z");

        thrown.expect(ExpiredSignedMarkDataException.class);
        tmchXMLUtil.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream(dummyEncodedSmdWithId.getBytes()));
    }

    @Test
    public void shouldThrowExceptionForNotYetValidSmdWhenCallingTheValidationMethodWithoutADate() throws Exception {
        String dummyEncodedSmdWithId = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5k" +
                "YWxvbmU9Im5vIj8+PHNpZ25lZE1hcms6c2lnbmVkTWFyayB4bWxuczpzaWduZWRNYXJr" +
                "PSJ1cm46aWV0ZjpwYXJhbXM6eG1sOm5zOnNpZ25lZE1hcmstMS4wIiBpZD0ic2lnbmVk" +
                "TWFyayI+PHNpZ25lZE1hcms6aWQ+MS0yPC9zaWduZWRNYXJrOmlkPjwvc2lnbmVkTWFy" +
                "azpzaWduZWRNYXJrPg==";

        Date dateForValidation = DatatypeConverter.parseDate("2008-08-16T09:00:00.0Z").getTime();
        whenNew(Date.class).withNoArguments().thenReturn(dateForValidation);

        when(mockXpath.evaluate(eq("/smd:signedMark/smd:notBefore"), any(Document.class)))
                .thenReturn("2009-08-16T09:00:00.0Z");
        when(mockXpath.evaluate(eq("/smd:signedMark/smd:notAfter"), any(Document.class)))
                .thenReturn("2013-08-16T09:00:00.0Z");

        thrown.expect(NotYetValidSignedMarkDataException.class);
        tmchXMLUtil.validateAndParseEncodedSignedMarkData(new ByteArrayInputStream(dummyEncodedSmdWithId.getBytes()));
    }
}