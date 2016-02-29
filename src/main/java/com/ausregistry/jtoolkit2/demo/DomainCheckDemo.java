package com.ausregistry.jtoolkit2.demo;

import com.ausregistry.jtoolkit2.se.DomainCheckCommand;
import com.ausregistry.jtoolkit2.se.DomainCheckResponse;
import com.ausregistry.jtoolkit2.session.SessionManager;
import com.ausregistry.jtoolkit2.session.SessionManagerFactory;
import com.ausregistry.jtoolkit2.session.SessionManagerProperties;
import com.ausregistry.jtoolkit2.session.SessionManagerPropertiesImpl;
import com.ausregistry.jtoolkit2.session.Transaction;

/**
 * A basic demonstration of the life cycle of a SessionManager using a Domain Check Command.
 */
public class DomainCheckDemo {
    private static final String USAGE = "Must be run with the following parameters: \"Domain Name\"";

    private final SessionManager manager;
    private final SessionManagerProperties properties;

    public DomainCheckDemo() throws Exception {
        // Read in configuration properties from the toolkit.properties file
        properties = new SessionManagerPropertiesImpl("toolkit.properties");

        // Create a new session manager. This will use the properties loaded above to set up parameters
        // required to connect to an EPP server.
        manager = SessionManagerFactory.newInstance(properties);
    }

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.err.println(USAGE);
                System.exit(1);
            }

            // Instantiate the demo class, creating a session manager
            final DomainCheckDemo demo = new DomainCheckDemo();

            // Run the demo using the command line arguments
            demo.runDemo(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runDemo(final String domainName) throws Exception {
        // Start the session manager. This will automatically create a connection, send a hello and a
        // greeting and perform a login. The manager will be ready to execute transactions after this call.
        manager.startup();

        // Create the required response object for the domain check
        final DomainCheckResponse response = new DomainCheckResponse();

        // Execute the command using the session manager, wrapping it in a Transaction object
        manager.execute(new Transaction(new DomainCheckCommand(domainName), response));

        // Print out the details of the response
        System.out.println("EPP Response code: " + response.getResults()[0].getResultCode());
        System.out.println("Domain Check contains Domain Name: " + response.getNameIDs().contains(domainName));
        System.out.println("Domain Name is available: " + response.isAvailable(domainName));
        System.out.println("Reason Domain Name is available or not: " + response.getReason(0));

        // End the session, disconnecting the socket connection as well
        manager.shutdown();
    }
}
