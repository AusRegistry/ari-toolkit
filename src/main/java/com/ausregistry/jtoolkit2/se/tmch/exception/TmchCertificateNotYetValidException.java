package com.ausregistry.jtoolkit2.se.tmch.exception;

import java.security.cert.CertPathValidatorException;
import java.util.Date;

import com.ausregistry.jtoolkit2.ErrorPkg;

public class TmchCertificateNotYetValidException extends RuntimeException {
    private final Date validFromDate;

    public TmchCertificateNotYetValidException(Date validFromDate, CertPathValidatorException e) {
        super(e);
        this.validFromDate = validFromDate;
    }

    public Date getValidFromDate() {
        return validFromDate;
    }

    @Override
    public String getMessage() {
        return ErrorPkg.getMessage("tmch.smd.cert.notYetValid", "<<valid-from-date>>", validFromDate);
    }
}
