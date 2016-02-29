package com.ausregistry.jtoolkit2.xml;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;

import org.junit.Assert;
import org.junit.Test;

public class EPPResolverTest {


    @Test
    public void canFindXSDs() throws TransformerException {

        EPPResolver resolver = new EPPResolver();
        String[] namespaceURIs = resolver.getResolvedURIs();
        Source[] sources = new Source[namespaceURIs.length];

        for (int i = 0; i < sources.length; i++) {
            sources[i] = resolver.resolve(namespaceURIs[i], null);
            Assert.assertNotNull("Could not find XSD: " + namespaceURIs[i], sources[i]);
        }
    }
}
