package com.ausregistry.jtoolkit2.se;

/**
 * This class models EPP object statuses.  Instances of this class can be used
 * to update object statuses and are also returned by subclasses of
 * InfoResponse to provide access to the attributes of status values of the
 * queried object.
 *
 * @see com.ausregistry.jtoolkit2.se.UpdateCommand
 * @see com.ausregistry.jtoolkit2.se.InfoResponse
 */
public class Status implements java.io.Serializable {
    private static final long serialVersionUID = -7773747306050766351L;

    private String status, rationale, lang;

    public Status(String status) {
        this(status, null);
    }

    public Status(String status, String rationale) {
        this.status = status;
        this.rationale = rationale;
    }

    public Status(String status, String rationale, String lang) {
        this(status, rationale);
        this.lang = lang;
    }

    public String toString() {
        return status;
    }

    public String getRationale() {
        return rationale;
    }

    public String getLanguage() {
        return lang;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }

        Status s = (Status) obj;
        return s.toString().equals(status);
    }

    public int hashCode() {
        if (status == null) {
            return 0;
        }

        return status.hashCode();
    }
}

