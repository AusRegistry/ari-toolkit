package com.ausregistry.jtoolkit2.se;

/**
 * Any classes that implement an EPP command extension should implement this
 * interface. The responsibility of implementor is to construct the subtree
 * under the command extension element.
 */
public interface CommandExtension extends java.io.Serializable {
    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    public void addToCommand(Command command) throws Exception;
}
