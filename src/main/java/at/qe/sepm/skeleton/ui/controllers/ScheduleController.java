package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.services.VacationService;
import at.qe.sepm.skeleton.ui.beans.HolidayBean;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import de.jollyday.Holiday;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Component
@Scope("view")
public class ScheduleController implements Serializable {
    private LazyScheduleModel lazyEventModel;

    public LazyScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(LazyScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    @Autowired
    private TimeBean timeBean;
    @Autowired
    private HolidayBean holidayBean;

    @Autowired
    private VacationService vacationService;

    @Autowired
    private UserService userService;


    /**
     * method to initialize the calendar with vacation and public holidays
     * the plus one for the holidays is because the default scheduler
     * sets them one day earlier
     */

    @PostConstruct
    public void init() {
        this.lazyEventModel = new LazyScheduleModel() {
            private static final long serialVersionUID = 3580478297132439482L;

            @Override
            public void loadEvents(java.util.Date start, java.util.Date end) {

                Instant startInstant = start.toInstant();
                Instant endInstant = end.toInstant();


                Collection<Vacation> vacations = vacationService.getVacationFromUser(userService.getAuthenticatedUser());


                vacations.forEach(f -> {

                    java.util.Date startVacation = java.util.Date.from(f.getStart());
                    java.util.Date endVacation = Date.from(f.getEnd());

                    addEvent(new DefaultScheduleEvent("Vacation", startVacation, endVacation, f));
                });

                Collection<Holiday> holidays = holidayBean.getPublicHolidays(timeBean.getYearOfInstant(startInstant));
                if (!timeBean.getYearOfInstant(startInstant).equals(timeBean.getYearOfInstant(endInstant))) {
                    holidays.addAll(holidayBean.getPublicHolidays(timeBean.getYearOfInstant(endInstant)));
                }
                holidays.forEach(h -> {
                    Calendar calendar = Calendar.getInstance(timeBean.getUtcTimeZone());
                    calendar.setTime(h.getDate().toDate());
                    calendar.add(Calendar.DATE, 1);
                    Date holiday = calendar.getTime();
                    addEvent(new DefaultScheduleEvent(h.getDescription(), holiday, holiday));
                });

            }
        };
    }


}
