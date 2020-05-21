package at.qe.sepm.skeleton.ui.beans;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collection;

/**
 * using http://jollyday.sourceforge.net/
 */

@Component
@Scope("request")
public class HolidayBean {
    public Collection<Holiday> getPublicHolidays(){
        HolidayManager m = HolidayManager.getInstance(HolidayCalendar.AUSTRIA);
        Collection<Holiday> holidays = m.getHolidays(Calendar.getInstance().get(Calendar.YEAR), "Austria");
        return holidays;
    }
}
