package com.ausregistry.jtoolkit2.tmdb.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Represents a notice from the TradeMark DataBase, represented by the "notice" element in the
 * 'urn:ietf:params:xml:ns:tmNotice-1.0' namespace, defined in the 'tmNotice-1.0.xsd' schema.
 */
public class TmNotice {

    private String id;
    private GregorianCalendar notBeforeDateTime;
    private GregorianCalendar notAfterDateTime;
    private List<TmClaim> claims = new ArrayList<TmClaim>();
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNotBeforeDateTime(GregorianCalendar notBeforeDateTime) {
        this.notBeforeDateTime = notBeforeDateTime;
    }

    public GregorianCalendar getNotBeforeDateTime() {
        return notBeforeDateTime;
    }

    public void setNotAfterDateTime(GregorianCalendar notAfterDateTime) {
        this.notAfterDateTime = notAfterDateTime;
    }

    public GregorianCalendar getNotAfterDateTime() {
        return notAfterDateTime;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public List<TmClaim> getClaims() {
        return claims;
    }

    public void addClaim(TmClaim claim) {
        claims.add(claim);
    }
}
