package com.ausregistry.jtoolkit2.se.fee;


import com.ausregistry.jtoolkit2.se.*;
import com.ausregistry.jtoolkit2.xml.XMLDocument;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

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
    private Map<String, FeeCheckData> feeDomains = new LinkedHashMap<String, FeeCheckData>();

    @Override
    public void fromXML(XMLDocument xmlDoc) throws XPathExpressionException {

        int cdCount = xmlDoc.getNodeCount(CHKDATA_COUNT_EXPR);
        if (cdCount > 0) {
            FeeCheckData feeCheckData;
            for (int i = 0; i < cdCount; i++) {
                String qry = replaceIndex(CHKDATA_IND_EXPR, i + 1);
                final String domainName = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_NAME_EXPR);
                final String currency = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_CURRENCY_EXPR);
                final FeeCheckData.Command command = new FeeCheckData.Command(
                        xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_COMMAND_EXPR));
                command.setPhase(xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_COMMAND_PHASE_EXPR));
                command.setSubphase(xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_COMMAND_SUBPHASE_EXPR));
                final String feeClass = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_FEE_CLASS_EXPR);

                final String periodValue = xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_PERIOD_EXPR);
                Period period = periodValue == null
                        ? null
                        : new Period(PeriodUnit.value(xmlDoc.getNodeValue(qry + CHKDATA_DOMAIN_PERIOD_UNIT_EXPR)),
                                Integer.parseInt(periodValue));

                feeCheckData = new FeeCheckData(domainName, command);
                feeCheckData.setCurrency(currency);
                feeCheckData.setPeriod(period);
                feeCheckData.setFeeClass(feeClass);

                NodeList feeNodes = xmlDoc.getElements(qry + CHKDATA_FEE_NODES_EXPR);
                for (int j = 0; j < feeNodes.getLength(); j++) {
                    BigDecimal feeValue = feeNodes.item(j).getTextContent() == null ? null
                            : new BigDecimal(feeNodes.item(j).getTextContent());
                    String description = feeNodes.item(j).getAttributes().getNamedItem("description").getTextContent();
                    String refundable = feeNodes.item(j).getAttributes().getNamedItem("refundable") != null
                            ? feeNodes.item(j).getAttributes().getNamedItem("refundable").getTextContent()
                            : null;
                    FeeCheckData.Fee fee = new FeeCheckData.Fee(feeValue, description);
                    fee.setRefundable("1".equals(refundable));
                    feeCheckData.addFee(fee);
                }

                feeDomains.put(domainName, feeCheckData);
            }
            initialised = true;
        } else {
            initialised = false;
        }
    }

    @Override
    public boolean isInitialised() {
        return initialised;
    }

    public Map<String, FeeCheckData> getFeeDomains() {
        return feeDomains;
    }
}
