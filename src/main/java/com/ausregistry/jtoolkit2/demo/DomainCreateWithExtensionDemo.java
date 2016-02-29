package com.ausregistry.jtoolkit2.demo;

import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.DomainCreateResponse;
import com.ausregistry.jtoolkit2.se.ResultCode;
import com.ausregistry.jtoolkit2.se.secdns.DSData;
import com.ausregistry.jtoolkit2.se.secdns.DSOrKeyType;
import com.ausregistry.jtoolkit2.se.secdns.SecDnsDomainCreateCommandExtension;
import com.ausregistry.jtoolkit2.session.SessionManager;
import com.ausregistry.jtoolkit2.session.SessionManagerFactory;
import com.ausregistry.jtoolkit2.session.SessionManagerProperties;
import com.ausregistry.jtoolkit2.session.SessionManagerPropertiesImpl;
import com.ausregistry.jtoolkit2.session.Transaction;

/**
 * A demonstration of the steps required to perform a domain create utilising the SECDNS extension.
 */
public class DomainCreateWithExtensionDemo {

    private static final String USAGE = "Must be run with the following parameters: \"Domain Name for create\" "
            + "\"Password\" \"Contact User ID\"";
    private final SessionManager manager;
    private final SessionManagerProperties properties;

    public DomainCreateWithExtensionDemo() throws Exception {
        // Read in configuration properties from the toolkit.properties file
        properties = new SessionManagerPropertiesImpl("toolkit.properties");

        // Create a new session manager. This will use the properties loaded above to set up parameters
        // required to connect to an EPP server.
        manager = SessionManagerFactory.newInstance(properties);
    }

    public static void main(String[] args) {
        try {
            // Instantiate the demo class, creating a session manager
            final DomainCreateWithExtensionDemo demo = new DomainCreateWithExtensionDemo();
            if (args.length != 3) {
                System.err.println(USAGE);
                System.exit(1);
            }
            // Parse command line arguments
            final String domainName = args[0];
            final String password = args[1];
            final String contactName = args[2];

            // Run the demo using the command line arguments
            demo.runDemo(domainName, password, contactName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runDemo(final String domainName, String password, String contactName) throws Exception {
        // Start the session manager. This will automatically create a connection, send a hello and a
        // greeting and perform a login. The manager will be ready to execute transactions after this call.
        manager.startup();

        // Create the domain create response.
        final DomainCreateResponse domainCreateResponse = new DomainCreateResponse();

        // Create a domain create command with the minimum required parameters
        final DomainCreateCommand command = new DomainCreateCommand(domainName, password, contactName,
                new String[] {contactName});

        // Create a SECDNS create command extension object
        final SecDnsDomainCreateCommandExtension ext = new SecDnsDomainCreateCommandExtension();

        // Create a DS data object, supplying key tag, algorithm, digest type and digest
        final DSData dsData = new DSData(1, 3, 1, "49FD46E6C4B45C55D4AC49FD46E6C4B45C55D4AC");

        // Add the DS data to to a DSOrKeyType object, which will store all DS and Key data for a domain
        final DSOrKeyType createData = new DSOrKeyType();

        //Add the DS data to the DSOrKeyType object
        createData.addToDsData(dsData);

        // Add the DSOrKeyType object to the extension
        ext.setCreateData(createData);

        // Add the extension to the domain create command
        command.appendExtension(ext);

        // Tell the manager to execute the command. This command includes the SECDNS extension object.
        manager.execute(new Transaction(command, domainCreateResponse));

        // Obtain the result code, and print relevant data if it is successful
        final int resultCode = domainCreateResponse.getResults()[0].getResultCode();
        System.out.println("Domain create response code: " + resultCode);
        System.out.println("Domain create response message: "
                + domainCreateResponse.getResults()[0].getResultMessage());
        if (resultCode == ResultCode.SUCCESS || resultCode == ResultCode.SUCCESS_ACT_PEND) {
            System.out.println("Domain created on: " + domainCreateResponse.getCreateDate().getTime());
            System.out.println("Domain expiry on: " + domainCreateResponse.getExpiryDate().getTime());
        }

        // End the session, disconnecting the socket connection as well
        manager.shutdown();
    }
}
