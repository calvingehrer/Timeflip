package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private ScheduleModel model;
    private ScheduleModel lazyEventModel;


    public void ScheduleBean() {
        model = new DefaultScheduleModel();
    }

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
        return model;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.model = eventModel;
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
        if (event.getId() == null)
            model.addEvent(event);
        else
            model.updateEvent(event);
        event = new DefaultScheduleEvent(); //reset dialog form
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();

    }

    //public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
    //  event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject()).endDate(selectEvent.getObject().plusHours(1)).build();
    //}


    public void onDateSelect(SelectEvent selectEvent) {
        //event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject()).endDate(selectEvent.getObject().plusHours(1)).build();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Delta");

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Start-Delta:, End-Delta: ");

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


}
