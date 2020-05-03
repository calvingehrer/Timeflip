package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.VacationServiceImpl;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@Component
@Scope("view")
public class ScheduleController implements Serializable {
    private final ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private String locale = "de";
    @Autowired
    private SessionInfoBean sessionInfoBean;

    public ScheduleController(VacationServiceImpl vacationService) {

        eventModel = new DefaultScheduleModel();

        Collection<Vacation> vacations = vacationService.getVacationFromUser(sessionInfoBean.getCurrentUser());

        vacations.forEach(x ->
        {

            Instant endInstant = x.getEnd();
            Date newEnd = (Date) Date.from(endInstant.plus(1, ChronoUnit.MINUTES));

            DefaultScheduleEvent vacation = new DefaultScheduleEvent("Vacation", java.util.Date.from(x.getStart()), newEnd, true);
            vacation.setData(x);
            eventModel.addEvent(vacation);
        });
    }

    /**
     * Returns the current model for the calendar
     *
     * @return The calendar model
     */
    public ScheduleModel getEventModel() {
        return eventModel;
    }


    /**
     * Returns the currently selected Event
     *
     * @return The currently selected Event
     */
    public ScheduleEvent getEvent() {
        return event;
    }

    /**
     * Sets the currently selected Event
     *
     * @param event The new event
     */
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    /**
     * Triggered when the user chooses a new event
     *
     * @param selectEvent The new event
     */
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    /**
     * Gets the locale of the calendar
     *
     * @return The calendar locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the calendar locale
     *
     * @param locale The new locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

}
