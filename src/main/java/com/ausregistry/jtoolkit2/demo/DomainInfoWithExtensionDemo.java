package com.ausregistry.jtoolkit2.demo;

import java.util.List;

import com.ausregistry.jtoolkit2.se.DomainInfoCommand;
import com.ausregistry.jtoolkit2.se.DomainInfoResponse;
import com.ausregistry.jtoolkit2.se.ResultCode;
import com.ausregistry.jtoolkit2.se.secdns.DSData;
import com.ausregistry.jtoolkit2.se.secdns.SecDnsDomainInfoResponseExtension;
import com.ausregistry.jtoolkit2.session.SessionManager;
import com.ausregistry.jtoolkit2.session.SessionManagerFactory;
import com.ausregistry.jtoolkit2.session.SessionManagerProperties;
import com.ausregistry.jtoolkit2.session.SessionManagerPropertiesImpl;
import com.ausregistry.jtoolkit2.session.Transaction;

/**
 * A demonstration of the steps required to perform a domain info, with the response
 * returning extension elements.
 */
public class DomainInfoWithExtensionDemo {

    private static final String USAGE = "Must be run with the following parameters: \"Domain Name\" [Password]";

    private final SessionManager manager;
    private final SessionManagerProperties properties;

    public DomainInfoWithExtensionDemo() throws Exception {
        // Read in configuration properties from the toolkit.properties file. This include extension namespaces
        // to be used in the session.
        properties = new SessionManagerPropertiesImpl("toolkit.properties");

        // Create a new session manager. This will use the properties loaded above to set up parameters
        // required to connect to an EPP server.
        manager = SessionManagerFactory.newInstance(properties);
    }

    public static void main(String[] args) {
        try {
            // Instantiate the demo class, creating a session manager
            final DomainInfoWithExtensionDemo demo = new DomainInfoWithExtensionDemo();
            if (args.length == 1 || args.length == 2) {
                System.err.println(USAGE);
                System.exit(1);
            }
            // Parse command line arguments
            final String domainName = args[0];
            String password = null;
            if (args.length == 2) {
                password = args[1];
            }

            // Run the demo using the command line arguments
            demo.runDemo(domainName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runDemo(final String domainName, final String password) throws Exception {
        // Start the session manager. This will automatically create a connection, send a hello and a
        // greeting and perform a login. The manager will be ready to execute transactions after this call.
        manager.startup();

        // Create the domain info response
        final DomainInfoResponse domainInfoResponse = new DomainInfoResponse();

        // Create a SECDNS response extension object
        final SecDnsDomainInfoResponseExtension secDNSExt = new SecDnsDomainInfoResponseExtension();

        // Register the extension response to the domain info response
        domainInfoResponse.registerExtension(secDNSExt);

        // Tell the manager to execute the command. The response includes the response extension
        manager.execute(new Transaction(new DomainInfoCommand(domainName, password), domainInfoResponse));

        // Obtain the result code, and print relevant data if it is successful
        final int resultCode = domainInfoResponse.getResults()[0].getResultCode();
        System.out.println("Domain create response code: " + resultCode);
        System.out.println("Domain create response message: " + domainInfoResponse.getResults()[0].getResultMessage());
        if (resultCode == ResultCode.SUCCESS) {
            System.out.println("Domain created on: " + domainInfoResponse.getCreateDate().getTime());
            System.out.println("Domain sponsored by: " + domainInfoResponse.getSponsorClient());
        }

        // If the response had SECDNS extension data, then the extension response will be initialized
        if (secDNSExt.isInitialised()) {
            // Print out the first DS data element from the response if it exists
            final List<DSData> dsDataList = secDNSExt.getInfData().getDsDataList();
            if (dsDataList != null) {
                final DSData dsData = dsDataList.get(0);
                System.out.println("DS data algorithm: " + dsData.getAlg());
                System.out.println("DS data digest: " + dsData.getDigest());
                System.out.println("DS data digest type: " + dsData.getDigestType());
                System.out.println("DS data key tag: " + dsData.getKeyTag());
            }
        }

        // End the session, disconnecting the socket connection as well
        manager.shutdown();
    }
}
