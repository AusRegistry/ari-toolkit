package com.ausregistry.jtoolkit2.se.rgp;

import com.ausregistry.jtoolkit2.se.Response;
import com.ausregistry.jtoolkit2.se.ResponseExtension;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

import java.util.List;

/**
 * Use this to access restore operation data for a domain as provided in an EPP domain
 * restore response compliant with RFCs 3915.
 *
 * @see com.ausregistry.jtoolkit2.se.rgp.DomainRestoreRequestCommand
 */
public class DomainRestoreResponse extends Response {
	private static final long serialVersionUID = -5724827272682186647L;

	private final DomainInfoRgpResponseExtension restoreExtension = new DomainInfoRgpResponseExtension(
			ResponseExtension.UPDATE);

	@Override
	public void fromXML(XMLDocument xmlDoc) {
		registerExtension(restoreExtension);
		super.fromXML(xmlDoc);

		if (!resultArray[0].succeeded()) {
			return;
		}
	}

	public List<RgpStatus> getRgpStatuses() {
		return restoreExtension.getRgpStatuses();
	}
}
