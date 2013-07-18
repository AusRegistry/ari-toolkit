package com.ausregistry.jtoolkit2.tmdb.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TmUdrpTest {

    private TmUdrp tmUdrp = new TmUdrp();


    @Test
    public void shoulGetTheSetCaseNumber() {
        tmUdrp.setCaseNumber("caseNumber");

        assertThat(tmUdrp.getCaseNumber(), is("caseNumber"));
    }

    @Test
    public void shouldGetTheSetUdrpProvider() {
        tmUdrp.setUdrpProvider("provider");

        assertThat(tmUdrp.getUdrpProvider(), is("provider"));
    }
}
