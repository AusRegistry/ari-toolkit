package com.ausregistry.jtoolkit2.xml;

/**
 * Container exception for all exceptions which may be thrown while parsing
 * an XML input stream.
 */
public class ParsingException extends Exception {
    private static final long serialVersionUID = -3478292764041060334L;

    public ParsingException(Throwable cause) {
        super(cause);
    }
}

