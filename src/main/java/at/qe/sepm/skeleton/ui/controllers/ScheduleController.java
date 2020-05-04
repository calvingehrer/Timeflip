package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.VacationServiceImpl;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Component
@Scope("view")
public class ScheduleController implements Serializable {
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private String locale = "de";
    private LazyScheduleModel lazyEventModel;

    public LazyScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(LazyScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    @Autowired
    private SessionInfoBean sessionInfoBean;


    @Autowired
    private VacationServiceImpl vacationService;

    /*public ScheduleController(VacationServiceImpl vacationService) {

        eventModel = new DefaultScheduleModel();

        Collection<Vacation> vacations = vacationService.getVacationFromUser(sessionInfoBean.getCurrentUser());


        if (!vacations.isEmpty()) {

            vacations.forEach(x ->
            {

                Instant endInstant = x.getEnd();

                Date newEnd = (Date) Date.from(endInstant.plus(1, ChronoUnit.MINUTES));

                DefaultScheduleEvent vacation = new DefaultScheduleEvent("Vacation", java.util.Date.from(x.getStart()), newEnd, true);
                vacation.setData(x);
                eventModel.addEvent(vacation);
            });
        }
    }*/

    @PostConstruct
    public void init() {
        this.lazyEventModel = new LazyScheduleModel() {
            private static final long serialVersionUID = 3580478297132439482L;

            @Override
            public void loadEvents(java.util.Date start, java.util.Date end) {

                Instant startInstant = start.toInstant();
                Instant endInstant = end.toInstant();


                Collection<Vacation> vacations = vacationService.getVacationFromUser(sessionInfoBean.getCurrentUser());

                vacations.forEach(f -> {

                    java.util.Date startVacation = java.util.Date.from(f.getStart());
                    java.util.Date endVacation = Date.from(f.getEnd());

                    addEvent(new DefaultScheduleEvent("Vacation", startVacation, endVacation, f));
                });
            }
        };
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
