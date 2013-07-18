package com.ausregistry.jtoolkit2.test.infrastructure;

import org.mockito.Matchers;
import org.w3c.dom.Node;

public class ToolkitMatchers {
    public static Node isNodeForXml(String xmlString) {
        return Matchers.argThat(new NodeForXml(xmlString));
    }
}