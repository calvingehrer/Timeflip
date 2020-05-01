package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Controller
@Scope("view")
public class DashboardController implements Serializable {

    private static final long serialVersionUID = 2723138783409404659L;
    private final ScheduleEvent event = new DefaultScheduleEvent();
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
                    Date vacationEnd = Date.from(f.getEnd());

                    addEvent(new DefaultScheduleEvent(f.toString(), vacatoionStart, vacationEnd));
                });
            }
        };
    }


}
