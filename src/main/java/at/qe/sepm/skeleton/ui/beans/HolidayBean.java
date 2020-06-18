package at.qe.sepm.skeleton.ui.beans;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * using http://jollyday.sourceforge.net/
 * to parse all public holidays of austria but can be changed
 */

@Component
@Scope("request")
public class HolidayBean {
    /**
     * @return public holidays with description
     */
    public Collection<Holiday> getPublicHolidays(int year) {
        HolidayManager m = HolidayManager.getInstance(HolidayCalendar.AUSTRIA);
        Collection<Holiday> holidays = m.getHolidays(year, "Austria");
        return holidays;
    }

    /**
     * @return only the dates of the public holidays
     */
    public Collection<Instant> getDatesOfPublicHolidays(int year) {
        Collection<Holiday> holidays = getPublicHolidays(year);
        return holidays.stream().map(holiday -> holiday.getDate().toDate().toInstant().truncatedTo(ChronoUnit.DAYS)).collect(Collectors.toSet());
    }
}
