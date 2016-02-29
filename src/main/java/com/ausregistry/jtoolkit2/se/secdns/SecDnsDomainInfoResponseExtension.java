package com.ausregistry.jtoolkit2.se.secdns;

import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Process the &lt;infData&gt; element as documented in RFC5910.
*/
public class SecDnsDomainInfoResponseExtension extends ResponseExtension {

    private static final long serialVersionUID = 5997664658231558327L;

    private static final String DS_DATA_LIST_EXPR = ResponseExtension.EXTENSION_EXPR
        + "/" + SecDNSXMLUtil.SEC_DNS_PREFIX + ":infData/" + SecDNSXMLUtil.SEC_DNS_PREFIX + ":dsData";
    private static final String KEY_DATA_LIST_EXPR = ResponseExtension.EXTENSION_EXPR
    + "/" + SecDNSXMLUtil.SEC_DNS_PREFIX + ":infData/" + SecDNSXMLUtil.SEC_DNS_PREFIX + ":keyData";
    private static final String MAXSIGLIFE_EXPR = ResponseExtension.EXTENSION_EXPR
    + "/" + SecDNSXMLUtil.SEC_DNS_PREFIX + ":infData/" + SecDNSXMLUtil.SEC_DNS_PREFIX + ":maxSigLife";

    private DSOrKeyType infData;

    private boolean initialised;

    @Override
    public void fromXML(final XMLDocument xmlDoc) throws XPathExpressionException {

        infData = new DSOrKeyType();

        if (xmlDoc.getNodeCount("count(" + MAXSIGLIFE_EXPR + ")") > 0) {
            MaxSigLifeType maxSigLife = new MaxSigLifeType();
            maxSigLife.setMaxSigLife(Integer.parseInt(xmlDoc.getNodeValue(MAXSIGLIFE_EXPR)));
            infData.setMaxSigLife(maxSigLife);
        }

        int secDnsCount = getResponseDsData(xmlDoc);
        initialised = (infData.getDsDataList() != null) && (infData.getDsDataList().size() == secDnsCount);
        if (!initialised) {
            secDnsCount = getResponseKeyData(xmlDoc);
            initialised = (infData.getKeyDataList() != null) && (infData.getKeyDataList().size() == secDnsCount);
        }
    }

    private int getResponseDsData(final XMLDocument xmlDoc) throws XPathExpressionException {
        final int secDnsCount = xmlDoc.getNodeCount("count(" + DS_DATA_LIST_EXPR + ")");
        if (secDnsCount > 0) {

            ArrayList<DSData> dsDataList = new ArrayList<DSData>();
            try {
                for (int i = 0; i < secDnsCount; i++) {
                    dsDataList.add(DSData.fromXML(xmlDoc, DS_DATA_LIST_EXPR + "[" + (i + 1) + "]"));
                }
            } catch (final Exception e) {
                dsDataList = null;
                throw new XPathExpressionException(e);
            }
            infData.setDsDataList(dsDataList);
        }
        return secDnsCount;
    }

    private int getResponseKeyData(final XMLDocument xmlDoc) throws XPathExpressionException {
        final int secDnsCount = xmlDoc.getNodeCount("count(" + KEY_DATA_LIST_EXPR + ")");
        if (secDnsCount > 0) {
            ArrayList<KeyData> keyDataList = new ArrayList<KeyData>();
            try {
                for (int i = 0; i < secDnsCount; i++) {
                    keyDataList.add(KeyData.fromXML(xmlDoc, KEY_DATA_LIST_EXPR + "[" + (i + 1) + "]"));
                }
            } catch (final Exception e) {
                keyDataList = null;
                throw new XPathExpressionException(e);
            }
            infData.setKeyDataList(keyDataList);
        }
        return secDnsCount;
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public final DSOrKeyType getInfData() {
        return infData;
    }

}
