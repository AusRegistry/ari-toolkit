package com.ausregistry.jtoolkit2.session;

//import java.io.IOException;
//import java.io.FileNotFoundException;

/**
 * A SessionManager is configured based on a SessionManagerProperties
 * instance.  This class presents a guaranteed minimum set of properties to be
 * available to the SessionManager and its SessionPool.
 */
public interface SessionManagerProperties {
    public SessionPoolProperties getSessionPoolProperties();
    public SessionProperties getSessionProperties();
}

