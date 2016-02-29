package com.ausregistry.jtoolkit2.se.tmch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.codec.DecoderException;
import org.junit.Before;
import org.junit.Test;

public class TmchXMLUtilIntegrationTest {
    private static final String ENCODED_SMD_PART = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPHNtZDpzaWdu"
            + "ZWRNYXJrIHhtbG5zOnNtZD0idXJuOmlldGY6cGFyYW1zOnhtbDpuczpzaWduZWRN"
            + "YXJrLTEuMCIgaWQ9InNpZ25lZE1hcmsiPgogIDxzbWQ6aWQ+MS0yPC9zbWQ6aWQ+"
            + "CiAgPHNtZDppc3N1ZXJJbmZvIGlzc3VlcklEPSIyIj4KICAgIDxzbWQ6b3JnPkV4"
            + "YW1wbGUgSW5jLjwvc21kOm9yZz4KICAgIDxzbWQ6ZW1haWw+c3VwcG9ydEBleGFt"
            + "cGxlLnRsZDwvc21kOmVtYWlsPgogICAgPHNtZDp1cmw+aHR0cDovL3d3dy5leGFt"
            + "cGxlLnRsZDwvc21kOnVybD4KICAgIDxzbWQ6dm9pY2UgeD0iMTIzNCI+KzEuNzAz"
            + "NTU1NTU1NTwvc21kOnZvaWNlPgogIDwvc21kOmlzc3VlckluZm8+CiAgPHNtZDpu"
            + "b3RCZWZvcmU+MjAwOS0wOC0xNlQwOTowMDowMC4wWjwvc21kOm5vdEJlZm9yZT4K"
            + "ICA8c21kOm5vdEFmdGVyPjIwMTAtMDgtMTZUMDk6MDA6MDAuMFo8L3NtZDpub3RB"
            + "ZnRlcj4KICA8bWFyazptYXJrIHhtbG5zOm1hcms9InVybjppZXRmOnBhcmFtczp4"
            + "bWw6bnM6bWFyay0xLjAiPgogICAgPG1hcms6dHJhZGVtYXJrPgogICAgICA8bWFy"
            + "azppZD4xMjM0LTI8L21hcms6aWQ+CiAgICAgIDxtYXJrOm1hcmtOYW1lPkV4YW1w"
            + "bGUgT25lPC9tYXJrOm1hcmtOYW1lPgogICAgICA8bWFyazpob2xkZXIgZW50aXRs"
            + "ZW1lbnQ9Im93bmVyIj4KICAgICAgICA8bWFyazpvcmc+RXhhbXBsZSBJbmMuPC9t"
            + "YXJrOm9yZz4KICAgICAgICA8bWFyazphZGRyPgogICAgICAgICAgPG1hcms6c3Ry"
            + "ZWV0PjEyMyBFeGFtcGxlIERyLjwvbWFyazpzdHJlZXQ+CiAgICAgICAgICA8bWFy"
            + "azpzdHJlZXQ+U3VpdGUgMTAwPC9tYXJrOnN0cmVldD4KICAgICAgICAgIDxtYXJr"
            + "OmNpdHk+UmVzdG9uPC9tYXJrOmNpdHk+CiAgICAgICAgICA8bWFyazpzcD5WQTwv"
            + "bWFyazpzcD4KICAgICAgICAgIDxtYXJrOnBjPjIwMTkwPC9tYXJrOnBjPgogICAg"
            + "ICAgICAgPG1hcms6Y2M+VVM8L21hcms6Y2M+CiAgICAgICAgPC9tYXJrOmFkZHI+"
            + "CiAgICAgIDwvbWFyazpob2xkZXI+CiAgICAgIDxtYXJrOmp1cmlzZGljdGlvbj5V"
            + "UzwvbWFyazpqdXJpc2RpY3Rpb24+CiAgICAgIDxtYXJrOmNsYXNzPjM1PC9tYXJr"
            + "OmNsYXNzPgogICAgICA8bWFyazpjbGFzcz4zNjwvbWFyazpjbGFzcz4KICAgICAg"
            + "PG1hcms6bGFiZWw+ZXhhbXBsZS1vbmU8L21hcms6bGFiZWw+CiAgICAgIDxtYXJr"
            + "OmxhYmVsPmV4YW1wbGVvbmU8L21hcms6bGFiZWw+CiAgICAgIDxtYXJrOmdvb2Rz"
            + "QW5kU2VydmljZXM+RGlyaWdlbmRhcyBldCBlaXVzbW9kaQogICAgICAgIGZlYXR1"
            + "cmluZyBpbmZyaW5nbyBpbiBhaXJmYXJlIGV0IGNhcnRhbSBzZXJ2aWNpYS4KICAg"
            + "ICAgPC9tYXJrOmdvb2RzQW5kU2VydmljZXM+IAogICAgICA8bWFyazpyZWdOdW0+"
            + "MjM0MjM1PC9tYXJrOnJlZ051bT4KICAgICAgPG1hcms6cmVnRGF0ZT4yMDA5LTA4"
            + "LTE2VDA5OjAwOjAwLjBaPC9tYXJrOnJlZ0RhdGU+CiAgICAgIDxtYXJrOmV4RGF0"
            + "ZT4yMDE1LTA4LTE2VDA5OjAwOjAwLjBaPC9tYXJrOmV4RGF0ZT4KICAgIDwvbWFy"
            + "azp0cmFkZW1hcms+CiAgPC9tYXJrOm1hcms+CiAgPFNpZ25hdHVyZSB4bWxucz0i"
            + "aHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnIyI+CiAgICA8U2lnbmVk"
            + "SW5mbz4KICAgICAgPENhbm9uaWNhbGl6YXRpb25NZXRob2QgQWxnb3JpdGhtPSJo"
            + "dHRwOi8vd3d3LnczLm9yZy8yMDAxLzEwL3htbC1leGMtYzE0biMiLz4KICAgICAg"
            + "PFNpZ25hdHVyZU1ldGhvZCBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIw"
            + "MDEvMDQveG1sZHNpZy1tb3JlI3JzYS1zaGEyNTYiLz4KICAgICAgPFJlZmVyZW5j"
            + "ZSBVUkk9IiNzaWduZWRNYXJrIj4KICAgICAgICA8VHJhbnNmb3Jtcz4KICAgICAg"
            + "ICAgIDxUcmFuc2Zvcm0gQWxnb3JpdGhtPSJodHRwOi8vd3d3LnczLm9yZy8yMDAw"
            + "LzA5L3htbGRzaWcjZW52ZWxvcGVkLXNpZ25hdHVyZSIvPgogICAgICAgIDwvVHJh"
            + "bnNmb3Jtcz4KICAgICAgICA8RGlnZXN0TWV0aG9kIEFsZ29yaXRobT0iaHR0cDov"
            + "L3d3dy53My5vcmcvMjAwMS8wNC94bWxlbmMjc2hhMjU2Ii8+CiAgICAgICAgPERp"
            + "Z2VzdFZhbHVlPm1pRjRNMmFUZDFZM3RLT3pKdGl5bDJWcHpBblZQblYxSHE3WmF4"
            + "K3l6ckE9PC9EaWdlc3RWYWx1ZT4KICAgICAgPC9SZWZlcmVuY2U+CiAgICA8L1Np"
            + "Z25lZEluZm8+CiAgICA8U2lnbmF0dXJlVmFsdWU+TUVMcEhUV0VWZkcxSmNzRzEv"
            + "YS8vbzU0T25sSjVBODY0K1g1SndmcWdHQkJlWlN6R0hOend6VEtGekl5eXlmbgps"
            + "R3hWd05Nb0JWNWFTdmtGN29FS01OVnpmY2wvUDBjek5RWi9MSjgzcDNPbDI3L2lV"
            + "TnNxZ0NhR2Y5WnVwdytNClhUNFEybE9ySXcrcVN4NWc3cTlUODNzaU1MdmtENXVF"
            + "WWxVNWRQcWdzT2JMVFc4L2RvVFFyQTE0UmN4Z1k0a0cKYTQrdDVCMWNUKzVWYWdo"
            + "VE9QYjh1VVNFREtqbk9zR2R5OHAyNHdneUs5bjhoMENUU1MyWlE2WnEvUm1RZVQ3"
            + "RApzYmNlVUhoZVErbWtRV0lsanBNUXFzaUJqdzVYWGg0amtFZ2ZBenJiNmdrWUVG"
            + "K1g4UmV1UFp1T1lDNFFqSUVUCnl4OGlmTjRLRTNHSWJNWGVGNExEc0E9PTwvU2ln"
            + "bmF0dXJlVmFsdWU+CiAgICA8S2V5SW5mbz4KICAgICAgPEtleVZhbHVlPgo8UlNB"
            + "S2V5VmFsdWU+CjxNb2R1bHVzPgpvL2N3dlhoYlZZbDBSRFdXdm95ZVpwRVRWWlZW"
            + "Y01Db3ZVVk5nL3N3V2ludU1nRVdnVlFGcnoweEEwNHBFaFhDCkZWdjRldmJVcGVr"
            + "SjVidXFVMWdtUXlPc0NLUWxoT0hUZFBqdmtDNXVwRHFhNTFGbGswVE1hTWtJUWpz"
            + "N2FVS0MKbUE0Ukc0dFRUR0svRWpSMWl4OC9EMGdIWVZSbGR5MVlQck1QK291NzVi"
            + "T1ZuSW9zK0hpZnJBdHJJdjRxRXF3TApMNEZUWkFVcGFDYTJCbWdYZnkyQ1NSUWJ4"
            + "RDVPcjFnY1NhM3Z1cmg1c1BNQ054cWFYbUlYbVFpcFMrRHVFQnFNCk04dGxkYU43"
            + "UllvalVFS3JHVnNOazVpOXkyLzdzam4xenl5VVBmN3ZMNEdnRFlxaEpZV1Y2MURu"
            + "WGd4L0pkNkMKV3h2c25ERjZzY3NjUXpVVEVsK2h5dz09CjwvTW9kdWx1cz4KPEV4"
            + "cG9uZW50PgpBUUFCCjwvRXhwb25lbnQ+CjwvUlNBS2V5VmFsdWU+CjwvS2V5VmFs"
            + "dWU+CiAgICAgIDxYNTA5RGF0YT4KPFg1MDlDZXJ0aWZpY2F0ZT5NSUlFU1RDQ0F6"
            + "R2dBd0lCQWdJQkFqQU5CZ2txaGtpRzl3MEJBUXNGQURCaU1Rc3dDUVlEVlFRR0V3"
            + "SlZVekVMCk1Ba0dBMVVFQ0JNQ1EwRXhGREFTQmdOVkJBY1RDMHh2Y3lCQmJtZGxi"
            + "R1Z6TVJNd0VRWURWUVFLRXdwSlEwRk8KVGlCVVRVTklNUnN3R1FZRFZRUURFeEpK"
            + "UTBGT1RpQlVUVU5JSUZSRlUxUWdRMEV3SGhjTk1UTXdNakE0TURBdwpNREF3V2hj"
            + "Tk1UZ3dNakEzTWpNMU9UVTVXakJzTVFzd0NRWURWUVFHRXdKVlV6RUxNQWtHQTFV"
            + "RUNCTUNRMEV4CkZEQVNCZ05WQkFjVEMweHZjeUJCYm1kbGJHVnpNUmN3RlFZRFZR"
            + "UUtFdzVXWVd4cFpHRjBiM0lnVkUxRFNERWgKTUI4R0ExVUVBeE1ZVm1Gc2FXUmhk"
            + "Rzl5SUZSTlEwZ2dWRVZUVkNCRFJWSlVNSUlCSWpBTkJna3Foa2lHOXcwQgpBUUVG"
            + "QUFPQ0FROEFNSUlCQ2dLQ0FRRUFvL2N3dlhoYlZZbDBSRFdXdm95ZVpwRVRWWlZW"
            + "Y01Db3ZVVk5nL3N3CldpbnVNZ0VXZ1ZRRnJ6MHhBMDRwRWhYQ0ZWdjRldmJVcGVr"
            + "SjVidXFVMWdtUXlPc0NLUWxoT0hUZFBqdmtDNXUKcERxYTUxRmxrMFRNYU1rSVFq"
            + "czdhVUtDbUE0Ukc0dFRUR0svRWpSMWl4OC9EMGdIWVZSbGR5MVlQck1QK291Nwo1"
            + "Yk9WbklvcytIaWZyQXRySXY0cUVxd0xMNEZUWkFVcGFDYTJCbWdYZnkyQ1NSUWJ4"
            + "RDVPcjFnY1NhM3Z1cmg1CnNQTUNOeHFhWG1JWG1RaXBTK0R1RUJxTU04dGxkYU43"
            + "UllvalVFS3JHVnNOazVpOXkyLzdzam4xenl5VVBmN3YKTDRHZ0RZcWhKWVdWNjFE"
            + "blhneC9KZDZDV3h2c25ERjZzY3NjUXpVVEVsK2h5d0lEQVFBQm80SC9NSUg4TUF3"
            + "RwpBMVVkRXdFQi93UUNNQUF3SFFZRFZSME9CQllFRlBaRWNJUWNEL0JqMklGei9M"
            + "RVJ1bzJBREp2aU1JR01CZ05WCkhTTUVnWVF3Z1lHQUZPMC83a0VoM0Z1RUtTK1Ev"
            + "a1lIYUQvVzZ3aWhvV2FrWkRCaU1Rc3dDUVlEVlFRR0V3SlYKVXpFTE1Ba0dBMVVF"
            + "Q0JNQ1EwRXhGREFTQmdOVkJBY1RDMHh2Y3lCQmJtZGxiR1Z6TVJNd0VRWURWUVFL"
            + "RXdwSgpRMEZPVGlCVVRVTklNUnN3R1FZRFZRUURFeEpKUTBGT1RpQlVUVU5JSUZS"
            + "RlUxUWdRMEdDQVFFd0RnWURWUjBQCkFRSC9CQVFEQWdlQU1DNEdBMVVkSHdRbk1D"
            + "VXdJNkFob0IrR0hXaDBkSEE2THk5amNtd3VhV05oYm00dWIzSm4KTDNSdFkyZ3VZ"
            + "M0pzTUEwR0NTcUdTSWIzRFFFQkN3VUFBNElCQVFCMnFTeTd1aSs0M2NlYktVS3dX"
            + "UHJ6ejl5LwpJa3JNZUpHS2pvNDBuKzl1ZWthdzNESjVFcWlPZi9xWjRwakJEKytv"
            + "UjZCSkNiNk5RdVFLd25vQXo1bEU0U3N1Cnk1K2k5M29UM0hmeVZjNGdOTUlvSG0x"
            + "UFMxOWw3REJLcmJ3YnpBZWEvMGpLV1Z6cnZtVjdUQmZqeEQzQVFvMVIKYlU1ZEJy"
            + "NklqYmRMRmxuTzV4MEcwbXJHN3g1T1VQdXVyaWh5aVVScEZEcHdIOEtBSDF3TWND"
            + "cFhHWEZSdEdLawp3eWRneVZZQXR5N290a2wvejNiWmtDVlQzNGdQdkY3MHNSNitR"
            + "eFV5OHUwTHpGNUEvYmVZYVpweFNZRzMxYW1MCkFkWGl0VFdGaXBhSUdlYTlsRUdG"
            + "TTBMOStCZzdYek5uNG5WTFhva3lFQjNiZ1M0c2NHNlF6blgyM0ZHazwvWDUwOUNl"
            + "cnRpZmljYXRlPgo8L1g1MDlEYXRhPgogICAgPC9LZXlJbmZvPgogIDwvU2lnbmF0"
            + "dXJlPgo8L3NtZDpzaWduZWRNYXJrPgo=";
    private static final String SMD_FILE_EXAMPLE = "Marks: Example One\n"
            + "smdID: 1-2\n"
            + "U-labels: example-one, exampleone\n"
            + "notBefore: 2011-08-16 09:00\n"
            + "notAfter: 2012-08-16 09:00\n"
            + "-----BEGIN ENCODED SMD-----\n"
            + ENCODED_SMD_PART
            + "-----END ENCODED SMD-----";

