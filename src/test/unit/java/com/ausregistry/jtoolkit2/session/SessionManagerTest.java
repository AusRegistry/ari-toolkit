package com.ausregistry.jtoolkit2.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.TestEnvironment;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.PollRequestCommand;
import com.ausregistry.jtoolkit2.se.PollResponse;
import com.ausregistry.jtoolkit2.se.Response;
import com.ausregistry.jtoolkit2.se.Result;
import com.ausregistry.jtoolkit2.se.StandardCommandType;

public class SessionManagerTest {
    private static final int PARALLEL_DEGREE = 3;
    private static final long JOIN_TIMEOUT = 10000; // milliseconds
    private static final int SEQUENTIAL_COUNT = 2;
    private SessionManager manager;
    private SessionManagerProperties properties;

    @Before
    public void setUp() throws Exception {
        TestEnvironment env = new TestEnvironment();
        System.setProperty("client.pollinterval.min", "3000");
        properties = env;
        manager = SessionManagerFactory.newInstance(properties);
    }

    @After
    public void tearDown() throws Exception {
        manager.shutdown();
    }

    @Test
    public void testChangePassword() {
        try {
            manager.startup();
            String oldPW = properties.getSessionProperties().getClientPW();
            String newPW = "23wejfd3wD*Sk*#";
            manager.changePassword(oldPW, newPW);
            manager.changePassword(newPW, oldPW);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testConfigure() {
        try {
            manager.configure(properties);
        } catch (ConfigurationException ce) {
            fail(ce.getMessage());
        }
    }

    @Test
    public void testStartup() {
        try {
            manager.startup();
        } catch (SessionOpenException soe) {
            fail(soe.getMessage());
        } catch (SessionConfigurationException sce) {
            fail(sce.getMessage());
        }
    }

    @Test
    public void testShutdown() {
        try {
            manager.startup();
        } catch (Exception e) {
        }

        manager.shutdown();
        try {
            Transaction tx = new Transaction(new PollRequestCommand(),
                    new PollResponse());
            manager.execute(tx);
            fail("execute should fail after shutdown");
        } catch (Exception e) {
        }
    }

    @Test
    public void testRun() {
        try {
            Timer.setTime("20070101.010101");
            manager.startup();
            Thread t = new Thread(manager);
            t.start();
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Timer.setTime("20070101.011001");
            t.interrupt();
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            StatsViewer sv = manager.getStatsViewer();

            long timeout = 10000L; // ms
            long elapsedTime = 0L;
            long shortSleep = 200L;
            long pc;
            do {
                try {
                    Thread.sleep(shortSleep);
                } catch (InterruptedException ie) {
                }
                elapsedTime += shortSleep;
                pc = sv.getCommandCount(StandardCommandType.POLL);
            } while (pc == 0L && elapsedTime < timeout);
            assertEquals(Long.valueOf(1L), Long.valueOf(pc));
            manager.shutdown();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testExecuteParallelTransactions() {
        final Transaction[][] txs = new Transaction[PARALLEL_DEGREE][SEQUENTIAL_COUNT];
        final SessionManager sessMan = manager;

        for (int i = 0; i < PARALLEL_DEGREE; i++) {
            for (int j = 0; j < SEQUENTIAL_COUNT; j++) {
                txs[i][j] = new Transaction(new PollRequestCommand(), new Response());
            }
        }

        Thread[] threads = new Thread[PARALLEL_DEGREE];
        for (int i = 0; i < PARALLEL_DEGREE; i++) {
            final int txIdx = i;
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    int errIdx = 0;
                    try {
                        errIdx = sessMan.execute(txs[txIdx]);
                    } catch (Exception e) {
                        fail(e.getMessage());
                    }

                    if (errIdx < SEQUENTIAL_COUNT) {
                        fail("Executed fewer than requested transactions: "
                                + String.valueOf(errIdx) + " < "
                                + String.valueOf(SEQUENTIAL_COUNT));
                    }
                }
            });
        }

        try {
            manager.startup();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join(JOIN_TIMEOUT);
            } catch (InterruptedException e) {
                fail(e.getMessage());
            }
        }

        for (int i = 0; i < txs.length; i++) {
            for (int j = 0; j < txs[i].length; j++) {
                Response response = txs[i][j].getResponse();
                Result[] result = response.getResults();
                if (result == null) {
                    fail("Transaction (" + String.valueOf(i + 1) + ","
                            + String.valueOf(j + 1)
                            + ") has no result. Transaction state: "
                            + txs[i][j].getState().toString());
                } else {
                    int code = result[0].getResultCode();
                    assertTrue(code == 1300 || code == 1301);
                }
            }
        }

        manager.shutdown();
    }

    @Test
    public void testExecuteTransaction() {
        Response response = new PollResponse();
        Transaction tx = new Transaction(new PollRequestCommand(), response);
        try {
            manager.startup();
            manager.execute(tx);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testExecuteTransactionArray() {
        Command[] commands = new PollRequestCommand[2];
        Response[] responses = new PollResponse[2];
        Transaction[] txs = new Transaction[2];

        for (int i = 0; i < 2; i++) {
            commands[i] = new PollRequestCommand();
            responses[i] = new PollResponse();
            txs[i] = new Transaction(commands[i], responses[i]);
        }

        try {
            manager.startup();
            manager.execute(txs);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetCommandCount() {
        try {
            manager.startup();
            StatsViewer sv = manager.getStatsViewer();
            long cc = sv.getCommandCount();
            assertEquals(1L, cc);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetCommandCountCommandType() {
        try {
            manager.startup();
            StatsViewer sv = manager.getStatsViewer();
            long cc = sv.getCommandCount(StandardCommandType.CREATE);
            assertEquals(0L, cc);
            cc = sv.getCommandCount(StandardCommandType.LOGIN);
            assertEquals(Long.valueOf(1), Long.valueOf(cc));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetStatsViewer() {
        StatsViewer sv = manager.getStatsViewer();
        assertNotNull(sv);
    }

}
