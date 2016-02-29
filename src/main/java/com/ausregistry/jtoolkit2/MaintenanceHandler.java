package com.ausregistry.jtoolkit2;

import java.io.IOException;
import java.util.logging.FileHandler;

/**
 * A trivial subclass of FileHandler intended to provide support for
 * configuration specific to maintenance logging.  The configuration properties
 * are set via <code>com.ausregistry.jtoolkit2.MaintenanceHandler.*</code>
 * parameters in the logging parameters file.  See the documentation of the
 * <code>FileHandler</code> for a description of the available parameters.
 */
public class MaintenanceHandler extends FileHandler {
    public MaintenanceHandler() throws IOException {
        super();
    }
}

