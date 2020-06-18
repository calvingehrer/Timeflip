package at.qe.sepm.skeleton.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum to represent the Interval for the emails
 */

public enum Interval {
    DAILY,
    WEEKLY,
    MONTHLY,
    NONE;

    public static List<String> getAllIntervals() {
        List<String> intervals = new ArrayList<String>();
        intervals.add("DAILY");
        intervals.add("WEEKLY");
        intervals.add("MONTHLY");
        intervals.add("NONE");
        return intervals;
    }
}
