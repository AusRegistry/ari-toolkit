/**
 * <p>Contains the full set of basic
 * {@link com.ausregistry.jtoolkit2.se.Command}<code>s</code>
 * and {@link com.ausregistry.jtoolkit2.se.Response}<code>s</code> for EPP
 * service communication, as well as the classes required for sending and
 * receiving EPP messages.</p>
 *
 * <p>This package also contains a full set of extensions specific for the .au
 * and .ae domain space, however the newer generic extensions are located in
 * subpackages of this package.</p>
 *
 * <p>The Extensible Provisioning Protocol (EPP) defines different services or
 * commands that must be mapped to and from Java objects. Each
 * {@link com.ausregistry.jtoolkit2.se.SendSE} class has a corresponding XML
 * representation which is an EPP service element that should be sent from the
 * client to the server. Each {@link com.ausregistry.jtoolkit2.se.ReceiveSE}
 * class should be given the appropriate type of XML document, which is an EPP
 * service element received by the client from the server, from which the
 * object will make available the data in the service element.</p>
 */
package com.ausregistry.jtoolkit2.se;

