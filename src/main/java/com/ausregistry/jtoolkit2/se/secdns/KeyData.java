package com.ausregistry.jtoolkit2.se.secdns;

import java.io.Serializable;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import com.ausregistry.jtoolkit2.xml.XMLWriter;

/**
 * Models the &lt;keyData&gt; element as documented in RFC5910.
 */
public class KeyData implements Serializable {

    private static final long serialVersionUID = -5073494654814738965L;

    private static final String SEC_DNS_PREFIX = ExtendedObjectType.SEC_DNS.getName();

    private int alg;
    private int flags;
    private int protocol;
    private String pubKey;

    /**
     * Instantiates a new key data.
     */
    public KeyData() { }

    /**
     * Instantiates a new key data.
     *
     * @param flagsArg the flags
     * @param protocolArg the protocol
     * @param algArg the algorithm
     * @param pubKeyArg the public key
     */
    public KeyData(final int flagsArg, final int protocolArg, final int algArg, final String pubKeyArg) {
        setFlags(flagsArg);
        setProtocol(protocolArg);
        setAlg(algArg);
        setPubKey(pubKeyArg);
    }

    /**
     * From converts the XML document to a KeyData Object.
     *
     * @param xmlDoc the XML doc
     * @param keyDataXPath the key data XPATH
     * @return the key data
     * @throws Exception the exception
     */
    public static final KeyData fromXML(final XMLDocument xmlDoc, final String keyDataXPath) throws Exception {
        final KeyData result = new KeyData();

        result.setFlags(Integer.parseInt(xmlDoc.getNodeValue(keyDataXPath + "/" + SEC_DNS_PREFIX + ":flags")));
        result.setProtocol(Integer.parseInt(xmlDoc.getNodeValue(keyDataXPath + "/" + SEC_DNS_PREFIX + ":protocol")));
        result.setAlg(Integer.parseInt(xmlDoc.getNodeValue(keyDataXPath + "/" + SEC_DNS_PREFIX + ":alg")));
        result.setPubKey(xmlDoc.getNodeValue(keyDataXPath + "/" + SEC_DNS_PREFIX + ":pubKey"));
        return result;
    }

    /**
     * Append key data element.
     *
     * @param xmlWriter the XML writer
     * @param parentElement the parent element
     * @param keyData the key data
     */
    public static final void appendKeyDataElement(final XMLWriter xmlWriter,
            final Element parentElement, final KeyData keyData) {

        final Element keyDataElement = SecDNSXMLUtil.createElement(xmlWriter, parentElement, "keyData");
        SecDNSXMLUtil.appendChildElement(xmlWriter, keyDataElement, "flags", keyData.getFlags());
        SecDNSXMLUtil.appendChildElement(xmlWriter, keyDataElement, "protocol", keyData.getProtocol());
        SecDNSXMLUtil.appendChildElement(xmlWriter, keyDataElement, "alg", keyData.getAlg());
        SecDNSXMLUtil.appendChildElement(xmlWriter, keyDataElement, "pubKey", keyData.getPubKey());
    }

    public int getFlags() {
        return flags;
    }

    /**
     * Sets the flags and validates the value is unsigned short.
     *
     * @param flagsArg the new flags
     * @throws IllegalArgumentException if {@code flagsArg} are outside of the range 0...65535
     */
    public void setFlags(final int flagsArg) {
        if (flagsArg < 0) {
            throw new IllegalArgumentException();
        }
        if (flagsArg > 65535) {
            throw new IllegalArgumentException();
        }
        this.flags = flagsArg;
    }

    public int getProtocol() {
        return protocol;
    }

    /**
     * Sets the protocol and validates the value is unsigned byte.
     *
     * @param protocolArg the new protocol
     * @throws IllegalArgumentException if {@code protocolArg} is outside of the range 0...255
     */
    public void setProtocol(final int protocolArg) {
        if (protocolArg < 0) {
            throw new IllegalArgumentException();
        }
        if (protocolArg > 255) {
            throw new IllegalArgumentException();
        }
        this.protocol = protocolArg;
    }

    public int getAlg() {
        return alg;
    }

    /**
     * Sets the algorithm and validates the value is an unsigned byte.
     *
     * @param algArg the new algorithm
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

    public String getPubKey() {
        return pubKey;
    }

    /**
     * Sets the pub key and validates the value is not a zero length string.
     *
     * @param pubKeyArg the new pub key
     * @throws IllegalArgumentException if {@code pubKeyArg} is empty.
     */
    public void setPubKey(final String pubKeyArg) {
        if (pubKeyArg.length() < 1) {
            throw new IllegalArgumentException();
        }
        this.pubKey = pubKeyArg;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + alg;
        result = prime * result + flags;
        result = prime * result + protocol;
        result = prime * result + ((pubKey == null) ? 0 : pubKey.hashCode());
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
        KeyData other = (KeyData) obj;
        if (alg != other.alg) {
            return false;
        }
        if (flags != other.flags) {
            return false;
        }
        if (protocol != other.protocol) {
            return false;
        }
        if (pubKey == null) {
            if (other.pubKey != null) {
                return false;
            }
        } else if (!pubKey.equals(other.pubKey)) {
            return false;
        }
        return true;
    }

}
