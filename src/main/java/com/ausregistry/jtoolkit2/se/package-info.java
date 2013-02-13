/**
 * The EPP defines different services or commands that must be mapped to and from Java objects.  Each
 * {@link com.ausregistry.jtoolkit2.se.SendSE} class has a corresponding XML
 * representation which is an EPP service element that should be sent from the
 * client to the server.  Each {@link com.ausregistry.jtoolkit2.se.ReceiveSE}
 * class should be given the appropriate type of XML document, which is an EPP
 * service element received by the client from the server, from which the
 * object will make available the data in the service element.
 */
package com.ausregistry.jtoolkit2.se;

