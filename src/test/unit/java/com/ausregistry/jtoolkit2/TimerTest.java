package com.ausregistry.jtoolkit2;

import java.text.SimpleDateFormat;

import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimerTest {
    private static final String OK_TIME_STRING = "20070101.010101";
    private static final String BAD_TIME_STRING = "200x0101.010101";

    @Test
    public void testSetTimePass() {
        Timer.setTime(OK_TIME_STRING);
        assertEquals(
                new SimpleDateFormat("yyyyMMdd.HHmmss").format(new Date(Timer.now())),
                OK_TIME_STRING);
    }

    @Test
    public void testSetTimeFail() {
        Timer.setTime(BAD_TIME_STRING);
        assertNotSame(
                new SimpleDateFormat("yyyyMMdd.HHmmss").format(new Date(Timer.now())),
                BAD_TIME_STRING);
    }
}