    private static final String TMCH_SIGNED_MARK_DATA_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<smd:signedMark xmlns:smd=\"urn:ietf:params:xml:ns:signedMark-1.0\" id=\"signedMark\">\n"
                    + "  <smd:id>1-2</smd:id>\n"
                    + "  <smd:issuerInfo issuerID=\"2\">\n"
                    + "    <smd:org>Example Inc.</smd:org>\n"
                    + "    <smd:email>support@example.tld</smd:email>\n"
                    + "    <smd:url>http://www.example.tld</smd:url>\n"
                    + "    <smd:voice x=\"1234\">+1.7035555555</smd:voice>\n"
                    + "  </smd:issuerInfo>\n"
                    + "  <smd:notBefore>2009-08-16T09:00:00.0Z</smd:notBefore>\n"
                    + "  <smd:notAfter>2010-08-16T09:00:00.0Z</smd:notAfter>\n"
                    + "  <mark:mark xmlns:mark=\"urn:ietf:params:xml:ns:mark-1.0\">\n"
                    + "    <mark:trademark>\n"
                    + "      <mark:id>1234-2</mark:id>\n"
                    + "      <mark:markName>Example One</mark:markName>\n"
                    + "      <mark:holder entitlement=\"owner\">\n"
                    + "        <mark:name>holderName</mark:name><mark:org>Example Inc.</mark:org>\n"
                    + "        <mark:addr>\n"
                    + "          <mark:street>123 Example Dr.</mark:street>\n"
                    + "          <mark:street>Suite 100</mark:street>\n"
                    + "          <mark:city>Reston</mark:city>\n"
                    + "          <mark:sp>VA</mark:sp>\n"
                    + "          <mark:pc>20190</mark:pc>\n"
                    + "          <mark:cc>US</mark:cc>\n"
                    + "        </mark:addr>\n"
                    + "<mark:voice x=\"1234\">+1.7035555555</mark:voice><mark:fax x=\"1234\">+1.7035555555</mark:fax>"
                    + "      </mark:holder>\n"
                    + "      <mark:contact type=\"owner\">\n"
                    + "        <mark:name>contactName</mark:name><mark:org>Example Inc.</mark:org>\n"
                    + "        <mark:addr>\n"
                    + "          <mark:street>123 Example Dr.</mark:street>\n"
                    + "          <mark:street>Suite 100</mark:street>\n"
                    + "          <mark:city>Reston</mark:city>\n"
                    + "          <mark:sp>VA</mark:sp>\n"
                    + "          <mark:pc>20190</mark:pc>\n"
                    + "          <mark:cc>US</mark:cc>\n"
                    + "        </mark:addr>\n"
                    + "<mark:voice x=\"1234\">+1.7035555555</mark:voice><mark:fax x=\"1234\">+1.7035555555</mark:fax>"
                    + "<mark:email>123@123.com</mark:email>"
                    + "      </mark:contact>\n"
                    + "      <mark:jurisdiction>US</mark:jurisdiction>\n"
                    + "      <mark:class>35</mark:class>\n"
                    + "      <mark:class>36</mark:class>\n"
                    + "      <mark:label>example-one</mark:label>\n"
                    + "      <mark:label>exampleone</mark:label>\n"
                    + "      <mark:goodsAndServices>Dirigendas et eiusmodi\n"
                    + "        featuring infringo in airfare et cartam servicia.\n"
                    + "      </mark:goodsAndServices> \n"
                    + "      <mark:regNum>234235</mark:regNum>\n"
                    + "      <mark:regDate>2009-08-16T09:00:00.0Z</mark:regDate>\n"
                    + "      <mark:exDate>2015-08-16T09:00:00.0Z</mark:exDate>\n"
                    + "    </mark:trademark>\n"
                    + "  </mark:mark>\n"
                    + "  <Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\n"
                    + "    <SignedInfo>\n"
                    + "      <CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n"
                    + "      <SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/>\n"
                    + "      <Reference URI=\"#signedMark\">\n"
                    + "        <Transforms>\n"
                    + "          <Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\n"
                    + "        </Transforms>\n"
                    + "        <DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n"
                    + "        <DigestValue>miF4M2aTd1Y3tKOzJtiyl2VpzAnVPnV1Hq7Zax+yzrA=</DigestValue>\n"
                    + "      </Reference>\n"
                    + "    </SignedInfo>\n"
                    + "    <SignatureValue>MELpHTWEVfG1JcsG1/a//o54OnlJ5A864+X5JwfqgGBBeZSzGHNzwzTKFzIyyyfn\n"
                    + "lGxVwNMoBV5aSvkF7oEKMNVzfcl/P0czNQZ/LJ83p3Ol27/iUNsqgCaGf9Zupw+M\n"
                    + "XT4Q2lOrIw+qSx5g7q9T83siMLvkD5uEYlU5dPqgsObLTW8/doTQrA14RcxgY4kG\n"
                    + "a4+t5B1cT+5VaghTOPb8uUSEDKjnOsGdy8p24wgyK9n8h0CTSS2ZQ6Zq/RmQeT7D\n"
                    + "sbceUHheQ+mkQWIljpMQqsiBjw5XXh4jkEgfAzrb6gkYEF+X8ReuPZuOYC4QjIET\n"
                    + "yx8ifN4KE3GIbMXeF4LDsA==</SignatureValue>\n"
                    + "    <KeyInfo>\n"
                    + "      <KeyValue>\n"
                    + "<RSAKeyValue>\n"
                    + "<Modulus>\n"
                    + "o/cwvXhbVYl0RDWWvoyeZpETVZVVcMCovUVNg/swWinuMgEWgVQFrz0xA04pEhXC\n"
                    + "FVv4evbUpekJ5buqU1gmQyOsCKQlhOHTdPjvkC5upDqa51Flk0TMaMkIQjs7aUKC\n"
                    + "mA4RG4tTTGK/EjR1ix8/D0gHYVRldy1YPrMP+ou75bOVnIos+HifrAtrIv4qEqwL\n"
                    + "L4FTZAUpaCa2BmgXfy2CSRQbxD5Or1gcSa3vurh5sPMCNxqaXmIXmQipS+DuEBqM\n"
                    + "M8tldaN7RYojUEKrGVsNk5i9y2/7sjn1zyyUPf7vL4GgDYqhJYWV61DnXgx/Jd6C\n"
                    + "WxvsnDF6scscQzUTEl+hyw==\n"
                    + "</Modulus>\n"
                    + "<Exponent>\n"
                    + "AQAB\n"
                    + "</Exponent>\n"
                    + "</RSAKeyValue>\n"
                    + "</KeyValue>\n"
                    + "      <X509Data>\n"
                    + "<X509Certificate>MIIESTCCAzGgAwIBAgIBAjANBgkqhkiG9w0BAQsFADBiMQswCQYDVQQGEwJVUzEL\n"
                    + "MAkGA1UECBMCQ0ExFDASBgNVBAcTC0xvcyBBbmdlbGVzMRMwEQYDVQQKEwpJQ0FO\n"
                    + "TiBUTUNIMRswGQYDVQQDExJJQ0FOTiBUTUNIIFRFU1QgQ0EwHhcNMTMwMjA4MDAw\n"
                    + "MDAwWhcNMTgwMjA3MjM1OTU5WjBsMQswCQYDVQQGEwJVUzELMAkGA1UECBMCQ0Ex\n"
                    + "FDASBgNVBAcTC0xvcyBBbmdlbGVzMRcwFQYDVQQKEw5WYWxpZGF0b3IgVE1DSDEh\n"
                    + "MB8GA1UEAxMYVmFsaWRhdG9yIFRNQ0ggVEVTVCBDRVJUMIIBIjANBgkqhkiG9w0B\n"
                    + "AQEFAAOCAQ8AMIIBCgKCAQEAo/cwvXhbVYl0RDWWvoyeZpETVZVVcMCovUVNg/sw\n"
                    + "WinuMgEWgVQFrz0xA04pEhXCFVv4evbUpekJ5buqU1gmQyOsCKQlhOHTdPjvkC5u\n"
                    + "pDqa51Flk0TMaMkIQjs7aUKCmA4RG4tTTGK/EjR1ix8/D0gHYVRldy1YPrMP+ou7\n"
                    + "5bOVnIos+HifrAtrIv4qEqwLL4FTZAUpaCa2BmgXfy2CSRQbxD5Or1gcSa3vurh5\n"
                    + "sPMCNxqaXmIXmQipS+DuEBqMM8tldaN7RYojUEKrGVsNk5i9y2/7sjn1zyyUPf7v\n"
                    + "L4GgDYqhJYWV61DnXgx/Jd6CWxvsnDF6scscQzUTEl+hywIDAQABo4H/MIH8MAwG\n"
                    + "A1UdEwEB/wQCMAAwHQYDVR0OBBYEFPZEcIQcD/Bj2IFz/LERuo2ADJviMIGMBgNV\n"
                    + "HSMEgYQwgYGAFO0/7kEh3FuEKS+Q/kYHaD/W6wihoWakZDBiMQswCQYDVQQGEwJV\n"
                    + "UzELMAkGA1UECBMCQ0ExFDASBgNVBAcTC0xvcyBBbmdlbGVzMRMwEQYDVQQKEwpJ\n"
                    + "Q0FOTiBUTUNIMRswGQYDVQQDExJJQ0FOTiBUTUNIIFRFU1QgQ0GCAQEwDgYDVR0P\n"
                    + "AQH/BAQDAgeAMC4GA1UdHwQnMCUwI6AhoB+GHWh0dHA6Ly9jcmwuaWNhbm4ub3Jn\n"
                    + "L3RtY2guY3JsMA0GCSqGSIb3DQEBCwUAA4IBAQB2qSy7ui+43cebKUKwWPrzz9y/\n"
                    + "IkrMeJGKjo40n+9uekaw3DJ5EqiOf/qZ4pjBD++oR6BJCb6NQuQKwnoAz5lE4Ssu\n"
                    + "y5+i93oT3HfyVc4gNMIoHm1PS19l7DBKrbwbzAea/0jKWVzrvmV7TBfjxD3AQo1R\n"
                    + "bU5dBr6IjbdLFlnO5x0G0mrG7x5OUPuurihyiURpFDpwH8KAH1wMcCpXGXFRtGKk\n"
                    + "wydgyVYAty7otkl/z3bZkCVT34gPvF70sR6+QxUy8u0LzF5A/beYaZpxSYG31amL\n"
                    + "AdXitTWFipaIGea9lEGFM0L9+Bg7XzNn4nVLXokyEB3bgS4scG6QznX23FGk</X509Certificate>\n"
                    + "</X509Data>\n"
                    + "    </KeyInfo>\n"
                    + "  </Signature>\n"
                    + "</smd:signedMark>";

