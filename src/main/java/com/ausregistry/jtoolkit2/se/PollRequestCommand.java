package com.ausregistry.jtoolkit2.se;

/**
 * Use this command to poll the EPP server for the first message in the
 * client's message queue maintained by the server.  From RFC5730:
 * <blockquote>
 * The EPP {@code <poll>} command is used to discover and retrieve service
 * messages queued by a server for individual clients.  If the message queue is
 * not empty, a successful response to a {@code <poll>} command MUST return the
 * first message from the message queue.  Each response returned from the
 * server includes a server-unique message identifier that MUST be provided to
 * acknowledge receipt of the message, and a counter that indicates the number
 * of messages in the queue.
 * </blockquote>
 */
public class PollRequestCommand extends PollCommand {
    private static final long serialVersionUID = -5638634358538603322L;

    public PollRequestCommand() {
        super(PollOperation.REQ);
    }
}

