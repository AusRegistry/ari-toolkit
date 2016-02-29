package com.ausregistry.jtoolkit2.tmdb.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class TmHolderTest {
    private TmHolder tmHolder = new TmHolder();

    @Test
    public void shouldGetTheRightEntitlement() {
        tmHolder.setEntitlement("owner");

        assertThat(tmHolder.getEntitlement(), is("owner"));
    }

    @Test
    public void shouldGetTheSetName() {
        tmHolder.setName("Holder Name");

        assertThat(tmHolder.getName(), is("Holder Name"));
    }

    @Test
    public void shouldGetTheSetOrganisation() {
        tmHolder.setOrganisation("Organisation");

        assertThat(tmHolder.getOrganisation(), is("Organisation"));
    }

    @Test
    public void shouldGetTheSetEmail() {
        tmHolder.setEmail("Email@someplace.com");

        assertThat(tmHolder.getEmail(), is("Email@someplace.com"));
    }

    @Test
    public void shouldGetTheSetVoice() {
        tmHolder.setVoice("+1-654654654");

        assertThat(tmHolder.getVoice(), is("+1-654654654"));
    }

    @Test
    public void shouldGetTheSetVoiceExtension() {
        tmHolder.setVoiceExtension("2");

        assertThat(tmHolder.getVoiceExtension(), is("2"));
    }

    @Test
    public void shouldGetTheSetFax() {
        tmHolder.setFax("+1-654654654");

        assertThat(tmHolder.getFax(), is("+1-654654654"));
    }

    @Test
    public void shouldGetTheSetFaxExtension() {
        tmHolder.setFaxExtension("2");

        assertThat(tmHolder.getFaxExtension(), is("2"));
    }

    @Test
    public void shouldGetTheSetAddress() {
        TmAddress mockAddress = mock(TmAddress.class);
        tmHolder.setAddress(mockAddress);

        assertThat(tmHolder.getAddress(), is(mockAddress));
    }


}
