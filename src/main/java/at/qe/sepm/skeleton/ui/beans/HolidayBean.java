package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.utils.TimeConverter;
import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * using http://jollyday.sourceforge.net/
 * to parse all public holidays of austria but can be changed
 */

@Component
@Scope("request")
public class HolidayBean {
    /**
     *
     * @return public holidays with description
     */
    public Collection<Holiday> getPublicHolidays(){
        HolidayManager m = HolidayManager.getInstance(HolidayCalendar.AUSTRIA);
        Collection<Holiday> holidays = m.getHolidays(TimeConverter.getYear(new Date().toInstant()), "Austria");
        return holidays;
    }

    /**
     *
     * @return only the dates of the public holidays
     */
    public Collection<Instant> getDatesOfPublicHolidays() {
        Collection<Holiday> holidays = getPublicHolidays();
        return holidays.stream().map(holiday -> holiday.getDate().toDate().toInstant().truncatedTo(ChronoUnit.DAYS)).collect(Collectors.toSet());
    }
}
