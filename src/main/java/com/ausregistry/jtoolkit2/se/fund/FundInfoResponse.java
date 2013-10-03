package com.ausregistry.jtoolkit2.se.fund;

import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ausregistry.jtoolkit2.se.DataResponse;
import com.ausregistry.jtoolkit2.se.ExtendedObjectType;
import com.ausregistry.jtoolkit2.se.StandardCommandType;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Use this to access fund object information as provided in an EPP fund
 * info response.  Such a service element is sent by a ari registry server
 * in response to a valid fund info command, implemented by the
 * FundInfoCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.fund.FundInfoCommand
 */

public class FundInfoResponse extends DataResponse {

    private List<Fund> funds = new ArrayList<Fund>();

    public FundInfoResponse() {
        super(StandardCommandType.INFO, ExtendedObjectType.FUND);
    }

    @Override
    public void fromXML(XMLDocument xmlDocArg) {
        debugLogger.finest("enter");
        super.fromXML(xmlDocArg);
        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            Node fundInfDataNode = xmlDocArg.getElement(RESPONSE_EXPR + "/e:resData/fund:infData");
            NodeList fundNodes = fundInfDataNode.getChildNodes();

            for (int i = 0; i < fundNodes.getLength(); i++) {
                Node fundNode = fundNodes.item(i);
                boolean limit = Boolean.parseBoolean(fundNode.getAttributes().getNamedItem("limit").getNodeValue());

                Node idNode = fundNode.getFirstChild();
                String id = idNode.getFirstChild().getNodeValue();
                BigDecimal available = null;
                if (idNode.getNextSibling() != null) {
                    available = new BigDecimal(idNode.getNextSibling().getFirstChild().getNodeValue());
                }

                funds.add(new Fund(id, limit, available));
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }

        debugLogger.finest("exit");
    }

    public List<Fund> getFunds() {
        return funds;
    }
}
