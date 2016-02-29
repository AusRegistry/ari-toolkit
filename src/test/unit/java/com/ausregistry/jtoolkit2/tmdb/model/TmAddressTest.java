package com.ausregistry.jtoolkit2.tmdb.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class TmAddressTest {
    private TmAddress tmAddress = new TmAddress();

    @Test
    public void shouldGetAllAddedStreets() {
        tmAddress.addStreet("street 1");
        tmAddress.addStreet("street 2");
        tmAddress.addStreet("street 3");

        assertThat(tmAddress.getStreets(), is(Arrays.asList("street 1", "street 2", "street 3")));
    }

    @Test
    public void shouldGetTheSetCity() {
        tmAddress.setCity("city");

        assertThat(tmAddress.getCity(), is("city"));
    }

    @Test
    public void shouldGetTheSetStateOrProvince() {
        tmAddress.setStateOrProvince("VIC");

        assertThat(tmAddress.getStateOrProvince(), is("VIC"));
    }

    @Test
    public void shouldGetTheSetPostalCode() {
        tmAddress.setPostalCode("3141");

        assertThat(tmAddress.getPostalCode(), is("3141"));
    }

    @Test
    public void shouldGetTheSetCountryCode() {
        tmAddress.setCountryCode("AU");

        assertThat(tmAddress.getCountryCode(), is("AU"));
    }

}
