package com.ausregistry.jtoolkit2.se.fee;


import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;
import java.util.*;

import static com.ausregistry.jtoolkit2.se.ReceiveSE.replaceIndex;

/**
 * <p>Extension for the EPP Domain Check response, representing the Fee
 * extension.</p>
 *
 * <p>Use this to check the price associated with this domain name as part of an EPP Domain Check
 * command compliant with RFC5730 and RFC5731. The "name", "command", "phase", "unit", "period", "class", "currency"
 * and "fee" values supplied, should match the fields that are requested for the domain name check for the requested
 * period. The response expected from a server should be handled by a Domain Check Response.</p>
 *
 * @see DomainCreateCommand
 * @see DomainCreateResponse
 * @see <a href="https://tools.ietf.org/html/draft-brown-epp-fees-03">Domain Name Fee Extension
 * Mapping for the Extensible Provisioning Protocol (EPP)</a>
 */
public class DomainCheckFeeResponseExtension extends ResponseExtension {

    private static final String FEE_CHECK_PREFIX = ExtendedObjectType.FEE.getName();

    private static final String CHKDATA_EXPR = replaceResponseType(ResponseExtension.EXTENSION_EXPR
            + "/"  + FEE_CHECK_PREFIX + ":RESPONSE_TYPE", ResponseExtension.CHK_DATA);

    private static final String CHKDATA_COUNT_EXPR = "count(" + CHKDATA_EXPR + "/*)";
    private static final String CHKDATA_IND_EXPR = CHKDATA_EXPR + "/" + FEE_CHECK_PREFIX + ":cd[IDX]";
    private static final String CHKDATA_DOMAIN_NAME_EXPR = "/" + FEE_CHECK_PREFIX + ":name/text()";
    private static final String CHKDATA_DOMAIN_CURRENCY_EXPR = "/" + FEE_CHECK_PREFIX + ":currency/text()";
    private static final String CHKDATA_DOMAIN_COMMAND_EXPR = "/" + FEE_CHECK_PREFIX + ":command/text()";
    private static final String CHKDATA_DOMAIN_COMMAND_PHASE_EXPR = "/" + FEE_CHECK_PREFIX + ":command/@phase";
    private static final String CHKDATA_DOMAIN_COMMAND_SUBPHASE_EXPR = "/" + FEE_CHECK_PREFIX + ":command/@subphase";
    private static final String CHKDATA_DOMAIN_PERIOD_EXPR = "/" + FEE_CHECK_PREFIX + ":period/text()";
    private static final String CHKDATA_DOMAIN_PERIOD_UNIT_EXPR = "/" + FEE_CHECK_PREFIX + ":period/@unit";
    private static final String CHKDATA_DOMAIN_FEE_CLASS_EXPR = "/" + FEE_CHECK_PREFIX + ":class/text()";

    private static final String CHKDATA_FEE_NODES_EXPR =  "/" + FEE_CHECK_PREFIX + ":fee";

    private boolean initialised;

    private List<FeeCheckData> feeDomains = new ArrayList<FeeCheckData>();

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {
        int checkDataCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);
        for (int checkDataIndex = 0; checkDataIndex < checkDataCount; checkDataIndex++) {
            parseFeeCheckDataNodes(xmlDoc, checkDataIndex);
        }
        initialised = checkDataCount > 0;
    }

    private void parseFeeCheckDataNodes(XMLDocument xmlDoc, int checkDataIndex) throws XPathExpressionException {
        String checkDataQueryPath = replaceIndex(CHKDATA_IND_EXPR, checkDataIndex + 1);

        final String domainName = parseTextValue(xmlDoc, checkDataQueryPath + CHKDATA_DOMAIN_NAME_EXPR);
        final FeeCheckData.Command command = parseCommandNode(xmlDoc, checkDataQueryPath);

        FeeCheckData feeCheckData = new FeeCheckData(domainName, command);
        feeCheckData.setCurrency(parseTextValue(xmlDoc, checkDataQueryPath + CHKDATA_DOMAIN_CURRENCY_EXPR));
        feeCheckData.setFeeClass(parseTextValue(xmlDoc, checkDataQueryPath + CHKDATA_DOMAIN_FEE_CLASS_EXPR));
        feeCheckData.setPeriod(parsePeriod(xmlDoc, checkDataQueryPath));

        parseFeeNodes(xmlDoc, feeCheckData, checkDataQueryPath + CHKDATA_FEE_NODES_EXPR);

        feeDomains.add(feeCheckData);
    }

    private String parseTextValue(XMLDocument xmlDoc, String queryPath) throws XPathExpressionException {
        return xmlDoc.getNodeValue(queryPath);
    }

    private FeeCheckData.Command parseCommandNode(XMLDocument xmlDoc, String checkDataQueryPath)
            throws XPathExpressionException {
        final FeeCheckData.Command command = new FeeCheckData.Command(
                xmlDoc.getNodeValue(checkDataQueryPath + CHKDATA_DOMAIN_COMMAND_EXPR));
        command.setPhase(xmlDoc.getNodeValue(checkDataQueryPath + CHKDATA_DOMAIN_COMMAND_PHASE_EXPR));
        command.setSubphase(xmlDoc.getNodeValue(checkDataQueryPath + CHKDATA_DOMAIN_COMMAND_SUBPHASE_EXPR));
        return command;
    }

    private Period parsePeriod(XMLDocument xmlDoc, String checkDataQueryPath) throws XPathExpressionException {
        final String periodValue = parseTextValue(xmlDoc, checkDataQueryPath + CHKDATA_DOMAIN_PERIOD_EXPR);
        final String unitValue = parseTextValue(xmlDoc, checkDataQueryPath + CHKDATA_DOMAIN_PERIOD_UNIT_EXPR);
        return (periodValue != null)
                ? new Period(PeriodUnit.value(unitValue), Integer.parseInt(periodValue))
                : null;
    }

    private void parseFeeNodes(XMLDocument xmlDoc, FeeCheckData feeCheckData, String feeNodesQueryPath)
            throws XPathExpressionException {
        NodeList feeNodes = xmlDoc.getElements(feeNodesQueryPath);
        if (feeNodes != null) {
            for (int feeNodeIndex = 0; feeNodeIndex < feeNodes.getLength(); feeNodeIndex++) {
                Node feeNode = feeNodes.item(feeNodeIndex);
                feeCheckData.addFee(parseFee(feeNode));
            }
        }
    }

    private FeeCheckData.Fee parseFee(Node feeNode) {
        BigDecimal feeValue = new BigDecimal(feeNode.getTextContent());
        String description = feeNode.getAttributes().getNamedItem("description").getTextContent();
        FeeCheckData.Fee fee = new FeeCheckData.Fee(feeValue, description);
        String refundable = feeNode.getAttributes().getNamedItem("refundable") != null
                ? feeNode.getAttributes().getNamedItem("refundable").getTextContent() : null;
        fee.setRefundable("1".equals(refundable));
        return fee;
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public List<FeeCheckData> getFeeDomains() {
        return Collections.unmodifiableList(feeDomains);
    }
}
