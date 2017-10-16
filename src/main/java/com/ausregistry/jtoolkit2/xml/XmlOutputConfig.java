package com.ausregistry.jtoolkit2.xml;


public final class XmlOutputConfig {

    /* Enable the output of XML namespace prefix when the flag is set to true.
     */
    private boolean prefixNamespace;

    private XmlOutputConfig(boolean prefixNamespace) {
        this.prefixNamespace = prefixNamespace;
    }

    public static XmlOutputConfig defaultConfig() {
        return new XmlOutputConfig(false);
    }

    public static XmlOutputConfig prefixAllNamespaceConfig() {
        return new XmlOutputConfig(true);
    }

    public boolean needsPrefixNamespace() {
        return prefixNamespace;
    }
}
