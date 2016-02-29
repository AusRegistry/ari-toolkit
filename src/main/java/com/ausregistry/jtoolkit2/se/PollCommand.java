package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Representation of the EPP poll command, as defined in RFC5730.  Subclasses
 * of this must internally specify the poll operation as either acknowledge
 * (ack) or request (req), without exposing the implementation of the poll
 * operation type to the user.
 */
public abstract class PollCommand extends Command {

    private static final long serialVersionUID = 2569927516750627497L;

    public PollCommand(PollOperation op) {
        super(StandardCommandType.POLL);

        if (op == null) {
            throw new IllegalArgumentException(
                    ErrorPkg.getMessage("se.poll.op.missing"));
        }

        cmdElement.setAttribute("op", op.toString());
    }
}

