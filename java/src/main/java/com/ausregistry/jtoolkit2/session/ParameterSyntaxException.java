package com.ausregistry.jtoolkit2.session;

/**
 * There was a syntax error in the value of a parameter in the EPP command
 * service element.
 */
public class ParameterSyntaxException extends Exception {
    private static final long serialVersionUID = 1916839915952352115L;
    private String msg;

    public ParameterSyntaxException(String errorMessage) {
        msg = errorMessage;
    }

    public String getMessage() {
        return msg;
    }

    public String toString() {
        return "Parameter syntax exception";
    }
}

