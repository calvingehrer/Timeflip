package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.exceptions.VacationException;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.services.VacationService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import at.qe.sepm.skeleton.utils.MessagesView;
import at.qe.sepm.skeleton.utils.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Controller
@Scope("view")
@Lazy
public class VacationController implements Serializable {

    private static final long serialVersionUID = 921418060697365626L;


    @Autowired
    private VacationService vacationService;

    @Autowired
    private UserService userService;
    @Autowired
    private SessionInfoBean sessionInfoBean;

    private User thisUser;
    private Date beginVacation;
    private Date endOfVacation;
    private Set<Vacation> vacations;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public VacationService getVacationService() {
        return vacationService;
    }

    public void setVacationService(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }

    public Date getBeginVacation() {
        return beginVacation;
    }

    public void setBeginVacation(Date beginVacation) {
        this.beginVacation = beginVacation;
    }

    public Date getEndOfVacation() {
        return endOfVacation;
    }

    public void setEndOfVacation(Date endOfVacation) {
        this.endOfVacation = endOfVacation;
    }

    public Set<Vacation> getVacations() {
        return vacations;
    }

    public void setVacations(Set<Vacation> vacations) {
        this.vacations = vacations;
    }

    @PostConstruct
    public void init(){
        this.setThisUser(userService.getAuthenticatedUser());
        this.setVacations(this.vacationService.getVacationFromUser(thisUser));
    }


    public void addVacation(){


        if(getBeginVacation() == null || getEndOfVacation() == null){
            MessagesView.errorMessage("vacation", "Please choose a date");
        }

        Vacation vacation = new Vacation();
        vacation.setStart(getBeginVacation().toInstant());
        vacation.setEnd(TimeConverter.addTime(getEndOfVacation().toInstant(), 1440));
        try {
            this.vacationService.addVacation(this.getThisUser(), vacation);
        }
        catch (VacationException e){
            MessagesView.errorMessage("vacation", e.getMessage());
            return;
        }
        MessagesView.successMessage("vacation", "Vacation saved");

        init();
    }
}
