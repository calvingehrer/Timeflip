package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.exceptions.VacationException;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.RequestService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.services.VacationService;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import at.qe.sepm.skeleton.utils.MessagesView;
import at.qe.sepm.skeleton.utils.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@Scope("view")
@Lazy
public class VacationController implements Serializable {

    private static final long serialVersionUID = 921418060697365626L;
    @Autowired
    TimeBean timeBean;
    @Autowired
    private VacationService vacationService;
    @Autowired
    private UserService userService;
    @Autowired
    private RequestService requestService;
    private Date beginVacation;
    private Date endOfVacation;
    private Set<Vacation> vacations;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
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

    private Set<Vacation> getVacations() {
        return vacations;
    }

    private void setVacations(Set<Vacation> vacations) {
        this.vacations = vacations;
    }

    @PostConstruct
    public void init() {
        this.setVacations(this.vacationService.getVacationFromUser(userService.getAuthenticatedUser()));
    }

    /**
     * adds a vacation
     * if no dates are given it shows an error,
     * if the user
     */


    public void addVacation() {
        if (getBeginVacation() == null || getEndOfVacation() == null) {
            MessagesView.errorMessage("vacation", "Please choose a date");
        }
        User currentUser = userService.getAuthenticatedUser();

        if (currentUser.getRoles().contains(UserRole.DEPARTMENTLEADER)
                || currentUser.getRoles().contains(UserRole.ADMIN)) {
            Vacation vacation = new Vacation();
            vacation.setStart(getBeginVacation().toInstant());
            vacation.setEnd(TimeConverter.addTime(getEndOfVacation().toInstant(), 1440));
            try {
                vacationService.checkVacationDates(userService.getAuthenticatedUser(), vacation.getStart(), vacation.getEnd());
                vacationService.addVacation(currentUser, vacation);
            } catch (VacationException e) {
                MessagesView.errorMessage("Vacation", e.getMessage());
                return;
            }
            MessagesView.successMessage("Vacation", "Vacation saved");
        } else {
            try {
                this.vacationService.checkVacationDates(currentUser, this.getBeginVacation().toInstant(), this.getEndOfVacation().toInstant());
            } catch (Exception e) {
                MessagesView.errorMessage("vacation", e.getMessage());
                return;
            }
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setTimeZone(timeBean.getUtcTimeZone());
            String startDate = simpleDateFormat.format(this.getBeginVacation());
            String endDate = simpleDateFormat.format(this.getEndOfVacation());
            requestService.addVacationRequest(currentUser, this.getBeginVacation(), this.getEndOfVacation(), "Requesting Vacation from " + startDate + " to " + endDate);
            MessagesView.successMessage("vacation", "Request sent");
        }

        init();
    }

    public List<Vacation> getSortedVacation() {
        Set<Vacation> sorted = getVacations();
        return new ArrayList<>(sorted);
    }
}
