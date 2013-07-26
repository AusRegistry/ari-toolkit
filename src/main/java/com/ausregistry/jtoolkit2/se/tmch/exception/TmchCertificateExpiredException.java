package com.ausregistry.jtoolkit2.se.tmch.exception;

import com.ausregistry.jtoolkit2.ErrorPkg;

import java.security.cert.CertPathValidatorException;
import java.util.Date;

public class TmchCertificateExpiredException extends RuntimeException {

    private Date notValidAfterDate;

    public TmchCertificateExpiredException(Date notValidAfterDate, CertPathValidatorException e) {
           super(e);
        this.notValidAfterDate = notValidAfterDate;
    }

    public Date getNotValidAfterDate() {
        return notValidAfterDate;
    }

    @Override
    public String getMessage() {
        return ErrorPkg.getMessage("tmch.smd.certificate.expired", "<<expiry-date>>", notValidAfterDate);
    }
}
