package at.qe.sepm.skeleton.model;

import java.time.Instant;

/**
 * Entity representing Vacation.
 *
 */

public class Vacation {

    private Integer vacationID;

    private Instant start;

    private Instant end;

    private static Long MAX_DAYS;


    public Integer getVacationID() {
        return vacationID;
    }

    public void setVacationID(Integer vacationID) {
        this.vacationID = vacationID;
    }

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
