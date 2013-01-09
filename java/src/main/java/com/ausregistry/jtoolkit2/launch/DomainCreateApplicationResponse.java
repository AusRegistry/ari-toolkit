package com.ausregistry.jtoolkit2.launch;

import javax.xml.xpath.XPathExpressionException;

import com.ausregistry.jtoolkit2.se.CreateResponse;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.xml.XMLDocument;

/**
 * Use this to access create data for a domain as provided in an EPP domain
 * create response compliant with RFC5730 and RFC5731.  Such a service element
 * is sent by a compliant EPP server in response to a valid domain create
 * command with domain create application extension.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainCreateCommand
 * @see com.ausregistry.jtoolkit2.launch.DomainCreateApplicationCommandExtension
 */
public class DomainCreateApplicationResponse extends CreateResponse {
	private static final long serialVersionUID = -5724827272682186647L;

	private static final String DOM_CR_DATE_EXPR = exprReplace(CR_DATE_EXPR);
	private static final String DOM_APPLICATION_ID_EXPR = exprReplace(CRE_DATA_EXPR) + "/launch:id/text()";

	private String applicationId;

	protected String crDateExpr() {
		return DOM_CR_DATE_EXPR;
	}

	public String getApplicationId() {
		return applicationId;
	}

	protected static String exprReplace(String expr) {
		return expr.replaceAll(OBJ, ExtendedObjectType.LAUNCH.getName());
	}

	public DomainCreateApplicationResponse() {
		super(ExtendedObjectType.LAUNCH);
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
	}

}

