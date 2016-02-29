package com.ausregistry.jtoolkit2.se.generic;

import com.ausregistry.jtoolkit2.se.CommandType;

/**
 * The base class for the Domain Registrant Transfer Command.
 */
public class DomainRegistrantTransferCommandType implements CommandType {

    private static final long serialVersionUID = 135465710957937789L;

    @Override
    public String getCommandName() {
        return "registrantTransfer";
    }
}
