package com.ausregistry.jtoolkit2.se.rgp;

import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.StandardObjectType;
import com.ausregistry.jtoolkit2.se.UpdateCommand;
import org.w3c.dom.Element;

/**
 * Use this to request to restore a domain object provisioned in an EPP
 * Registry. Instances of this class generate RFC5730, RFC5731 and RFC3915 compliant
 * domain update EPP command service elements via the toXML method, with the
 * RGP restore extension. The response expected from a server should be handled 
 * by a Domain Restore Response object.
 * 
 * @see com.ausregistry.jtoolkit2.se.rgp.DomainRestoreResponse
 */
public class DomainRestoreRequestCommand extends UpdateCommand {

	private static final long serialVersionUID = 2409916920503111390L;

	/**
	 * <p>Constructor taking in a domain name for the restore request.</p>
	 */
	public DomainRestoreRequestCommand(String name) {
		super(StandardObjectType.DOMAIN, name);
		xmlWriter.appendChild(objElement, "chg");

		extension = xmlWriter.appendChild(command, "extension");

		final Element updateElement = xmlWriter.appendChild(extension,
				"update", ExtendedObjectType.RESTORE.getURI());

		xmlWriter.appendChild(updateElement, "restore", "op", "request");
	}
}
