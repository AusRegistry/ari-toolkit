package com.ausregistry.jtoolkit2.se;

/**
 * An enumeration of the standard commands defined in RFC5730.
 */
public enum StandardCommandType implements CommandType {
    LOGIN("login"),
    LOGOUT("logout"),
    POLL("poll"),
    CHECK("check"),
    INFO("info"),
    CREATE("create"),
    DELETE("delete"),
    UPDATE("update"),
    TRANSFER("transfer"),
    RENEW("renew");

    private String name;

    StandardCommandType(String name) {
        this.name = name;
    }

    @Override
    public String getCommandName() {
        return name;
    }
}

