package com.ausregistry.jtoolkit2.se.tmch.exception;

import com.ausregistry.jtoolkit2.ErrorPkg;

public class SmdSignatureMissingException extends RuntimeException {
    @Override
    public String getMessage() {
        return ErrorPkg.getMessage("tmch.smd.signature.missing");
    }
}
