package com.ausregistry.jtoolkit2.se;

import com.ausregistry.jtoolkit2.xml.XMLDocument;

import javax.xml.xpath.XPathExpressionException;

/**
 * Use this to access ENUM domain object information as provided in an EPP domain
 * info response with an optional e164epp extension, compliant with RFC5730,
 * RFC5731 and RFC4114.  Such a service element is sent by a compliant EPP server in
 * response to a valid domain info command requesting information about an ENUM
 * domain, as implemented by the DomainInfoCommand class.
 *
 * @see com.ausregistry.jtoolkit2.se.DomainInfoCommand
 */
public final class EnumDomainInfoResponse extends DomainInfoResponse {
    private static final long serialVersionUID = -7813953838538474917L;

    private static final String E164_INF_DATA_EXPR = "/e:epp/e:response/e:extension/e164epp:infData";
    private static final String NAPTR_COUNT_EXPR = "count(" + E164_INF_DATA_EXPR + "/e164epp:naptr)";
    private static final String NAPTR_IND_EXPR = exprReplace(E164_INF_DATA_EXPR) + "/e164epp:naptr[IDX]";
    private static final String NAPTR_ORDER_EXPR = "/e164epp:order/text()";
    private static final String NAPTR_PREF_EXPR = "/e164epp:pref/text()";
    private static final String NAPTR_FLAGS_EXPR = "/e164epp:flags/text()";
    private static final String NAPTR_SVC_EXPR = "/e164epp:svc/text()";
    private static final String NAPTR_REGEX_EXPR = "/e164epp:regex/text()";
    private static final String NAPTR_REPL_EXPR = "/e164epp:repl/text()";

    private NAPTR[] naptrs;

    public EnumDomainInfoResponse() {
        super();
    }

    protected static String exprReplace(String expr) {
        return expr.replaceAll(OBJ, "e164epp");
    }
    public NAPTR[] getNAPTRs() {
        return naptrs;
    }

    @Override
    public void fromXML(XMLDocument xmlDoc) {
        super.fromXML(xmlDoc);

        if (!resultArray[0].succeeded()) {
            return;
        }

        try {
            int naptrCount = xmlDoc.getNodeCount(NAPTR_COUNT_EXPR);
            naptrs = new NAPTR[naptrCount];

            for (int i = 0; i < naptrCount; i++) {
                String qry = ReceiveSE.replaceIndex(NAPTR_IND_EXPR, i + 1);

                String order = xmlDoc.getNodeValue(qry + NAPTR_ORDER_EXPR);
                String pref = xmlDoc.getNodeValue(qry + NAPTR_PREF_EXPR);
                String flags = xmlDoc.getNodeValue(qry + NAPTR_FLAGS_EXPR);
                String svc = xmlDoc.getNodeValue(qry + NAPTR_SVC_EXPR);
                String regex = xmlDoc.getNodeValue(qry + NAPTR_REGEX_EXPR);
                String repl = xmlDoc.getNodeValue(qry + NAPTR_REPL_EXPR);

                naptrs[i] = new NAPTR(
                        Integer.parseInt(order), Integer.parseInt(pref),
                        flags.toCharArray(), svc, regex, repl);
            }
        } catch (XPathExpressionException xpee) {
            maintLogger.warning(xpee.getMessage());
        }
    }
}

