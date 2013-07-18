package com.ausregistry.jtoolkit2.tmdb.model;

/**
 *  Represents a "classDesc" element in the 'urn:ietf:params:xml:ns:tmNotice-1.0' namespace,
 *  defined in the 'tmNotice-1.0.xsd' schema.
 */
public class TmClaimClassificationDesc {

    private final int classNumber;
    private final String description;

    public TmClaimClassificationDesc(int classNumber, String description) {
        this.classNumber = classNumber;
        this.description = description;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public String getDescription() {
        return description;
    }
}
