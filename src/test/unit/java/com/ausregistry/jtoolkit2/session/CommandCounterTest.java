package com.ausregistry.jtoolkit2.session;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.se.StandardCommandType;
import com.ausregistry.jtoolkit2.Timer;

/**
 * @author anthony
 *
 */
public class CommandCounterTest {
    private CommandCounter counter;

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        Timer.setTime("20070101.010101");
        counter = new CommandCounter(5000);
        counter.increment(StandardCommandType.LOGIN);
        counter.increment(StandardCommandType.CHECK);
        counter.increment(StandardCommandType.LOGOUT);
    }

    /**
     * Test method for {@link com.ausregistry.jtoolkit2.session.CommandCounter#increment(com.ausregistry.jtoolkit2.se.CommandType)}.
     */
    @Test
    public void testIncrement() {
        assertEquals(Long.valueOf(0L), Long.valueOf(counter.getCount(StandardCommandType.POLL)));
        counter.increment(StandardCommandType.POLL);
        assertEquals(Long.valueOf(1L), Long.valueOf(counter.getCount(StandardCommandType.POLL)));
    }

    /**
     * Test method for {@link com.ausregistry.jtoolkit2.session.CommandCounter#getCount(com.ausregistry.jtoolkit2.se.CommandType)}.
     */
    @Test
    public void testGetCount() {
        assertEquals(Long.valueOf(1L), Long.valueOf(counter.getCount(StandardCommandType.LOGIN)));
        assertEquals(Long.valueOf(1L), Long.valueOf(counter.getCount(StandardCommandType.CHECK)));
        assertEquals(Long.valueOf(1L), Long.valueOf(counter.getCount(StandardCommandType.LOGOUT)));
    }

    /**
     * Test method for {@link com.ausregistry.jtoolkit2.session.CommandCounter#getRecentCount(com.ausregistry.jtoolkit2.se.CommandType)}.
     */
    @Test
    public void testGetRecentCount() {
        assertEquals(Long.valueOf(1L), Long.valueOf(counter.getRecentCount(StandardCommandType.LOGIN)));
        assertEquals(Long.valueOf(1L), Long.valueOf(counter.getRecentCount(StandardCommandType.CHECK)));
        assertEquals(Long.valueOf(1L), Long.valueOf(counter.getRecentCount(StandardCommandType.LOGOUT)));
    }

    /**
     * Test method for {@link com.ausregistry.jtoolkit2.session.CommandCounter#getTotal()}.
     */
    @Test
    public void testGetTotal() {
        assertEquals(Long.valueOf(3L), Long.valueOf(counter.getTotal()));
    }

    /**
     * Test method for {@link com.ausregistry.jtoolkit2.session.CommandCounter#getRecentTotal()}.
     */
    @Test
    public void testGetRecentTotal() {
        assertEquals(Long.valueOf(3L), Long.valueOf(counter.getRecentTotal()));
        // 1 minute later, recent total should be the same.
        Timer.setTime("20070101.010201");
        assertEquals(Long.valueOf(3L), Long.valueOf(counter.getRecentTotal()));
    }

    /**
     * Test method for {@link com.ausregistry.jtoolkit2.session.CommandCounter#getExactRecentTotal()}.
     */
    @Test
    public void testGetExactRecentTotal() {
        assertEquals(Long.valueOf(3L), Long.valueOf(counter.getExactRecentTotal()));
        // 2 seconds later, exact recent total should be unchanged.
        Timer.setTime("20070101.010103");
        assertEquals(Long.valueOf(3L), Long.valueOf(counter.getExactRecentTotal()));
        // 7 seconds later, all commands are considered old (> 5s).
        Timer.setTime("20070101.010108");
        assertEquals(Long.valueOf(0L), Long.valueOf(counter.getExactRecentTotal()));
        counter.increment(StandardCommandType.CHECK);
        // 1 second later, only the check command is considered recent.
        Timer.setTime("20070101.010109");
        assertEquals(Long.valueOf(1L), Long.valueOf(counter.getExactRecentTotal()));
    }
}

