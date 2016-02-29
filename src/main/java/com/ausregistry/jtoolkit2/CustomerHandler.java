package com.ausregistry.jtoolkit2;

import java.io.IOException;

/**
 * A trivial subclass of FileHandler intended to provide support for
 * logging messages targeted at customers.  The configuration properties are
 * set via <code>com.ausregistry.jtoolkit2.CustomerHandler.*</code> parameters
 * in the logging parameters file.  See the documentation of the
 * <code>FileHandler</code> for a description of the available parameters.
 */
public class CustomerHandler extends java.util.logging.FileHandler {

    /**
     * Instantiates a new customer handler.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public CustomerHandler() throws IOException {
        super();
    }
}

