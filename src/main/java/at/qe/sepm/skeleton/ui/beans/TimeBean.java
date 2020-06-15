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
     *
     * @return current time zone
     */
    public TimeZone getUtcTimeZone() {
        return TimeZone.getTimeZone(ZoneId.of("UTC"));
    }

    /**
     * @param instant to convert
     * @return date
     */

    public Date instantToDate(Instant instant) {
        return Date.from(instant);
    }

    /**
     * @param instant to get year from
     * @return year
     */

    public Integer getYearOfInstant(Instant instant) {
        Date date = instantToDate(instant);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }


}
