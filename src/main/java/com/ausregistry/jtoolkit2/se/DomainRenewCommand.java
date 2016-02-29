package com.ausregistry.jtoolkit2.se;

import java.util.GregorianCalendar;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Use this to request the renewal of a domain object provisioned in an EPP
 * Registry.  The requesting client must be the sponsoring client of the domain
 * object.  Instances of this class generate RFC5730 and RFC5731 compliant
 * domain renew EPP command service elements via the toXML method.  The
 * response expected from a server should be handled by a DomainRenewResponse
 * object.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainRenewResponse
 */
public class DomainRenewCommand extends ObjectCommand {
    private static final long serialVersionUID = 4860769492565708028L;

    /**
     * @throws IllegalArgumentException if {@code exDate} is {@code null}.
     */
    public DomainRenewCommand(String name, GregorianCalendar exDate) {
        super(StandardCommandType.RENEW, StandardObjectType.DOMAIN, name);

        if (exDate == null) {
            throw new IllegalArgumentException(ErrorPkg.getMessage(
                        "se.domain.renew.curExpDate.missing"));
        }

        String dateStr = EPPDateFormatter.toXSDate(exDate);

        xmlWriter.appendChild(objElement, "curExpDate").setTextContent(dateStr);
    }

    public DomainRenewCommand(String name, GregorianCalendar exDate,
            Period period) {

        this(name, exDate);

        if (period != null) {
            period.appendPeriod(xmlWriter, objElement);
        }
    }
}

