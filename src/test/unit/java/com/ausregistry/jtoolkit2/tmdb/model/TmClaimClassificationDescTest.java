package com.ausregistry.jtoolkit2.tmdb.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TmClaimClassificationDescTest {

    @Test
    public void shouldHaveCorrectClassNumber() {
        TmClaimClassificationDesc classDesc = new TmClaimClassificationDesc(35, "description");
        assertThat(classDesc.getClassNumber(), is(35));
    }

    @Test
    public void shouldHaveCorrectDescription() {
        TmClaimClassificationDesc classDesc = new TmClaimClassificationDesc(35, "description");
        assertThat(classDesc.getDescription(), is("description"));
    }
}
