package com.ausregistry.jtoolkit2.se.tmch.exception;

import java.util.Date;

import com.ausregistry.jtoolkit2.ErrorPkg;

public class NotYetValidSignedMarkDataException extends RuntimeException {
    private final Date notBeforeDate;

    public NotYetValidSignedMarkDataException(Date notBeforeDate) {
        this.notBeforeDate = notBeforeDate;
    }

    public Date getValidFromDate() {
        return notBeforeDate;
    }

    @Override
    public String getMessage() {
        return ErrorPkg.getMessage("tmch.smd.notYetValid", "<<valid-from-date>>", notBeforeDate);
    }
}
