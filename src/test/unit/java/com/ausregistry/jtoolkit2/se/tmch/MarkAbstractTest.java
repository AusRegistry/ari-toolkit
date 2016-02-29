package com.ausregistry.jtoolkit2.se.tmch;

import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;

public abstract class MarkAbstractTest {
    private static final String TMCH_SIGNED_MARK_DATA_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<smd:signedMark xmlns:smd=\"urn:ietf:params:xml:ns:signedMark-1.0\" id=\"signedMark\">\n" +
                    "  <smd:id>1-2</smd:id>\n" +
                    "  <smd:issuerInfo issuerID=\"2\">\n" +
                    "    <smd:org>Example Inc.</smd:org>\n" +
                    "    <smd:email>support@example.tld</smd:email>\n" +
                    "    <smd:url>http://www.example.tld</smd:url>\n" +
                    "    <smd:voice x=\"1234\">+1.7035555555</smd:voice>\n" +
                    "  </smd:issuerInfo>\n" +
                    "  <smd:notBefore>2009-08-16T09:00:00.0Z</smd:notBefore>\n" +
                    "  <smd:notAfter>2010-08-16T09:00:00.0Z</smd:notAfter>\n" +
                    "  <mark:mark xmlns:mark=\"urn:ietf:params:xml:ns:mark-1.0\">\n" +
                    "    <mark:trademark>\n" +
                    "      <mark:id>1234-2</mark:id>\n" +
                    "      <mark:markName>Example One</mark:markName>\n" +
                    "      <mark:holder entitlement=\"owner\">\n" +
                    "        <mark:name>holderName</mark:name><mark:org>Example Inc.</mark:org>\n" +
                    "        <mark:addr>\n" +
                    "          <mark:street>123 Example Dr.</mark:street>\n" +
                    "          <mark:street>Suite 100</mark:street>\n" +
                    "          <mark:city>Reston</mark:city>\n" +
                    "          <mark:sp>VA</mark:sp>\n" +
                    "          <mark:pc>20190</mark:pc>\n" +
                    "          <mark:cc>US</mark:cc>\n" +
                    "        </mark:addr>\n" +
                    "<mark:voice x=\"1234\">+1.7035555555</mark:voice><mark:fax x=\"1234\">+1.7035555555</mark:fax>" +
                    "          <mark:email>support@example.tld</mark:email>\n" +
                    "      </mark:holder>\n" +
                    "<mark:contact type=\"owner\">" +
                    "<mark:name>contactName</mark:name><mark:org>Example Inc.</mark:org>\n" +
            "        <mark:addr>\n" +
            "          <mark:street>123 Example Dr.</mark:street>\n" +
            "          <mark:street>Suite 100</mark:street>\n" +
            "          <mark:city>Reston</mark:city>\n" +
            "          <mark:sp>VA</mark:sp>\n" +
            "          <mark:pc>20190</mark:pc>\n" +
            "          <mark:cc>US</mark:cc>\n" +
            "        </mark:addr>\n" +
            "<mark:voice x=\"1234\">+1.7035555555</mark:voice><mark:fax x=\"1234\">+1.7035555555</mark:fax>" +
            "<mark:email>123@123.com</mark:email>" +
            "      </mark:contact>" +
                    "      <mark:jurisdiction>US</mark:jurisdiction>\n" +
                    "      <mark:class>35</mark:class>\n" +
                    "      <mark:class>36</mark:class>\n" +
                    "      <mark:label>example-one</mark:label>\n" +
                    "      <mark:label>exampleone</mark:label>\n" +
                    "      <mark:goodsAndServices>Dirigendas et eiusmodi\n" +
                    "        featuring infringo in airfare et cartam servicia.\n" +
                    "      </mark:goodsAndServices> \n" +
                    "      <mark:apId>SOMEAPID</mark:apId>\n" +
                    "      <mark:apDate>2009-08-16T09:00:00.0Z</mark:apDate>\n" +
                    "      <mark:regNum>234235-A</mark:regNum>\n" +
                    "      <mark:regDate>2009-08-16T09:00:00.0Z</mark:regDate>\n" +
                    "      <mark:exDate>2015-08-16T09:00:00.0Z</mark:exDate>\n" +
                    "    </mark:trademark>\n" +
                    "    <mark:treatyOrStatute>\n" +
                    "      <mark:id>1234-2</mark:id>\n" +
                    "      <mark:markName>Example One</mark:markName>\n" +
                    "      <mark:holder entitlement=\"owner\">\n" +
                    "        <mark:org>Example Inc.</mark:org>\n" +
                    "        <mark:addr>\n" +
                    "          <mark:street>123 Example Dr.</mark:street>\n" +
                    "          <mark:street>Suite 100</mark:street>\n" +
                    "          <mark:city>Reston</mark:city>\n" +
                    "          <mark:sp>VA</mark:sp>\n" +
                    "          <mark:pc>20190</mark:pc>\n" +
                    "          <mark:cc>US</mark:cc>\n" +
                    "        </mark:addr>\n" +
                    "      </mark:holder>\n" +
                    "      <mark:protection>" +
                    "<mark:cc>US</mark:cc>" +
                    "<mark:region>region</mark:region>" +
                    "<mark:ruling>US</mark:ruling>" +
                    "<mark:ruling>CA</mark:ruling>" +
                    "</mark:protection>\n" +
                    "      <mark:label>example-one</mark:label>\n" +
                    "      <mark:label>exampleone</mark:label>\n" +
                    "      <mark:goodsAndServices>Dirigendas et eiusmodi\n" +
                    "        featuring infringo in airfare et cartam servicia.\n" +
                    "      </mark:goodsAndServices> \n" +
                    "      <mark:refNum>234235-A</mark:refNum>\n" +
                    "      <mark:proDate>2009-08-16T09:00:00.0Z</mark:proDate>\n" +
                    "      <mark:title>title</mark:title>\n" +
                    "      <mark:execDate>2015-08-16T09:00:00.0Z</mark:execDate>\n" +
                    "    </mark:treatyOrStatute>\n" +
                    "    <mark:court>\n" +
                    "      <mark:id>1234-2</mark:id>\n" +
                    "      <mark:markName>Example One</mark:markName>\n" +
                    "      <mark:holder entitlement=\"owner\">\n" +
                    "        <mark:org>Example Inc.</mark:org>\n" +
                    "        <mark:addr>\n" +
                    "          <mark:street>123 Example Dr.</mark:street>\n" +
                    "          <mark:street>Suite 100</mark:street>\n" +
                    "          <mark:city>Reston</mark:city>\n" +
                    "          <mark:sp>VA</mark:sp>\n" +
                    "          <mark:pc>20190</mark:pc>\n" +
                    "          <mark:cc>US</mark:cc>\n" +
                    "        </mark:addr>\n" +
                    "      </mark:holder>\n" +
                    "      <mark:label>example-one</mark:label>\n" +
                    "      <mark:label>exampleone</mark:label>\n" +
                    "      <mark:goodsAndServices>Dirigendas et eiusmodi\n" +
                    "        featuring infringo in airfare et cartam servicia.\n" +
                    "      </mark:goodsAndServices> \n" +
                    "      <mark:refNum>234235-A</mark:refNum>\n" +
                    "      <mark:proDate>2009-08-16T09:00:00.0Z</mark:proDate>\n" +
                    "      <mark:cc>cc</mark:cc><mark:region>r1</mark:region><mark:region>r3</mark:region>\n" +
                    "      <mark:courtName>courtName</mark:courtName>\n" +
                    "    </mark:court>\n" +
                    "  </mark:mark>\n" +
                    "  <Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                    "    <SignedInfo>\n" +
                    "      <CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
                    "      <SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/>\n" +
                    "      <Reference URI=\"#signedMark\">\n" +
                    "        <Transforms>\n" +
                    "          <Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\n" +
                    "        </Transforms>\n" +
                    "        <DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
                    "        <DigestValue>miF4M2aTd1Y3tKOzJtiyl2VpzAnVPnV1Hq7Zax+yzrA=</DigestValue>\n" +
                    "      </Reference>\n" +
                    "    </SignedInfo>\n" +
                    "    <SignatureValue>MELpHTWEVfG1JcsG1/a//o54OnlJ5A864+X5JwfqgGBBeZSzGHNzwzTKFzIyyyfn\n" +
                    "lGxVwNMoBV5aSvkF7oEKMNVzfcl/P0czNQZ/LJ83p3Ol27/iUNsqgCaGf9Zupw+M\n" +
                    "XT4Q2lOrIw+qSx5g7q9T83siMLvkD5uEYlU5dPqgsObLTW8/doTQrA14RcxgY4kG\n" +
                    "a4+t5B1cT+5VaghTOPb8uUSEDKjnOsGdy8p24wgyK9n8h0CTSS2ZQ6Zq/RmQeT7D\n" +
                    "sbceUHheQ+mkQWIljpMQqsiBjw5XXh4jkEgfAzrb6gkYEF+X8ReuPZuOYC4QjIET\n" +
                    "yx8ifN4KE3GIbMXeF4LDsA==</SignatureValue>\n" +
                    "    <KeyInfo>\n" +
                    "      <KeyValue>\n" +
                    "<RSAKeyValue>\n" +
                    "<Modulus>\n" +
                    "o/cwvXhbVYl0RDWWvoyeZpETVZVVcMCovUVNg/swWinuMgEWgVQFrz0xA04pEhXC\n" +
                    "FVv4evbUpekJ5buqU1gmQyOsCKQlhOHTdPjvkC5upDqa51Flk0TMaMkIQjs7aUKC\n" +
                    "mA4RG4tTTGK/EjR1ix8/D0gHYVRldy1YPrMP+ou75bOVnIos+HifrAtrIv4qEqwL\n" +
                    "L4FTZAUpaCa2BmgXfy2CSRQbxD5Or1gcSa3vurh5sPMCNxqaXmIXmQipS+DuEBqM\n" +
                    "M8tldaN7RYojUEKrGVsNk5i9y2/7sjn1zyyUPf7vL4GgDYqhJYWV61DnXgx/Jd6C\n" +
                    "WxvsnDF6scscQzUTEl+hyw==\n" +
                    "</Modulus>\n" +
                    "<Exponent>\n" +
                    "AQAB\n" +
                    "</Exponent>\n" +
                    "</RSAKeyValue>\n" +
                    "</KeyValue>\n" +
                    "      <X509Data>\n" +
                    "<X509Certificate>MIIESTCCAzGgAwIBAgIBAjANBgkqhkiG9w0BAQsFADBiMQswCQYDVQQGEwJVUzEL\n" +
                    "MAkGA1UECBMCQ0ExFDASBgNVBAcTC0xvcyBBbmdlbGVzMRMwEQYDVQQKEwpJQ0FO\n" +
                    "TiBUTUNIMRswGQYDVQQDExJJQ0FOTiBUTUNIIFRFU1QgQ0EwHhcNMTMwMjA4MDAw\n" +
                    "MDAwWhcNMTgwMjA3MjM1OTU5WjBsMQswCQYDVQQGEwJVUzELMAkGA1UECBMCQ0Ex\n" +
                    "FDASBgNVBAcTC0xvcyBBbmdlbGVzMRcwFQYDVQQKEw5WYWxpZGF0b3IgVE1DSDEh\n" +
                    "MB8GA1UEAxMYVmFsaWRhdG9yIFRNQ0ggVEVTVCBDRVJUMIIBIjANBgkqhkiG9w0B\n" +
                    "AQEFAAOCAQ8AMIIBCgKCAQEAo/cwvXhbVYl0RDWWvoyeZpETVZVVcMCovUVNg/sw\n" +
                    "WinuMgEWgVQFrz0xA04pEhXCFVv4evbUpekJ5buqU1gmQyOsCKQlhOHTdPjvkC5u\n" +
                    "pDqa51Flk0TMaMkIQjs7aUKCmA4RG4tTTGK/EjR1ix8/D0gHYVRldy1YPrMP+ou7\n" +
                    "5bOVnIos+HifrAtrIv4qEqwLL4FTZAUpaCa2BmgXfy2CSRQbxD5Or1gcSa3vurh5\n" +
                    "sPMCNxqaXmIXmQipS+DuEBqMM8tldaN7RYojUEKrGVsNk5i9y2/7sjn1zyyUPf7v\n" +
                    "L4GgDYqhJYWV61DnXgx/Jd6CWxvsnDF6scscQzUTEl+hywIDAQABo4H/MIH8MAwG\n" +
                    "A1UdEwEB/wQCMAAwHQYDVR0OBBYEFPZEcIQcD/Bj2IFz/LERuo2ADJviMIGMBgNV\n" +
                    "HSMEgYQwgYGAFO0/7kEh3FuEKS+Q/kYHaD/W6wihoWakZDBiMQswCQYDVQQGEwJV\n" +
                    "UzELMAkGA1UECBMCQ0ExFDASBgNVBAcTC0xvcyBBbmdlbGVzMRMwEQYDVQQKEwpJ\n" +
                    "Q0FOTiBUTUNIMRswGQYDVQQDExJJQ0FOTiBUTUNIIFRFU1QgQ0GCAQEwDgYDVR0P\n" +
                    "AQH/BAQDAgeAMC4GA1UdHwQnMCUwI6AhoB+GHWh0dHA6Ly9jcmwuaWNhbm4ub3Jn\n" +
                    "L3RtY2guY3JsMA0GCSqGSIb3DQEBCwUAA4IBAQB2qSy7ui+43cebKUKwWPrzz9y/\n" +
                    "IkrMeJGKjo40n+9uekaw3DJ5EqiOf/qZ4pjBD++oR6BJCb6NQuQKwnoAz5lE4Ssu\n" +
                    "y5+i93oT3HfyVc4gNMIoHm1PS19l7DBKrbwbzAea/0jKWVzrvmV7TBfjxD3AQo1R\n" +
                    "bU5dBr6IjbdLFlnO5x0G0mrG7x5OUPuurihyiURpFDpwH8KAH1wMcCpXGXFRtGKk\n" +
                    "wydgyVYAty7otkl/z3bZkCVT34gPvF70sR6+QxUy8u0LzF5A/beYaZpxSYG31amL\n" +
                    "AdXitTWFipaIGea9lEGFM0L9+Bg7XzNn4nVLXokyEB3bgS4scG6QznX23FGk</X509Certificate>\n" +
                    "</X509Data>\n" +
                    "    </KeyInfo>\n" +
                    "  </Signature>\n" +
                    "</smd:signedMark>";

    protected XMLDocument xmlDocument;

    @Before
    public void parseExampleDocument() throws Exception {
        xmlDocument = new XMLParser().parse(TMCH_SIGNED_MARK_DATA_XML);
    }
}
