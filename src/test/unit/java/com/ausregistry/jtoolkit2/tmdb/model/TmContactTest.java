package com.ausregistry.jtoolkit2.tmdb.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class TmContactTest {

    private TmContact tmContact = new TmContact();

    @Test
    public void shouldGetTheRightType() {
        tmContact.setType("owner");

        assertThat(tmContact.getType(), is("owner"));
    }

    @Test
    public void shouldGetTheSetName() {
        tmContact.setName("Contact Name");

        assertThat(tmContact.getName(), is("Contact Name"));
    }

    @Test
    public void shouldGetTheSetOrganisation() {
        tmContact.setOrganisation("Organisation");

        assertThat(tmContact.getOrganisation(), is("Organisation"));
    }

    @Test
    public void shouldGetTheSetEmail() {
        tmContact.setEmail("Email@someplace.com");

        assertThat(tmContact.getEmail(), is("Email@someplace.com"));
    }

    @Test
    public void shouldGetTheSetVoice() {
        tmContact.setVoice("+1-654654654");

        assertThat(tmContact.getVoice(), is("+1-654654654"));
    }

    @Test
    public void shouldGetTheSetVoiceExtension() {
        tmContact.setVoiceExtension("2");

        assertThat(tmContact.getVoiceExtension(), is("2"));
    }

    @Test
    public void shouldGetTheSetFax() {
        tmContact.setFax("+1-654654654");

        assertThat(tmContact.getFax(), is("+1-654654654"));
    }

    @Test
    public void shouldGetTheSetFaxExtension() {
        tmContact.setFaxExtension("2");

        assertThat(tmContact.getFaxExtension(), is("2"));
    }

    @Test
    public void shouldGetTheSetAddress() {
        TmAddress mockAddress = mock(TmAddress.class);
        tmContact.setAddress(mockAddress);

        assertThat(tmContact.getAddress(), is(mockAddress));
    }


}
