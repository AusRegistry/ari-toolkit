package com.ausregistry.jtoolkit2.se;

/**
 * Any classes that implement an EPP command extension should implement this
 * interface. The responsibility of implementor is to construct the subtree
 * under the command extension element.
 */
public interface CommandExtension extends java.io.Serializable {
    String CREATE = "create";
    String UPDATE = "update";

    void addToCommand(Command command);
}
