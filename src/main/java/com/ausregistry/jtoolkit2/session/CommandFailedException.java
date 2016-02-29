package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Thrown to indicate that the server encountered an error unrelated to EPP
 * and was therefore unable to successfully process the associated command.
 * This may or may not be a persistent failure, so retrying the command
 * unchanged may work.  Repeated failures of this kind are likely to indicate
 * a limitation of the server related to the specific command causing this
 * error.
 */
public class CommandFailedException extends Exception {

    private static final long serialVersionUID = 6641607844943105934L;

    public CommandFailedException() {
        super(ErrorPkg.getMessage("epp.server.cmdfail"));
    }

    public CommandFailedException(String message) {
        super(message);
    }
}

