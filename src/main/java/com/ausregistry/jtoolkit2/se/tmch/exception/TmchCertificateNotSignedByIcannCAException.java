package com.ausregistry.jtoolkit2.se.tmch.exception;

import java.security.cert.CertPathValidatorException;
import java.security.cert.X509Certificate;

import com.ausregistry.jtoolkit2.ErrorPkg;

public class TmchCertificateNotSignedByIcannCAException extends RuntimeException {
    private final X509Certificate certificate;

    public TmchCertificateNotSignedByIcannCAException(X509Certificate certificate, CertPathValidatorException e) {
        super(e);
        this.certificate = certificate;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    @Override
    public String getMessage() {
        return ErrorPkg.getMessage("tmch.smd.cert.notSignedByIcannCA", "<<cert-detailed-msg>>", certificate);
    }
}
