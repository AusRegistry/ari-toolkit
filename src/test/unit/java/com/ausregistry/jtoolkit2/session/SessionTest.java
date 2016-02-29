package com.ausregistry.jtoolkit2.session;

import static org.junit.Assert.*;

import com.ausregistry.jtoolkit2.TestEnvironment;
import com.ausregistry.jtoolkit2.se.StandardCommandType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SessionTest {
    private static final String TEST_SE =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\"><hello/></epp>";
    private Session session;
    private SessionProperties props;

    @Before
    public void setUp() throws Exception {
        props = new TestEnvironment();
        session = SessionFactory.newInstance(props);
    }

    @After
    public void tearDown() throws Exception {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOpen() throws Exception {
        try {
            session.open();
        } catch (Throwable t) {
            fail(t.toString());
        }

        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClose() throws Exception {
        try {
            session.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            session.close();
        } catch (Throwable t) {
            fail(t.toString());
        }
    }

    @Test
    public void testSend() throws Exception {
        try {
            session.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            session.write(TEST_SE);
            session.read();
        } catch (Throwable t) {
            fail(t.toString());
        }
    }

    @Test
    public void testRecv() throws Exception {
        try {
            session.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            session.write(TEST_SE);
            String response = session.read();
            assertNotNull(response);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testIncCommandCounter() throws Exception {
        try {
            session.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            StatsViewer sv = session.getStatsManager();
            assert sv != null;
            long cc = sv.getCommandCount();
            long lc = sv.getCommandCount(StandardCommandType.LOGIN);
            assertEquals(1L, cc);
            assertEquals(1L, lc);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testIncResultCounter() throws Exception {
        try {
            session.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            StatsManager sm = session.getStatsManager();
            sm.incResultCounter(1000);
            assertEquals(Long.valueOf(sm.getResultCodeCount(1000)), Long.valueOf(1));
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testGetCommandCount() {
        try {
            session.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            StatsViewer sv = session.getStatsManager();
            assert sv != null;
            long cc = sv.getCommandCount();
            long lic = sv.getCommandCount(StandardCommandType.LOGIN);
            long loc = sv.getCommandCount(StandardCommandType.LOGOUT);
            assertEquals(1L, cc);
            assertEquals(1L, lic);
            assertEquals(0L, loc);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testGetResultCodeCount() {
        try {
            session.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            StatsManager sm = session.getStatsManager();
            assertEquals(Long.valueOf(sm.getResultCodeCount(1000)), Long.valueOf(0));
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
