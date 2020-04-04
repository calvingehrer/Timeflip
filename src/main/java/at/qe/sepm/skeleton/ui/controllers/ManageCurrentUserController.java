package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Intervall;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("view")
public class ManageCurrentUserController {
    private static final long serialVersionUID = -5637562154142043652L;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    @Autowired
    private UserService userService;

    private User me;

    private String intervall;

    public String getIntervall() { return intervall; }

    public void setIntervall(String intervall) { this.intervall = intervall; }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public void reloadUser() {
        this.setMe(this.userService.loadUser(me.getUsername()));
    }

    @PostConstruct
    public void init() {
        this.setMe(userService.getAuthenticatedUser());
    }

    public String getFullName() {
        return me.getFirstName() + " " + me.getLastName();
    }

    /**
     * Saves changed information
     */
    public void saveUserDetails() {
        try {
            this.getMe().setIntervall(Intervall.convertStringToIntervall(this.intervall));
            this.userService.updateUser(me);
            reloadUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
