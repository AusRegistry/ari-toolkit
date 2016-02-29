package com.ausregistry.jtoolkit2.tmdb.xml;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

import com.ausregistry.jtoolkit2.tmdb.model.TmUdrp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Node;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TmUdrpXmlParser.class})
public class TmUdrpXmlParserTest {
    @Mock
    private TmUdrp mockUdrp;

    private TmUdrpXmlParser tmUdrpXmlParser = new TmUdrpXmlParser();

    @Before
    public void setUp() throws Exception {
        whenNew(TmUdrp.class).withNoArguments().thenReturn(mockUdrp);
    }

    @Test
    public void shouldParseCaseNumber() throws Exception {
        String udrpXml = "<tmNotice:udrp><tmNotice:caseNo>D2003-0499</tmNotice:caseNo></tmNotice:udrp>";

        TmUdrp tmUdrp = tmUdrpXmlParser.parse(nodeForString(udrpXml));

        assertThat(tmUdrp, sameInstance(mockUdrp));
        verify(mockUdrp).setCaseNumber("D2003-0499");
    }

    @Test
    public void shouldParseUdrpProvider() throws Exception {
        String udrpXml = "<tmNotice:udrp><tmNotice:udrpProvider>WIPO</tmNotice:udrpProvider></tmNotice:udrp>";

        tmUdrpXmlParser.parse(nodeForString(udrpXml));

        verify(mockUdrp).setUdrpProvider("WIPO");
    }

    private Node nodeForString(String xmlFragment) throws Exception {
        return DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xmlFragment.getBytes()))
                .getDocumentElement();
    }
}
