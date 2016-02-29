package com.ausregistry.jtoolkit2.session;

import java.util.HashMap;

/**
 * Keep track of the number of each result code received in responses.
 * Instances of this class provide no synchronization.  If multiple threads
 * access an instance concurrently, they must provide their own
 * synchronization.  Specifically, concurrent executions of
 * <code>increment</code> may exhibit unexpected behaviour.
 */
public class ResultCounter {
    // exp # result codes in a normal session.
    private static final int INITIAL_SIZE = 6;

    private long total;
    private HashMap<Integer, Long> map;

    public ResultCounter() {
        map = new HashMap<Integer, Long>(INITIAL_SIZE);
        total = 0L;
    }

    public void increment(int code) {
        if (map.containsKey(code)) {
            long val = map.get(code);
            map.put(code, val + 1);
        } else {
            map.put(code, 1L);
        }
        total++;
    }

    public long getValue(int code) {
        if (map.containsKey(code)) {
            return map.get(code);
        } else {
            return 0L;
        }
    }

    public long getTotal() {
        return total;
    }
}

