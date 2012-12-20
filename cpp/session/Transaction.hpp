#ifndef __TRANSACTION_HPP
#define __TRANSACTION_HPP

#include "se/Command.hpp"
#include "se/Response.hpp"

/**
 * Each EPP command/response pair is modelled as a Transaction.  A Transaction
 * associates a Response with a Command to enable the user to determine the
 * effect of each command.  The {@link SessionManager} takes either a single
 * Transaction or an array of Transactions as an argument to {@link
 * SessionManager#execute}.
 *
 */
class Transaction
{
public:
	/**
	 * Describe the state of a transaction.  Initially, a transaction is
	 * unprocessed.  See the description of {@link
	 * SessionManagerImpl#execute(Transaction[])} for the state transitions.
	 */
	enum State { UNPROCESSED, PROCESSED, RETRY, FATAL_ERROR };

    /// @TODO SWIG/Perl workaround - figure out why SWIG wants an empty constructor.
    Transaction () : hasError(false), errorCause("") {}

	/// Construct a Transaction.
    Transaction (Command* command, Response* response)
		: command(command), response(response), state(UNPROCESSED), hasError(false), errorCause("")
	{ }

    Command* getCommand() { return command; };
    Response* getResponse() { return response; };

	/**
	 * Get the current state of this Transaction.
	 *
	 * @see SessionManagerImpl#execute(Transaction[])
	 */
	State getState() { return state; }
	void setState(State s) { state = s; }

	/**
	 * Get the cause of failure of this Transaction (if any).  Transaction
	 * retains ownership.
	 */
	const EPPException* getCause() { return hasError ? &errorCause : NULL; }

	/**
	 * Indicate the cause of failure of this Transaction.  Similarly to {@link
	 * #setState(TransactionState)}, the application should not generally use
	 * this.
	 */
	void setCause(const EPPException& e)
	{
		errorCause = e;
		hasError = true;
	}

private:
    Command*  command;
    Response*  response;
	State state;

    bool hasError;
	EPPException errorCause;
};

#endif // __TRANSACTION_HPP
