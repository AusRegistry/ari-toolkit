package com.ausregistry.jtoolkit2.se.blocked;

import com.ausregistry.jtoolkit2.se.CommandType;

class BlockedDomainCreateCommandType implements CommandType {
    private static final long serialVersionUID = 1541321760417977584L;

    public String getCommandName() {
		return "create";
	}
}

