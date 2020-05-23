package at.qe.sepm.skeleton.ui.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
@Scope("session")
public class TimeBean {
    /**
     * method to get the time zone
     * @return
     */
    public TimeZone getUtcTimeZone() {
        return TimeZone.getTimeZone(ZoneId.of("UTC"));
    }

    public Calendar setNull (Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public Calendar getStartOfDay(Calendar calendar) {
        return setNull(calendar);
    }

    public Calendar getEndOfDay(Calendar calendar) {
        calendar.add(Calendar.DATE, 1);
        return setNull(calendar);
    }

    public Calendar getStartOfWeek(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return setNull(calendar);
    }

    public Calendar getEndOfWeek(Calendar calendar) {
        calendar.add(Calendar.DATE, 7);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return setNull(calendar);
    }

    public Calendar getStartOfMonth(Calendar calendar) {
        calendar.set(Calendar.DATE, 1);
        return setNull(calendar);
    }

    public Calendar getEndOfMonth(Calendar calendar) {
        calendar.set(Calendar.DATE, 1);
        return setNull(calendar);
    }

}
