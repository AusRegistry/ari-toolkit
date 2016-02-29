package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.EPPDateFormatter;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import java.util.GregorianCalendar;

import javax.xml.xpath.XPathExpressionException;

/**
 * Representation of the EPP create response, as defined in RFC5730.
 * Subclasses of this must specify the object to which the command is mapped.
 * Instances of this class provide an interface to access create data for the
 * object identified in a {@link com.ausregistry.jtoolkit2.se.CreateCommand}.
 * This relies on the instance first being initialised by a suitable EPP create
 * response using the method fromXML.  For flexibility, this implementation
 * extracts the data from the response using XPath queries, the expressions for
 * which are defined statically.
 *
 * @see com.ausregistry.jtoolkit2.se.CreateCommand
 */
public abstract class CreateResponse extends DataResponse {

    protected static final String OBJ = "OBJ";
    protected static final String CRE_DATA_EXPR = "/e:epp/e:response/e:resData/OBJ:creData";
    protected static final String CR_DATE_EXPR = CRE_DATA_EXPR + "/OBJ:crDate/text()";

    private static final long serialVersionUID = 5488683100446489329L;

    private GregorianCalendar crDate;

    public CreateResponse(ObjectType objectType) {
        super(StandardCommandType.CREATE, objectType);
    }

    public GregorianCalendar getCreateDate() {
        return crDate;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            final String crDateStr = xmlDoc.getNodeValue(crDateExpr());
            crDate = EPPDateFormatter.fromXSDateTime(crDateStr);
        } catch (final XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }

    protected abstract String crDateExpr();
}
