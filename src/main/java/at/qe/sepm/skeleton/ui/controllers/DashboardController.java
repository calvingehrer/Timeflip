package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.event.NamedEvent;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

@Controller
@Scope("view")
@NamedEvent
public class DashboardController implements Serializable {

    private static final long serialVersionUID = 2723138783409404659L;
    private ScheduleEvent event = new DefaultScheduleEvent();
    @Autowired
    private UserService userService;
    @Autowired
    private VacationService vacationService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private MailService mailService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SessionInfoBean sessionInfoBean;
    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    @PostConstruct
    public void init() {
        this.lazyEventModel = new LazyScheduleModel() {
            private static final long serialVersionUID = 3580478297132439482L;

            @Override
            public void loadEvents(Date start, Date end) {

                Instant startInstant = start.toInstant();
                Instant endInstant = end.toInstant();

                Collection<Vacation> vacations = vacationService.getVacationFromUser(sessionInfoBean.getCurrentUser());


                vacations.forEach(f -> {

                    Date vacatoionStart = Date.from(f.getStart());
                    Date vacationEnd = Date.from(f.getEnd().plus(1, ChronoUnit.MINUTES));

                    addEvent(new DefaultScheduleEvent("Vacation", vacatoionStart, vacationEnd));
                });
            }
        };
    }

    public void addEvent() {
        if (event.isAllDay()) {
            //see https://github.com/primefaces/primefaces/issues/1164
            if (event.getStartDate().toInstant().equals(event.getEndDate().toInstant())) {
                //event.setEndDate(event.getEndDate().plusDays(1));
            }
        }


        if (event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);

        event = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

}
