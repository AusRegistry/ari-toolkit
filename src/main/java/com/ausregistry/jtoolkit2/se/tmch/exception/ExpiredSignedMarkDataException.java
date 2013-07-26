package com.ausregistry.jtoolkit2.se.tmch.exception;

import java.util.Date;

import com.ausregistry.jtoolkit2.ErrorPkg;

public class ExpiredSignedMarkDataException extends RuntimeException {
    private final Date notAfterDate;

    public ExpiredSignedMarkDataException(Date notAfterDate) {
        this.notAfterDate = notAfterDate;
    }


    public Date getNotValidAfterDate() {
        return notAfterDate;
    }

    @Override
    public String getMessage() {
        return ErrorPkg.getMessage("tmch.smd.expired", "<<expiry-date>>", notAfterDate);
    }
}
