package com.ausregistry.jtoolkit2.se.tmch.exception;

import com.ausregistry.jtoolkit2.ErrorPkg;

public class TmchSmdRevokedException extends RuntimeException {
    private final String smdId;

    public TmchSmdRevokedException(String smdId) {
        this.smdId = smdId;
    }

    public String getSmdId() {
        return smdId;
    }

    @Override
    public String getMessage() {
        return ErrorPkg.getMessage("tmch.smd.revoked", "<<id>>", smdId);
    }
}