    private TmchXmlParser tmchXMLParser;

    @Before
    public void setUp() throws Exception {
        tmchXMLParser = new TmchXmlParser();
    }

    @Test
    public void shouldExtractBase64EncodedPartFromSmdFile() throws IOException {
        byte[] bytes = tmchXMLParser.extractBase64EncodedPartFromSmdFile(new ByteArrayInputStream(SMD_FILE_EXAMPLE
                .getBytes()));
        assertEquals(ENCODED_SMD_PART, new String(bytes));
    }

    @Test
    public void shouldReturnDecodedSignedMarkData() throws IOException, DecoderException {
        String expectedDecodedSignedMarkData = "encodedSignedMarkData";
        String encodedSignedMarkData = "ZW5jb2RlZFNpZ25lZE1hcmtEYXRh";
        ByteArrayInputStream encodedSignedMarkDataStream = new ByteArrayInputStream(encodedSignedMarkData.getBytes());
        String decoded = new String(tmchXMLParser.decodeSignedMarkData(encodedSignedMarkDataStream));

        assertEquals(decoded, expectedDecodedSignedMarkData);
    }

    @Test
    public void shouldReturnAllObjects() throws Exception {
        SignedMarkData signedMarkData = tmchXMLParser
                .parseDecodedSignedMarkData(new ByteArrayInputStream(TMCH_SIGNED_MARK_DATA_XML.getBytes()));

        assertNotNull(signedMarkData.getMarksList());
        assertNotNull(signedMarkData.getSmdIssuerInfo());
        assertNotNull(signedMarkData.getMarksList().getMarks().get(0));
    }

    @Test
    public void shouldParseEncodedSignedMarkData() throws Exception {
        SignedMarkData signedMarkData =
                tmchXMLParser.parseEncodedSignedMarkData(new ByteArrayInputStream(ENCODED_SMD_PART.getBytes()));
        assertNotNull(signedMarkData.getMarksList());
        assertNotNull(signedMarkData.getSmdIssuerInfo());
        assertNotNull(signedMarkData.getMarksList().getMarks().get(0));
    }
}
