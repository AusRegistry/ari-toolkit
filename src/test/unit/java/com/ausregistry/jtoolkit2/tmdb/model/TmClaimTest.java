package com.ausregistry.jtoolkit2.tmdb.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.Test;

public class TmClaimTest {

    private TmClaim tmClaim = new TmClaim();

    @Test
    public void shouldGetTheSetMarkName() {
        tmClaim.setMarkName("MarkName");

        assertThat(tmClaim.getMarkName(), is("MarkName"));
    }

    @Test
    public void shouldGetTheSetJurisdiction() {
        tmClaim.setJurisdiction("Jurisdiction");

        assertThat(tmClaim.getJurisdiction(), is("Jurisdiction"));
    }


    @Test
    public void shouldGetTheSetJurisdictioncc() {
        tmClaim.setJurisdictionCC("JurisdictionCC");

        assertThat(tmClaim.getJurisdictionCC(), is("JurisdictionCC"));
    }


    @Test
    public void shouldGetTheSetGoodsAndServices() {
        tmClaim.setGoodsAndServices("GoodsAndServices");

        assertThat(tmClaim.getGoodsAndServices(), is("GoodsAndServices"));
    }

    @Test
    public void shouldGetAllTheAddedClassificationDesc() {
        TmClaimClassificationDesc mockClassDescOne = mock(TmClaimClassificationDesc.class);
        TmClaimClassificationDesc mockClassDescTwo = mock(TmClaimClassificationDesc.class);

        tmClaim.addClassificationDesc(mockClassDescOne);
        tmClaim.addClassificationDesc(mockClassDescTwo);

        assertThat(tmClaim.getClassificationDescriptions(), is(Arrays.asList(mockClassDescOne, mockClassDescTwo)));
    }

    @Test
    public void shouldGetAllTheAddedHolder() {
        TmHolder mockClassDescOne = mock(TmHolder.class);
        TmHolder mockClassDescTwo = mock(TmHolder.class);

        tmClaim.addHolder(mockClassDescOne);
        tmClaim.addHolder(mockClassDescTwo);

        assertThat(tmClaim.getHolders(), is(Arrays.asList(mockClassDescOne, mockClassDescTwo)));
    }

    @Test
    public void shouldGetAllTheAddedContact() {
        TmContact mockClassDescOne = mock(TmContact.class);
        TmContact mockClassDescTwo = mock(TmContact.class);

        tmClaim.addContact(mockClassDescOne);
        tmClaim.addContact(mockClassDescTwo);

        assertThat(tmClaim.getContacts(), is(Arrays.asList(mockClassDescOne, mockClassDescTwo)));
    }

    @Test
    public void shouldGetAllTheAddedUdrp() {
        TmUdrp mockClassDescOne = mock(TmUdrp.class);
        TmUdrp mockClassDescTwo = mock(TmUdrp.class);

        tmClaim.addUdrp(mockClassDescOne);
        tmClaim.addUdrp(mockClassDescTwo);

        assertThat(tmClaim.getUdrps(), is(Arrays.asList(mockClassDescOne, mockClassDescTwo)));
    }

    @Test
    public void shouldGetAllTheAddedCourt() {
        TmCourt mockClassDescOne = mock(TmCourt.class);
        TmCourt mockClassDescTwo = mock(TmCourt.class);

        tmClaim.addCourt(mockClassDescOne);
        tmClaim.addCourt(mockClassDescTwo);

        assertThat(tmClaim.getCourts(), is(Arrays.asList(mockClassDescOne, mockClassDescTwo)));
    }
}
