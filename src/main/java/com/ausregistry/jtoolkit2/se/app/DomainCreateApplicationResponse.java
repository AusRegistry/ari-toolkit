package com.ausregistry.jtoolkit2.se.app;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.CreateResponse;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * <p>Representation of the EPP Domain Create response with the Create Domain Application aspect of the
 * Domain Name Application extension.</p>
 *
 * <p>Use this to access domain application data for a domain as provided in an EPP Domain Create response
 * compliant with RFC5730 and RFC5731. Such a service element is sent by a compliant EPP server in response
 * to a valid Domain Create command with the Domain Name Application extension.</p>
 *
 * @see DomainCreateApplicationCommandExtension
 * @see <a href="http://ausregistry.github.io/doc/application-1.0/application-1.0.html">Domain Name Application
 * Extension Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCreateApplicationResponse extends CreateResponse {
	private static final long serialVersionUID = -5724827272682186647L;

	private static final String DOM_CR_DATE_EXPR = exprReplace(CR_DATE_EXPR);
	private static final String DOM_APPLICATION_ID_EXPR = exprReplace(CRE_DATA_EXPR) + "/app:id/text()";
	private static final String DOM_DOMAIN_NAME_EXPR = exprReplace(CRE_DATA_EXPR) + "/app:name/text()";

	private String applicationId;
    private String domainName;

	protected String crDateExpr() {
		return DOM_CR_DATE_EXPR;
	}

	public String getApplicationId() {
		return applicationId;
	}

    public String getDomainName() {
        return domainName;
    }

    protected static String exprReplace(String expr) {
		return expr.replaceAll(OBJ, ExtendedObjectType.APP.getName());
	}

	public DomainCreateApplicationResponse() {
		super(ExtendedObjectType.APP);
	}

	@Override
	public void fromXML(XMLDocument xmlDoc) {
		super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

		try {
			applicationId = xmlDoc.getNodeValue(DOM_APPLICATION_ID_EXPR);
		} catch (XPathExpressionException xpee) {
			maintLogger.warning(xpee.getMessage());
		}
        try {
            domainName = xmlDoc.getNodeValue(DOM_DOMAIN_NAME_EXPR);
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
	}
}

