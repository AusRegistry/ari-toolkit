package com.ausregistry.jtoolkit2.se;

/**
 * The Extensible Provisioning Protocol defines object-specific commands. Object
 * mappings for EPP map those commands to the defined object. Such objects are
 * identified definitively by namespace URI. Instances of the ObjectType class
 * provide that identification within the toolkit, as well as convenience
 * methods for simplifying the usage of EPP object-specific commands.
 */
public interface ObjectType extends java.io.Serializable {

    /**
     * Get the commonly used name for the object identified by this type, to be
     * used as the prefix. This name should be unique across all extensions and
     * object types.
     */
    String getName();

    /**
     * Get the namespace URI of the object identified by this type. This is the
     * authoritative key for distinguishing between object types.
     */
    String getURI();

    /**
     * Get the schema location hint normally prescribed for this object type.
     */
    String getSchemaLocation();

    /**
     * Get the label name of the primary identifier used in EPP service elements
     * mapped to the object identified by this type.
     */
    String getIdentType();
}
