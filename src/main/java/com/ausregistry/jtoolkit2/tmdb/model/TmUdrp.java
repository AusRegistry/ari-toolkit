package com.ausregistry.jtoolkit2.tmdb.model;

/**
 *  Represents the "udrp" child element of "notExactMatch" element in the 'urn:ietf:params:xml:ns:tmNotice-1.0'
 *  namespace, defined in the 'tmNotice-1.0.xsd' schema.
 */
public class TmUdrp {
    private String caseNumber;
    private String udrpProvider;

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public void setUdrpProvider(String udrpProvider) {
        this.udrpProvider = udrpProvider;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public String getUdrpProvider() {
        return udrpProvider;
    }
}
