package com.ausregistry.jtoolkit2;

import java.io.IOException;
import java.util.logging.FileHandler;

/**
 * A trivial subclass of FileHandler intended to provide support for
 * configuration specific to debug logging.  The configuration properties
 * are set via <code>com.ausregistry.jtoolkit2.DebugHandler.*</code>
 * parameters in the logging parameters file.  See the documentation of the
 * <code>FileHandler</code> for a description of the available parameters.
 */
public class DebugHandler extends FileHandler {
    public DebugHandler() throws IOException {
        super();
    }
}

