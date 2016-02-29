package com.ausregistry.jtoolkit2.xml;

import java.util.logging.Logger;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.ausregistry.jtoolkit2.ErrorPkg;

/**
 * Provide a Schema object for use in validating XML documents.
 *
 * Uses the debug and user level loggers.
 */
public final class EPPSchemaProvider {
    private static String pname;

    private static final EPPResolver DEFAULT_RESOLVER = new EPPResolver();
    private static final SchemaFactory SCHEMA_FACTORY =
            SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private static Schema schema = null;
    private static boolean isValidating = true;

    static {
        pname = EPPSchemaProvider.class.getPackage().getName();
        try {
            init();
        } catch (SAXParseException saxpe) {
            Logger.getLogger(pname + ".debug").warning(
                    ErrorPkg.getMessage("xml.validation.error", new String[] {
                            "<<line>>", "<<column>>", "<<message>>"
                    }, new String[] {
                            String.valueOf(saxpe.getLineNumber()),
                            String.valueOf(saxpe.getColumnNumber()),
                            saxpe.getMessage()
                    }));
            saxpe.printStackTrace();
        } catch (Exception e) {
            Logger.getLogger(pname + ".user").warning(e.getMessage());
            e.printStackTrace();
        }
    }

    private EPPSchemaProvider() {
        // intentionally do nothing, required by checkstyle
    }

    /**
     * Configure default schema validation sources which will apply to all
     * instances of this class.
     */
    public static void init() throws TransformerException, SAXException {
        init(DEFAULT_RESOLVER);
    }

    /**
     * Configure schema validation sources which will apply to all instances of
     * this class.
     *
     * @param resolver The object used to resolve URIs to local schema
     * definition resources.
     */
    public static void init(EPPResolver resolver) throws TransformerException,
            SAXException {

        String[] namespaceURIs = resolver.getResolvedURIs();
        Source[] sources = new Source[namespaceURIs.length];

        for (int i = 0; i < sources.length; i++) {
            sources[i] = resolver.resolve(namespaceURIs[i], null);
        }

        schema = SCHEMA_FACTORY.newSchema(sources);
    }

    /**
     * Set whether XML Schema validation should be performed by the system.
     * This affects whether other classes validate XML documents via the
     * result of <a href="#getSchema()">getSchema</a>.
     */
    public static void setValidating(boolean validating) {
        isValidating = validating;
    }

    /**
     * Get the XML Schema object configured by this provider class for use in
     * validating XML documents.
     */
    public static Schema getSchema() {
        if (!EPPSchemaProvider.isValidating) {
            return null;
        } else if (schema == null) {
            try {
                EPPSchemaProvider.init();
            } catch (SAXException saxe) {
                Logger.getLogger(pname + ".user").warning(saxe.getMessage());
                Logger.getLogger(pname + ".user").warning(
                        ErrorPkg.getMessage("xml.validation.schemaload.saxerr.disabled"));
                return null;
            } catch (TransformerException te) {
                Logger.getLogger(pname + ".user").warning(te.getMessage());
                Logger.getLogger(pname + ".user").warning(
                        ErrorPkg.getMessage("xml.validation.schemaload.transform.disabled"));
                return null;
            }
        }

        return schema;
    }
}
