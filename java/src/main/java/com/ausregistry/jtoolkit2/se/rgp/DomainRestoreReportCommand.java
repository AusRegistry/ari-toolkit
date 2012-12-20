package com.ausregistry.jtoolkit2.se.rgp;

import java.util.GregorianCalendar;

import org.w3c.dom.Element;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.StandardObjectType;
import com.ausregistry.jtoolkit2.se.UpdateCommand;

/**
 * Use this to initiate the restoration of a domain object provisioned in an EPP
 * Registry with the provided report. Instances of this class generate RFC3915 
 * compliant domain update  EPP command service elements with RGP extension 
 * elements via the toXML method. The response expected from a server should be 
 * handled by a Response object.
 * 
 * @see com.ausregistry.jtoolkit2.se.Response
 */
public class DomainRestoreReportCommand extends UpdateCommand {

	private static final long serialVersionUID = 2409916920503111390L;

	/**
	 * Constructor allowing all possible fields for a domain restore report
	 * command. Null is acceptable for the optional values (secondStatement and
	 * other).
	 */
	public DomainRestoreReportCommand(String name, String preData,
			String postData, GregorianCalendar delTime,
			GregorianCalendar resTime, ReportTextElement resReason,
			ReportTextElement statement, ReportTextElement secondStatement,
			String other) {
		super(StandardObjectType.DOMAIN, name);
		
		if (name == null || preData == null || postData == null || delTime == null
				|| resTime == null || resReason == null || statement == null) {
			throw new IllegalArgumentException(ErrorPkg.getMessage(
						"se.domain.restore.report.missing_arg"));
		}

		xmlWriter.appendChild(objElement, "chg");

		extension = xmlWriter.appendChild(command, "extension");

		final Element updateElement = xmlWriter.appendChild(extension,
				"update", ExtendedObjectType.RESTORE.getURI());

		Element restoreElement = xmlWriter.appendChild(updateElement, "restore", "op", "report");
		Element reportElement = xmlWriter.appendChild(restoreElement, "report");
		
		xmlWriter.appendChild(reportElement, "preData").setTextContent(preData);
		xmlWriter.appendChild(reportElement, "postData").setTextContent(postData);
		xmlWriter.appendChild(reportElement, "delTime").setTextContent(EPPDateFormatter.toXSDateTime(delTime));
		xmlWriter.appendChild(reportElement, "resTime").setTextContent(EPPDateFormatter.toXSDateTime(resTime));
		appendReportTextElement(resReason, reportElement, "resReason");
		appendReportTextElement(statement, reportElement, "statement");
		if (secondStatement != null) {
			appendReportTextElement(secondStatement, reportElement, "statement");
		}
		if (other != null) {
			xmlWriter.appendChild(reportElement, "other").setTextContent(other);
		}
	}

	private void appendReportTextElement(ReportTextElement reportTextElement, Element xmlElement, String xmlElementName) {
		if (reportTextElement.getLanguage() != null) {
			xmlWriter.appendChild(xmlElement, xmlElementName, "lang", reportTextElement.getLanguage()).setTextContent(
					reportTextElement.getReportElement());
		} else {
			xmlWriter.appendChild(xmlElement, xmlElementName).setTextContent(reportTextElement.getReportElement());
		}
	}
}
