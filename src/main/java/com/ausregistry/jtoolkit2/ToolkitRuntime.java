package com.ausregistry.jtoolkit2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Provides global toolkit initialisation. At least one class used by the application must refer to this class by
 * invoking the init method.
 *
 * Essentially the will create a jtkenv.log file if the env logger is defined that contains the system properties and
 * the local host IP address.
 */
public final class ToolkitRuntime {
    static {
        initialise();
    }

    private ToolkitRuntime() {
        // intentionally do nothing, make checkstyle happy
    }

    /**
     * The method that initialises the ToolkitRuntime environment logger.
     */
    public static void init() {
    }

    private static synchronized void initialise() {
        String pname = ToolkitRuntime.class.getPackage().getName();
        Logger envLogger = Logger.getLogger(pname + ".env");
        for (Handler hndlr : envLogger.getHandlers()) {
            envLogger.removeHandler(hndlr);
        }

        Handler handler;
        try {
            handler = new FileHandler("%t/jtkenv.log", 8192, 1, false);
            handler.setFormatter(new SimpleFormatter());
            handler.setLevel(Level.INFO);
        } catch (java.io.IOException ioe) {
            handler = new ConsoleHandler();
        }
        envLogger.setLevel(Level.INFO);
        envLogger.addHandler(handler);

        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException uhe) {
            localhost = "no internet address";
        }

        StringBuilder builder = new StringBuilder("\nhost address = " + localhost + "\n");
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            String prop = entry.getKey().toString() + " = " + entry.getValue().toString() + "\n";
            builder.append(prop);
        }
        envLogger.info(builder.toString());
    }
}
