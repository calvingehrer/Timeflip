package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.event.NamedEvent;
import java.io.Serializable;

@Controller
@Scope("view")
@NamedEvent
public class DashboardController implements Serializable {

    private static final long serialVersionUID = 2723138783409404659L;
    @Autowired
    private SessionInfoBean sessionInfoBean;

    public String getName() {
        return sessionInfoBean.getCurrentUser().getFirstName();
    }

    public String getDepartment() {
        return String.valueOf(sessionInfoBean.getCurrentUser().getDepartment());
    }

}
