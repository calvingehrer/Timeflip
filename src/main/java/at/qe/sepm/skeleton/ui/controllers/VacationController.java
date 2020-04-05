package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.exceptions.VacationException;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.services.VacationService;
import at.qe.sepm.skeleton.utils.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

@Controller
@Scope("view")
@Lazy
public class VacationController implements Serializable {

    private static final long serialVersionUID = 921418060697365626L;


    @Autowired
    private VacationService vacationService;

    @Autowired
    private UserService userUserService;

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

    public UserService getUserUserService() {
        return userUserService;
    }

    public void setUserUserService(UserService userUserService) {
        this.userUserService = userUserService;
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
        this.setThisUser(userUserService.getAuthenticatedUser());
        this.setVacations(this.vacationService.getVAcationsFromUser(thisUser));
    }

    public TimeZone utcTimeZone(){
        return TimeZone.getTimeZone(ZoneId.of("UTC"));
    }

    public void addVacation(){
        FacesMessage message;

        if(getBeginVacation() == null || getEndOfVacation() == null){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please choose a date", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        Vacation vacation = new Vacation();
        vacation.setStart(getBeginVacation().toInstant());
        vacation.setEnd(TimeConverter.addTime(getEndOfVacation().toInstant(), 1440));
        try {
            this.vacationService.addVacation(this.getThisUser(), vacation);
        }
        catch (VacationException e){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vacation saved", null);
        FacesContext.getCurrentInstance().addMessage(null, message);

        init();
    }
}
