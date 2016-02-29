package com.ausregistry.jtoolkit2.se;

import java.io.Serializable;

/**
 * This class models the &lt;variant&gt; element specified in an IDN EPP Domain
 * Create/Info response, as documented in 'IDN Extension Mapping for the
 * Extensible Provisioning Protocol'.
 */
public final class IdnDomainVariant implements Comparable<IdnDomainVariant>,
        Serializable {

    private static final long serialVersionUID = -2441248857298156912L;

    private String name;

    /**
     * Constructs an IDN domain variant
     * @param name
     *            the DNS form of the domain name
     */
    public IdnDomainVariant(final String name) {
        this.name = name;
    }

    /**
     * @return the DNS form of the IDN domain name
     */
    public String getName() {
        return name;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        /*
         * Given that DNS form is generated from the user form, adding its hash
         * code generally does not serve to differentiate the overall hash
         * value. However, since both values are provided externally this can't
         * be guaranteed, hence both are used.
         */
        result = prime * result + ((name == null) ? 0 : name.hashCode());

        return result;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        return (compareTo((IdnDomainVariant) obj) == 0);
    }

    /**
     * Compares an IdnDomainVariant based, in order, on the user form and DNS
     * form. Given that DNS form is generated from the user form, these two
     * comparisons should yield identical results.  However, since both values are
     * provided externally this can't be guaranteed, hence both are compared.
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final IdnDomainVariant other) {
        if (other == null) {
            throw new NullPointerException();
        }

        return compareStrings(name, other.name);
    }

    private int compareStrings(final String mine, final String other) {
        int match = 0;

        if (mine != null) {
            if (other != null) {
                match = mine.compareTo(other);
            } else {
                match = 1;
            }
        } else {
            if (other != null) {
                match = -1;
            }
        }
        return match;
    }
}
