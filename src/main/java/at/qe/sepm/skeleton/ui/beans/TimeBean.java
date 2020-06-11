package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.ui.controllers.StatisticsController;
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
        StatisticsController.setDayToBeginning(calendar);
        return calendar;
    }

    public Date instantToDate(Instant instant){
        return Date.from(instant);
    }




}
