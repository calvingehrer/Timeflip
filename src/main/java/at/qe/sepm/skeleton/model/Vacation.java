package at.qe.sepm.skeleton.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.Instant;

/**
 * Entity representing Vacation.
 *
 */


public class Vacation {

    @Temporal(TemporalType.TIMESTAMP)
    private Instant start;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant end;

    private static Long MAX_DAYS;


    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public static Long getMaxDays() {
        return MAX_DAYS;
    }

    public static void setMaxDays(Long maxDays) {
        MAX_DAYS = maxDays;
    }


}
