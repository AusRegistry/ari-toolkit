package com.ausregistry.jtoolkit2.se;

/**
 * Use this command to acknowledge receipt of a service message previously
 * retrieved via a poll request.  This dequeues the message and makes the next
 * message available for retrieval.  From RFC5730:
 * <blockquote>
 * After a messages has been received by the client, the client MUST respond to
 * the message with an explicit acknowledgement to confirm that the message has
 * been received.  A server MUST dequeue the message and decrement the queue
 * counter after receiving acknowledgement from the client, making the next
 * message in the queue (if any) available for retrieval.
 * </blockquote>
 */
public class PollAckCommand extends PollCommand {
    private static final long serialVersionUID = -8544628706096451507L;

    public PollAckCommand(String msgID) {
        super(PollOperation.ACK);

        cmdElement.setAttribute("msgID", msgID);
    }
}

