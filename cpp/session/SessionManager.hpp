#ifndef __SESSION_MANAGER_HPP
#define __SESSION_MANAGER_HPP

#include "session/CommandFailedException.hpp"
#include "session/ConfigurationException.hpp"
#include "session/FatalSessionException.hpp"
#include "session/SessionConfigurationException.hpp"
#include "session/SessionOpenException.hpp"
#include "session/EPPIOException.hpp"
#include "xml/ParsingException.hpp"
#include "common/IllegalStateException.hpp"

class Greeting;
class SessionManagerProperties;
class Transaction;
class StatsViewer;

/**
 * The SessionManager provides a transaction processing service to clients.
 * Upon successful configuration, it guarantees that a pool of {@link
 * Session}s will be available for processing
 * {@link Transaction}s.  A SessionManager is
 * configured from a {@link * SessionManagerProperties} object.
 */
class SessionManager
{
public:

	virtual ~SessionManager(void) = 0;
    /**
     * Configure the SessionManager from the given set of properties.  This can
     * also be used to reconfigure the SessionManager and its internal {@link
     * SessionPool}.  A change of password should be performed using <a
     * href="#changePassword()">changePassword</a>.  Following a change of
     * password, the properties source should be updated to reflect the change.
     * If this is not done, then the next invocation of configure() will fail.
     * @param properties A SessionManagerProperties ready to be used to reconfigure.  This
     *   object must outlive the SessionManager.
     */
    virtual void configure(SessionManagerProperties* props)
        throw (ConfigurationException) = 0;

    /**
     * Change the EPP client password from oldPassword to newPassword.  Note
     * that this does not update the configuration source to reflect the change
     * - that must be done separately before any future attempts to
     *   (re-)configure the system.
     */
    virtual void changePassword (const std::string &oldPassword,
            const std::string &newPassword) = 0;
    /**
     * Change the maximum size that the managed session pool will grow to.
     * Note that this setting will not be saved to the configuration source.
     */
    virtual void changeMaxPoolSize (int newSize) = 0;

    virtual const SessionManagerProperties & getProperties () const = 0;


    /**
     * Try to process a single transaction.  Some implementation-defined number
     * of attempts will be made to process the transaction in cases where I/O
     * errors or non-protocol server errors occur during processing.  Use of
     * the underlying session is protected against concurrent use by other
     * threads by using the getSession/releaseSession features of this
     * SessionManager's {@link SessionPool}.
     * This method guarantees that the session used will be returned to the
     * pool before the method returns.
     *
     * @throws FatalSessionException No session could be acquired to process
     * the transaction.  Check the exception message and log records for
     * details.
     *
     * @throws IOException Every attempt to execute the transaction command
     * failed due to an IOException.  This is the last such IOException.
     *
     * @throws ParsingException Parsing of the response failed.  Check the
     * exception message for the cause.
     *
     * @throws CommandFailedException The acceptable limit on the number of
     * failed commands due to server error was exceeded in trying to process
     * the command.  This probably indicates a server limitation related to the
     * command being processed.
     *
     * @throws IllegalStateException The SessionManager had been shutdown or
     * not started up prior to invoking this method.
     */
    virtual void execute(Transaction &tx) 
        throw (FatalSessionException,
                EPPIOException,
                ParsingException,
                CommandFailedException,
                IllegalStateException) = 0;

    /**
     * Try to pipeline execute each of the transactions in the given array.
     * The transactions are guaranteed to be processed in order within a single
     * session.
     *
     * @throws FatalSessionException A session could not be obtained for the
     * purpose of carrying the required EPP commands and responses.  Without
     * intervention, it should be expected that no further transactions may be
     * executed.
     *
     * @throws IOException Every session used to try to send the first command
     * threw an IOException. It was thus deemed that further attempts to write
     * to other sessions would fail.
     *
     * @throws IllegalStateException The SessionManager had been shutdown or
     * not started up prior to invoking this method.
     *
     * @return the number of transactions actually executed.  This may be less
     * than txs.length if an I/O error occurred.  Zero indicates that a
     * shutdown is in progress.
     */
    virtual int execute(std::vector<Transaction> &txs)
        throw (FatalSessionException,
                IllegalStateException) = 0;

    /**
     * Prepare the SessionManager for providing Transaction processing
     * services.  This initialises the {@link
     * SessionPool} managed by the
     * SessionManager, guaranteeing that any requirements defined by
     * SessionPool properties are met, providing the pool is initialised
     * successfully.
     *
     * @throws SessionConfigurationException The pool was unable configure a
     * session due to a configuration issue.  Such problems include invalid
     * server location specification and missing or invalid authentication
     * resources.
     *
     * @throws SessionOpenException The pool was unable to open a session due
     * to a configuration issue.  Such problems include incorrect server
     * location, failed authentication and network failure.
     */
    virtual void startup () 
        throw (SessionConfigurationException,
                SessionOpenException) = 0;

    /**
     * Shutdown the SessionManager, making it unavailable for further
     * transaction processing.
     */
    virtual void shutdown () = 0;

    /**
     * Get the service discovery information embodied in the most recent
     * Greeting received from the EPP server.
     *
     *
     * @throws SessionConfigurationException No prior connection to the EPP
     * server had been established and the SessionPoolProperties provided to
     * the SessionPool for the purpose of establishing a new Session were
     * invalid.
     *
     * @throws SessionOpenException No prior connection to the EPP server had
     * been established and the attempt to establish a connection for the
     * purpose of retrieving service information failed.
     */
    virtual const Greeting * getLastGreeting() const
        throw (SessionConfigurationException,
                SessionOpenException) = 0;

    /**
     * Initiate the SessionPool's keep-alive system.  This will run until
     * <a href=#shutdown()>shutdown</a> is invoked on the SessionManager.
     */
    virtual void run() = 0;

    /**
     * Get the StatsViewer responsible for providing operating statistics about
     * the SessionManager.
     */
    virtual const StatsViewer & getStatsViewer () const = 0;
};

inline SessionManager::~SessionManager(void)
{
    return;
}

#endif // __SESSION_MANAGER_HPP

