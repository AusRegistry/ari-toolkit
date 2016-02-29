package com.ausregistry.jtoolkit2.session;

import java.io.IOException;

import com.ausregistry.jtoolkit2.se.Greeting;
import com.ausregistry.jtoolkit2.xml.ParsingException;

/**
 * This defines the operations or actions required for a SessionManager.
 */
public interface SessionManager extends Runnable {

    /**
     * This method would configure the SessionManager.  This may include reading and setting properties.
     *
     * @param properties the properties to be used
     * @throws ConfigurationException the configuration exception
     */
    void configure(SessionManagerProperties properties) throws ConfigurationException;

    /**
     * Change password.
     *
     * @param oldPassword the old password
     * @param newPassword the new password
     */
    void changePassword(String oldPassword, String newPassword);

    /**
     * Change max pool size.  Assumes there is a session pool.
     *
     * @param newSize the new size of the session pool
     */
    void changeMaxPoolSize(int newSize);

    /**
     * Returns the properties used during configuration.
     *
     * @return the properties
     */
    SessionManagerProperties getProperties();

    /**
     * Execute a single Transaction.
     *
     * @param tx the Transaction to be executed.
     * @throws FatalSessionException the fatal session exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ParsingException the parsing exception
     * @throws CommandFailedException the command failed exception
     * @throws IllegalStateException the illegal state exception
     */
    void execute(Transaction tx) throws FatalSessionException, IOException, ParsingException,
            CommandFailedException, IllegalStateException;

    /**
     * Execute multiple Transactions.
    *
     * @param txs the Transactions to be sent to the EPP server.
     * @return the number of Transactions executed successfully
     * @throws FatalSessionException the fatal session exception
     * @throws IllegalStateException the illegal state exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    int execute(Transaction[] txs) throws FatalSessionException, IllegalStateException, IOException;

    /**
     * Initialise the SessionManager.
     *
     * @throws SessionConfigurationException the session configuration exception
     * @throws SessionOpenException the session open exception
     */
    void startup() throws SessionConfigurationException, SessionOpenException;

    /**
     * Shutdown the SessionManger, making it unavailable for further transaction processing.
     */
    void shutdown();

    /**
     * Returns the last greeting.
     *
     * @return the last greeting
     * @throws SessionConfigurationException the session configuration exception
     * @throws SessionOpenException the session open exception
     */
    Greeting getLastGreeting() throws SessionConfigurationException, SessionOpenException;

    @Override
    void run();

    /**
     * Returns the viewer created to record run statistics of sessions.
     *
     * @return the viewer
     */
    StatsViewer getStatsViewer();
}
