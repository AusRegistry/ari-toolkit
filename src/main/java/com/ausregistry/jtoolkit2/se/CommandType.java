package com.ausregistry.jtoolkit2.se;

/**
 * Each EPP command is identified by an instance of CommandType.  A CommandType
 * has a command name, which is the name of the corresponding EPP command, and
 * a response name, which is the name of the specific EPP response appropriate
 * to this command type, if any.
 *
 */
public interface CommandType extends java.io.Serializable {
    /**
     * Get the command name, as described above.
     */
    String getCommandName();
}

