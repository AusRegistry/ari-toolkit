package com.ausregistry.jtoolkit2.tmdb.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class TmCourtTest {

    private TmCourt tmCourt = new TmCourt();

    @Test
    public void shouldGetTheSetReferenceNumber() {
        tmCourt.setReferenceNumber(234235L);

        assertThat(tmCourt.getReferenceNumber(), is(234235L));
    }

    @Test
    public void shouldGetTheSetCountryCode() {
        tmCourt.setCountryCode("AU");

        assertThat(tmCourt.getCountryCode(), is("AU"));
    }

    @Test
    public void shouldGetAllTheRegionsAdded() {
        tmCourt.addRegion("region1");
        tmCourt.addRegion("region2");
        tmCourt.addRegion("region3");

        assertThat(tmCourt.getRegions(), is(Arrays.asList("region1", "region2", "region3")));
    }

    @Test
    public void shouldGetTheSetCourtName() {
        tmCourt.setCourtName("Court Name");

        assertThat(tmCourt.getCourtName(), is("Court Name"));
    }
}
