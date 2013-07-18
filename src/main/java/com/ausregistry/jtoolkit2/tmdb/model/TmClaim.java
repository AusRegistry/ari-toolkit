package com.ausregistry.jtoolkit2.tmdb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Trademark Claim, represented by the "claim" element in the
 * 'urn:ietf:params:xml:ns:tmNotice-1.0' namespace, defined in the 'tmNotice-1.0.xsd' schema.
 */
public class TmClaim {
    private String markName;
    private String jurisdiction;
    private String jurisdictionCC;
    private String goodsAndServices;
    private List<TmClaimClassificationDesc> classificationDescriptions = new ArrayList<TmClaimClassificationDesc>();
    private List<TmHolder> holders = new ArrayList<TmHolder>();
    private List<TmContact> contacts = new ArrayList<TmContact>();
    private List<TmUdrp> udrps = new ArrayList<TmUdrp>();
    private List<TmCourt> courts = new ArrayList<TmCourt>();

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public void setJurisdictionCC(String jurisdictionCC) {
        this.jurisdictionCC = jurisdictionCC;
    }

    public void setGoodsAndServices(String goodsAndServices) {
        this.goodsAndServices = goodsAndServices;
    }

    public void addClassificationDesc(TmClaimClassificationDesc claimClassificationDesc) {
        classificationDescriptions.add(claimClassificationDesc);
    }

    public void addHolder(TmHolder tmHolder) {
        holders.add(tmHolder);
    }

    public void addContact(TmContact tmContact) {
        contacts.add(tmContact);
    }

    public void addUdrp(TmUdrp tmUdrp) {
        udrps.add(tmUdrp);
    }

    public void addCourt(TmCourt tmCourt) {
        courts.add(tmCourt);
    }

    public String getMarkName() {
        return markName;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public String getJurisdictionCC() {
        return jurisdictionCC;
    }

    public String getGoodsAndServices() {
        return goodsAndServices;
    }

    public List<TmClaimClassificationDesc> getClassificationDescriptions() {
        return classificationDescriptions;
    }

    public List<TmHolder> getHolders() {
        return holders;
    }

    public List<TmContact> getContacts() {
        return contacts;
    }

    public List<TmUdrp> getUdrps() {
        return udrps;
    }

    public List<TmCourt> getCourts() {
        return courts;
    }
}
