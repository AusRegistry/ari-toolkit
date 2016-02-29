package com.ausregistry.jtoolkit2.tmdb.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.GregorianCalendar;

import org.junit.Test;

public class TmNoticeTest {

    private TmNotice tmNotice = new TmNotice();

    @Test
    public void shouldGetTheId() {
        tmNotice.setId("ID");

        assertThat(tmNotice.getId(), is("ID"));
    }

    @Test
    public void shouldGetTheSetNotBeforeDateTime() {
        GregorianCalendar mockCalendar = mock(GregorianCalendar.class);
        tmNotice.setNotBeforeDateTime(mockCalendar);

        assertThat(tmNotice.getNotBeforeDateTime(), is(mockCalendar));
    }

    @Test
    public void shouldGetTheSetNotAfterDateTime() {
        GregorianCalendar mockCalendar = mock(GregorianCalendar.class);
        tmNotice.setNotAfterDateTime(mockCalendar);

        assertThat(tmNotice.getNotAfterDateTime(), is(mockCalendar));
    }

    @Test
    public void shouldGetAllTheAddedClaims() {
        TmClaim mockClaimOne = mock(TmClaim.class);
        TmClaim mockClaimTwo = mock(TmClaim.class);

        tmNotice.addClaim(mockClaimOne);
        tmNotice.addClaim(mockClaimTwo);

        assertThat(tmNotice.getClaims(), is(Arrays.asList(mockClaimOne, mockClaimTwo)));
    }

    @Test
    public void shouldGetTheLabel() {
        tmNotice.setLabel("label");

        assertThat(tmNotice.getLabel(), is("label"));
    }
}
