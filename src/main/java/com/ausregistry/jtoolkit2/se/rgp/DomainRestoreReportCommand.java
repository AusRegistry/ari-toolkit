package com.ausregistry.jtoolkit2.se.rgp;

import java.util.GregorianCalendar;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.StandardObjectType;
import com.ausregistry.jtoolkit2.se.UpdateCommand;
import org.w3c.dom.Element;

/**
 * <p>Representation of the EPP Domain Update command with the Domain Restore Report aspect of the Registry Grace Period
 * extension.</p>
 *
 * <p>Use this to post a Domain Restoration Report for restoring a domain from a "pending restore" RGP state
 * as part of an EPP Domain Update command compliant with RFC5730, RFC5731 and RFC3915. The response expected
 * from a server should be handled by a Domain Restore Response</p>
 *
 * @see DomainRestoreResponse
 * @see <a href="http://tools.ietf.org/html/rfc3915">Domain Registry Grace Period Mapping for the
 * Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainRestoreReportCommand extends UpdateCommand {

    private static final long serialVersionUID = 2409916920503111390L;

    /**
     * <p>Constructor allowing all possible fields for a domain restore report command.</p>
     * @param name Required.
     * @param preData Required.
     * @param postData Required.
     * @param delTime Required.
     * @param resTime Required.
     * @param resReason Required.
     * @param statement Required.
     * @param secondStatement Optional.
     * @param other Optional.
     * @throws IllegalArgumentException if {@code name}, {@code preData}, {@code postData}, {@code delTime},
     * {@code resTime}, {@code resReason} or {@code statement} is {@code null}.
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

    private void appendReportTextElement(ReportTextElement reportTextElement,
                                         Element xmlElement,
                                         String xmlElementName) {
        if (reportTextElement.getLanguage() != null) {
            xmlWriter.appendChild(xmlElement, xmlElementName, "lang", reportTextElement.getLanguage()).setTextContent(
                    reportTextElement.getReportElement());
        } else {
            xmlWriter.appendChild(xmlElement, xmlElementName).setTextContent(reportTextElement.getReportElement());
        }
    }
}
