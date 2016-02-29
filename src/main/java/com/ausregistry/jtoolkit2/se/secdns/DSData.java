package com.ausregistry.jtoolkit2.se.secdns;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Models the &lt;dsData&gt; element as documented in RFC5910.
 */
public class DSData implements Serializable {

    private static final long serialVersionUID = 3729382972073559741L;

    private static final String SEC_DNS_PREFIX = ExtendedObjectType.SEC_DNS.getName();
    private static final Pattern DIGEST_VALIDATION_PATTERN = Pattern.compile("^[A-Fa-f0-9]+$");

    private int keyTag;
    private int alg;
    private int digestType;
    private String digest;
    private KeyData keyData;

    public DSData() {
    }

    public DSData(final int keyTagArg, final int algArg, final int digestTypeArg, final String digestArg) {
        setKeyTag(keyTagArg);
        setAlg(algArg);
        setDigestType(digestTypeArg);
        setDigest(digestArg);
    }

    public static final DSData fromXML(final XMLDocument xmlDoc, final String dsDataXPath) throws Exception {
        final DSData result = new DSData();

        result.setKeyTag(Integer.parseInt(xmlDoc.getNodeValue(dsDataXPath + "/" + SEC_DNS_PREFIX + ":keyTag")));
        result.setAlg(Integer.parseInt(xmlDoc.getNodeValue(dsDataXPath + "/" + SEC_DNS_PREFIX + ":alg")));
        result.setDigestType(Integer.parseInt(xmlDoc.getNodeValue(dsDataXPath + "/" + SEC_DNS_PREFIX + ":digestType")));
        result.setDigest(xmlDoc.getNodeValue(dsDataXPath + "/" + SEC_DNS_PREFIX + ":digest"));
        setKeyDataInDSData(xmlDoc, dsDataXPath, result);

        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + alg;
        result = prime * result + ((digest == null) ? 0 : digest.hashCode());
        result = prime * result + digestType;
        result = prime * result + keyTag;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DSData other = (DSData) obj;
        if (alg != other.alg) {
            return false;
        }
        if (digest == null) {
            if (other.digest != null) {
                return false;
            }
        } else if (!digest.equals(other.digest)) {
            return false;
        }
        if (digestType != other.digestType) {
            return false;
        }
        if (keyTag != other.keyTag) {
            return false;
        }
        if (keyData != null && !keyData.equals(other.getKeyData())) {
            return false;
        }
        return true;
    }

    private static void setKeyDataInDSData(final XMLDocument xmlDoc, final String dsDataXPath,
            final DSData result) throws Exception {
        final Node tempNode = xmlDoc.getElement(dsDataXPath + "/" + SEC_DNS_PREFIX + ":keyData");
        if (tempNode != null) {
            result.setKeyData(KeyData.fromXML(xmlDoc, dsDataXPath + "/" + SEC_DNS_PREFIX + ":keyData"));
        }
    }

    public static final void appendDsDataElement(final XMLWriter xmlWriter, final Element parentElement,
            final DSData dsData) {
        final Element dsDataElement = SecDNSXMLUtil.createElement(xmlWriter, parentElement, "dsData");
        SecDNSXMLUtil.appendChildElement(xmlWriter, dsDataElement, "keyTag", dsData.getKeyTag());
        SecDNSXMLUtil.appendChildElement(xmlWriter, dsDataElement, "alg", dsData.getAlg());
        SecDNSXMLUtil.appendChildElement(xmlWriter, dsDataElement, "digestType", dsData.getDigestType());
        SecDNSXMLUtil.appendChildElement(xmlWriter, dsDataElement, "digest", dsData.getDigest());
        if (dsData.getKeyData() != null) {
            KeyData.appendKeyDataElement(xmlWriter, dsDataElement, dsData.getKeyData());
        }
    }

    public int getKeyTag() {
        return keyTag;
    }

    /**
     * @throws IllegalArgumentException if {@code keyTagArg} is outside of the range 0...65535
     */
    public void setKeyTag(final int keyTagArg) {
        if (keyTagArg < 0) {
            throw new IllegalArgumentException();
        }
        if (keyTagArg > 65535) {
            throw new IllegalArgumentException();
        }
        this.keyTag = keyTagArg;
    }

    public int getAlg() {
        return alg;
    }

    /**
     * @throws IllegalArgumentException if {@code algArg} is outside of the range 0...255
     */
    public void setAlg(final int algArg) {
        if (algArg < 0) {
            throw new IllegalArgumentException();
        }
        if (algArg > 255) {
            throw new IllegalArgumentException();
        }
        this.alg = algArg;
    }

    public int getDigestType() {
        return digestType;
    }

    /**
     * @throws IllegalArgumentException if {@code digestTypeArg} is outside of the range 0...255
     */
    public void setDigestType(final int digestTypeArg) {
        if (digestTypeArg < 0) {
            throw new IllegalArgumentException();
        }
        if (digestTypeArg > 255) {
            throw new IllegalArgumentException();
        }
        this.digestType = digestTypeArg;
    }

    public String getDigest() {
        return digest;
    }

    /**
     * @throws IllegalArgumentException if {@code digestArg} does not resemble a valid digest, or if
     * the digest has an odd number of characters.
     */
    public void setDigest(final String digestArg) {
        final Matcher matcher = DIGEST_VALIDATION_PATTERN.matcher(digestArg);
        if (matcher.matches() && digestArg.length() % 2 == 0) {
            this.digest = digestArg.toUpperCase();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public KeyData getKeyData() {
        return keyData;
    }

    public void setKeyData(final KeyData keyDataArg) {
        this.keyData = keyDataArg;
    }

}
