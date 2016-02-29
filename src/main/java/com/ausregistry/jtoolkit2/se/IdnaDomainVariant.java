package com.ausregistry.jtoolkit2.se;

import java.io.Serializable;

/**
 * This class models the &lt;variant&gt; element specified in an IDNA EPP Domain
 * Create/Info response, as documented in 'IDN Extension Mapping for the
 * Extensible Provisioning Protocol'.
 */
public final class IdnaDomainVariant implements Comparable<IdnaDomainVariant>,
        Serializable {

    private static final long serialVersionUID = -2441248857298156912L;

    private String name;
    private String userForm;

    /**
     * Constructs an IDNA domain variant
     * @param name
     *            the DNS form of the domain name
     * @param userForm
     *            the user form of the domain name
     */
    public IdnaDomainVariant(final String name, final String userForm) {
        this.userForm = userForm;
        this.name = name;
    }

    /**
     * @return the DNS form of the IDNA domain name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the user form of the IDNA domain name
     */
    public String getUserForm() {
        return userForm;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((userForm == null) ? 0 : userForm.hashCode());

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
     * @see Object#equals(Object)
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

        return (compareTo((IdnaDomainVariant) obj) == 0);
    }

    /**
     * Compares an IdnaDomainVariant based, in order, on the user form and DNS
     * form. Given that DNS form is generated from the user form, these two
     * comparisons should yield identical results.  However, since both values are
     * provided externally this can't be guaranteed, hence both are compared.
     *
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(final IdnaDomainVariant other) {
        if (other == null) {
            throw new NullPointerException();
        }

        int match = compareStrings(userForm, other.userForm);

        if (match == 0) {
            match = compareStrings(name, other.name);
        }

        return match;
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
