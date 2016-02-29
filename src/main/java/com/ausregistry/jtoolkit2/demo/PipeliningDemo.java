package com.ausregistry.jtoolkit2.demo;

import com.ausregistry.jtoolkit2.se.ContactCheckCommand;
import com.ausregistry.jtoolkit2.se.ContactCheckResponse;
import com.ausregistry.jtoolkit2.se.DomainCheckCommand;
import com.ausregistry.jtoolkit2.se.DomainCheckResponse;
import com.ausregistry.jtoolkit2.se.DomainInfoCommand;
import com.ausregistry.jtoolkit2.se.DomainInfoResponse;
import com.ausregistry.jtoolkit2.se.HostCheckCommand;
import com.ausregistry.jtoolkit2.se.HostCheckResponse;
import com.ausregistry.jtoolkit2.se.Result;
import com.ausregistry.jtoolkit2.se.ResultCode;
import com.ausregistry.jtoolkit2.session.SessionManager;
import com.ausregistry.jtoolkit2.session.SessionManagerFactory;
import com.ausregistry.jtoolkit2.session.SessionManagerProperties;
import com.ausregistry.jtoolkit2.session.SessionManagerPropertiesImpl;
import com.ausregistry.jtoolkit2.session.Transaction;

/**
 * A demonstration of the steps required to pipeline a series of commands
 * through a session manager.
 */
public class PipeliningDemo {

    private static final String USAGE = "Must be run with the following parameters: \"Domain Name to check\""
            + " \"Host Name to check\" \"Contact User ID to check\" \"Domain Name for Info\"";
    private final SessionManager manager;
    private final SessionManagerProperties properties;

    public PipeliningDemo() throws Exception {
        // Read in configuration properties from the toolkit.properties file
        properties = new SessionManagerPropertiesImpl("toolkit.properties");

        // Create a new session manager. This will use the properties loaded above to set up parameters
        // required to connect to an EPP server.
        manager = SessionManagerFactory.newInstance(properties);
    }

    public static void main(String[] args) {
        try {
            // Instantiate the demo class, creating a session manager
            final PipeliningDemo demo = new PipeliningDemo();
            if (args.length != 4) {
                System.err.println(USAGE);
                System.exit(1);
            }
            // Parse command line arguments
            final String domainCheckName = args[0];
            final String hostName = args[1];
            final String contactName = args[2];
            final String domainInfoName = args[3];

            // Run the demo using the command line arguments
            demo.runDemo(domainCheckName, hostName, contactName, domainInfoName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runDemo(final String domainCheckName, final String hostName, final String contactName,
            final String domainInfoName) throws Exception {
        // Start the session manager. This will automatically create a connection, send a hello and a
        // greeting and perform a login. The manager will be ready to execute transactions after this call.
        manager.startup();

        // Create the required responses. Each command will need a corresponding response object.
        final DomainCheckResponse domainCheckResponse = new DomainCheckResponse();
        final HostCheckResponse hostCheckResponse = new HostCheckResponse();
        final ContactCheckResponse contactCheckResponse = new ContactCheckResponse();
        final DomainInfoResponse domainInfoResponse = new DomainInfoResponse();

        // Create a new array of transactions. Add each transaction that you wish to pipeline to the array.
        final Transaction[] transactions = new Transaction[] {
                new Transaction(new DomainCheckCommand(domainCheckName), domainCheckResponse),
                new Transaction(new HostCheckCommand(hostName), hostCheckResponse),
                new Transaction(new ContactCheckCommand(contactName), contactCheckResponse),
                new Transaction(new DomainInfoCommand(domainInfoName), domainInfoResponse)};

        // Tell the manager to execute the transactions. The transactions will automatically be pipelined.
        // The number of successfully completed transactions will be returned.
        final int completedTransactions = manager.execute(transactions);

        // Check that the correct amount of transactions were returned. If so, print the details of each response.
        if (completedTransactions == transactions.length) {
            printResponseDetails(domainCheckName, hostName, contactName, domainCheckResponse, hostCheckResponse,
                    contactCheckResponse, domainInfoResponse);
        } else {
            System.out.println("A command response was not returned");
        }

        // End the session, disconnecting the socket connection as well
        manager.shutdown();
    }

    private void printResponseDetails(final String domainCheckName, final String hostName, final String contactName,
            DomainCheckResponse domainCheckResponse, HostCheckResponse hostCheckResponse,
            ContactCheckResponse contactCheckResponse, DomainInfoResponse domainInfoResponse) {
        // Print out the details of the domain check response.
        System.out.println("Domain Check:");
        System.out.println("EPP Response code: " + domainCheckResponse.getResults()[0].getResultCode());
        boolean isAvailable = domainCheckResponse.isAvailable(domainCheckName);
        System.out.println("Domain Name is available: " + isAvailable);
        if (!isAvailable) {
            System.out.println("Reason Domain Name is not available: " + domainCheckResponse.getReason(0));
        }

        // Print out the details of the host check response.
        System.out.println("Host Check:");
        System.out.println("EPP Response code: " + hostCheckResponse.getResults()[0].getResultCode());
        isAvailable = hostCheckResponse.isAvailable(hostName);
        System.out.println("Host Name is available: " + isAvailable);
        if (!isAvailable) {
            System.out.println("Reason Host Name is not available: " + hostCheckResponse.getReason(0));
        }

        // Print out the details of the contact check response.
        System.out.println("Contact Check:");
        System.out.println("EPP Response code: " + contactCheckResponse.getResults()[0].getResultCode());
        isAvailable = contactCheckResponse.isAvailable(contactName);
        System.out.println("Contact Name is available: " + isAvailable);
        if (!isAvailable) {
            System.out.println("Reason Contact Name is not available: " + contactCheckResponse.getReason(0));
        }

        // Print out the details of the domain info response.
        System.out.println("Domain Info:");
        Result results = domainInfoResponse.getResults()[0];
        int resultCode = results.getResultCode();
        System.out.println("EPP Response code: " + resultCode);
        if (resultCode == ResultCode.SUCCESS) {
            System.out.println("Domain Name ROID: " + domainInfoResponse.getROID());
            System.out.println("Domain Name sponsor: " + domainInfoResponse.getSponsorClient());
            System.out.println("Domain Name expiry date: " + domainInfoResponse.getExpireDate().getTime());
        } else {
            System.out.println("Extended reason: " + results.getResultExtReason(0));
        }
    }
}
