package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.Response;

/**
 * Each EPP command/response pair is modelled as a Transaction.  A Transaction
 * associates a Response with a Command to enable the user to determine the
 * effect of each command.  The {@link
 * com.ausregistry.jtoolkit2.session.SessionManager} takes either a single
 * Transaction or an array of Transactions as an argument to {@link
 * com.ausregistry.jtoolkit2.session.SessionManager#execute}.
 */
public class Transaction {
    private Command command;
    private Response response;
    private Throwable cause;
    private TransactionState state;
    private long startTime;
    private long elapsedTime;

    /**
     * Create a Transaction from the given command/response pair.
     */
    public Transaction(Command command, Response response) {
        this.command = command;
        this.response = response;
        this.state = TransactionState.UNPROCESSED;
    }

    public Command getCommand() {
        return command;
    }

    public Response getResponse() {
        return response;
    }

    /**
     * Get the current state of this Transaction.
     *
     * @see com.ausregistry.jtoolkit2.session.SessionManagerImpl#execute(Transaction[])
     * @see com.ausregistry.jtoolkit2.session.TransactionState
     */
    public TransactionState getState() {
        return state;
    }

    /**
     * Indicate the new state of this Transaction.  This should not generally
     * be used by the application - rather a SessionManager usually invokes
     * this so that the application may determine the state using {@link #getState()}.
     */
    public void setState(TransactionState state) {
        this.state = state;
        switch (state) {
            case PROCESSED:
            case RETRY:
            case FATAL_ERROR:
                elapsedTime = Timer.msDiff(startTime);
                break;
            default:
                break;
        }
    }

    /**
     * Get the cause of failure of this Transaction.
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * Indicate the cause of failure of this Transaction.  Similarly to {@link
     * #setState(TransactionState)}, the application should not generally use
     * this.
     */
    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    /**
     * Indicate that the transaction is now in progress.
     */
    public void start() {
        startTime = Timer.now();
    }

    /**
     * The interval of time (in milliseconds) that elapsed between when the
     * start method was executed and when the transaction completed, as
     * determined by a change in transaction state.
     */
    public long getResponseTime() {
        return elapsedTime;
    }
}

