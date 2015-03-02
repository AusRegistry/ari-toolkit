package com.ausregistry.jtoolkit2.se.blocked;

import java.util.GregorianCalendar;

/**
 * This class models a Blocked Domain Name object. Instances are used to access attributes
 * of a Blocked Domain Name information via a blocked info EPP command, the response to
 * which is implemented in the class BlockedInfoResponse.
 */
public class BlockedDomain {
    private final String id;
    private final String domainName;
    private final String registrantContactId;
    private final String clID;
    private final GregorianCalendar crDate;
    private final GregorianCalendar exDate;

    public BlockedDomain(String id, String domainName, String registrantContactId,
                         String clID, GregorianCalendar crDate, GregorianCalendar exDate) {
        this.id = id;
        this.domainName = domainName;
        this.registrantContactId = registrantContactId;
        this.clID = clID;
        this.crDate = crDate;
        this.exDate = exDate;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getRegistrantContactId() {
        return registrantContactId;
    }

    public String getId() {
        return id;
    }

    public String getClID() {
        return clID;
    }

    public GregorianCalendar getCrDate() {
        return crDate;
    }

    public GregorianCalendar getExDate() {
        return exDate;
    }
}
