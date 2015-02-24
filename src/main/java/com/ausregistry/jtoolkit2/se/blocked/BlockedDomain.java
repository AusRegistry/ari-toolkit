package com.ausregistry.jtoolkit2.se.blocked;

import java.math.BigDecimal;

/**
 * This class models a Blocked Domain Name object. Instances are used to access attributes
 * of a Blocked Domain Name information via a blocked create EPP command, the response to
 * which is implemented in the class BlockedCreateResponse.
 */
public class BlockedDomain {
    private final String registrantContactId;
    private final String id;
    private final String domainName;

    public BlockedDomain(String id, String domainName, String registrantContactId) {
        this.id = id;
        this.domainName = domainName;
        this.registrantContactId = registrantContactId;
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

}
