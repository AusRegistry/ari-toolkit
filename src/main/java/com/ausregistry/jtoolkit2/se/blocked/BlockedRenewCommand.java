package com.ausregistry.jtoolkit2.se.blocked;

import java.util.GregorianCalendar;

import com.ausregistry.jtoolkit2.EPPDateFormatter;
import com.ausregistry.jtoolkit2.ErrorPkg;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.ObjectCommand;
import com.ausregistry.jtoolkit2.se.Period;
import com.ausregistry.jtoolkit2.se.StandardCommandType;

/**
 * Use this to renew a blocked domain objects. Instances of this class
 * generate blocked domain info EPP command service elements via the toXML method.  The
 * response expected from a server should be handled by a DomainRenewResponse
 * object.
 *
 * @see com.ausregistry.jtoolkit2.se.blocked.BlockedRenewResponse
 */
public class BlockedRenewCommand extends ObjectCommand {
    private static final long serialVersionUID = 9181066107823620601L;

    /**
     * @throws IllegalArgumentException if {@code exDate} is {@code null}.
     */
    public BlockedRenewCommand(String id, GregorianCalendar exDate) {
        super(StandardCommandType.RENEW, ExtendedObjectType.BLOCKED, id);

        if (exDate == null) {
            throw new IllegalArgumentException(ErrorPkg.getMessage(
                    "se.domain.renew.curExpDate.missing"));
        }

        xmlWriter.appendChild(objElement, "curExpDate").setTextContent(EPPDateFormatter.toXSDate(exDate));
    }

    public BlockedRenewCommand(String id, GregorianCalendar exDate, Period period) {
        this(id, exDate);

        if (period != null) {
            period.appendPeriod(xmlWriter, objElement);
        }
    }
}
