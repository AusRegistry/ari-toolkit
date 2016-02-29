package com.ausregistry.jtoolkit2.session;

import com.ausregistry.jtoolkit2.Timer;

import com.ausregistry.jtoolkit2.se.CommandType;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Keep track of how many commands of each type have been processed recently.
 * Instances of this class provide no synchronization.  If multiple threads
 * access an instance concurrently, they must provide their own
 * synchronization.  Specifically, concurrent invocations of
 * <code>increment</code> may exhibit unexpected behaviour.
 *
 * @author anthony (anthony@ausregistry.com.au)
 */
public class CommandCounter {
    /// exp number of command types in a normal session.
    private static final int INITIAL_TYPE_MAP_SIZE = 8;
    /// 95% CI value for number of commands per reset interval.
    private static final int INITIAL_TIME_LIST_SIZE = 3;
    /// Default time interval for retaining records of command processing.
    private static final long DEFAULT_RESET_INTERVAL = 1000;

    private Map<String, List<Long>> recentMap;
    private long resetInterval; // milliseconds
    private int recentTotal;
    private Map<String, Long> totalMap;
    private long total;

    {
        recentMap = new HashMap<String, List<Long>>(INITIAL_TYPE_MAP_SIZE);
        totalMap = new HashMap<String, Long>(INITIAL_TYPE_MAP_SIZE);
        recentTotal = 0;
        total = 0L;
    }

    /**
     * Construct a command counter using the default reset interval.
     */
    public CommandCounter() {
        this(DEFAULT_RESET_INTERVAL);
    }

    /**
     * Construct a command counter which ages command processing records out
     * based on the given reset interval (in milliseconds).
     *
     * @param resetInterval the interval of time over which counts are taken.
     */
    public CommandCounter(long resetInterval) {
        this.resetInterval = resetInterval;
    }

    /**
     * Increment the count of commands of the given type processed recently.
     *
     * @param type the type of command being processed.
     */
    public void increment(CommandType type) {
        String name = type.getCommandName();

        // list of times in milliseconds
        List<Long> timeList;
        if (totalMap.containsKey(name)) {
            long val = totalMap.get(name) + 1;
            totalMap.put(name, val);
        } else {
            totalMap.put(name, 1L);
        }

        if (recentMap.containsKey(name)) {
            timeList = recentMap.get(name);

            cleanAndCheckEmpty(timeList);
        } else {
            timeList = new ArrayList<Long>(INITIAL_TIME_LIST_SIZE);
            recentMap.put(name, timeList);
        }

        timeList.add(Timer.now());
        recentTotal++;
        total++;
    }

    /**
     * Get the number of commands of the given type processed recently (within
     * the reset interval from now).
     */
    public int getRecentCount(CommandType type) {
        String name = type.getCommandName();

        if (!recentMap.containsKey(name)) {
            return 0;
        } else {
            List<Long> timeList = recentMap.get(name);
            boolean allRemoved = cleanAndCheckEmpty(timeList);

            if (allRemoved) {
                reset(name);
                return 0;
            } else {
                return timeList.size();
            }
        }
    }

    /**
     * Get the total number of commands of the given type recorded by this
     * counter.
     */
    public long getCount(CommandType type) {
        String name = type.getCommandName();

        if (!totalMap.containsKey(name)) {
            return 0L;
        } else {
            return totalMap.get(name);
        }
    }

    /**
     * Get the total number of commands of all types recorded by this counter.
     */
    public long getTotal() {
        return total;
    }

    /**
     * Get an approximation of the total number of commands processed
     * recently.  This is actually an upper bound on the actual number of
     * commands processed recently, as the underlying per-command type lists
     * are not necessarily up-to-date.  For the exact number, but at
     * significantly greater cost in execution time, use
     * <code>getExactTotal</code>.
     */
    public int getRecentTotal() {
        return recentTotal;
    }

    /**
     * Get the total number of commands processed recently.  This first cleans
     * up the underlying per-command type lists to guarantee that only recent
     * commands are counted in the total.
     */
    public int getExactRecentTotal() {
        for (Map.Entry<String, List<Long>> entry : recentMap.entrySet()) {
            clean(entry.getValue(), entry.getKey());
        }

        return recentTotal;
    }

    private void reset(String type) {
        recentMap.remove(type);
    }

    /**
     * Iterate through the list, removing old items, until an item is found
     * that is more recent than <code>resetInterval</code> milliseconds ago.
     * Since the list is naturally ordered by time, no later items need be
     * checked.  Also, it is expected that the list will be short enough that
     * linear search will yield acceptable performance.
     *
     * @return Indicate whether the list is left empty upon return.  That is,
     * whether <code>timeList.isEmpty()</code> would return true.
     */
    private synchronized boolean cleanAndCheckEmpty(List<Long> timeList) {
        ListIterator<Long> listIter = timeList.listIterator();

        while (listIter.hasNext()) {
            if (Timer.msDiff(listIter.next()) > resetInterval) {
                listIter.remove();
                recentTotal--;
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * Perform cleanAndCheckEmpty, then <code>reset</code> the appropriate
     * entry in <code>recentMap</code> if the cleanup removes all records from
     * the list.
     */
    private void clean(List<Long> timeList, String type) {
        cleanAndCheckEmpty(timeList);
    }
}

