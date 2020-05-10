package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("view")
public class ManageCurrentUserController implements Serializable {
    private static final long serialVersionUID = -5637562154142043652L;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    @Autowired
    private UserService userService;

    private User currentUser;

    private String intervall;

    private String oldPassword;

    private String newPassword;

    private String confirmNew;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNew() {
        return confirmNew;
    }

    public void setConfirmNew(String confirmNew) {
        this.confirmNew = confirmNew;
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    public String getIntervall() { return intervall; }

    public void setIntervall(String intervall) { this.intervall = intervall; }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void reloadUser() {
        this.setCurrentUser(this.userService.loadUser(currentUser.getUsername()));
    }


    @PostConstruct
    public void init() {
        this.setCurrentUser(userService.getAuthenticatedUser());
    }

    public String getFullName() {
        return currentUser.getFirstName() + " " + currentUser.getLastName();
    }

    public List<String> completeIntervall(String query) {
        String upperQuery = query.toUpperCase();
        return Interval.getAllIntervals().stream().filter(a -> a.contains(upperQuery)).collect(Collectors.toList());
    }

    public boolean checkOldPassword(){
        return passwordEncoder.matches(oldPassword, currentUser.getPassword());
    }

    public boolean checkConfirmedPassword(){
        newPassword = passwordEncoder.encode(newPassword);
        return passwordEncoder.matches(confirmNew, newPassword);
    }


    public void changePassword(){
        if(checkOldPassword()) {
            if (checkConfirmedPassword()){
                    MessagesView.successMessage("passwordControl", "The new password will be saved");
                try {
                    this.currentUser.setPassword(newPassword);
                    this.userService.updateUser(currentUser);
                    reloadUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                MessagesView.warnMessage("passwordControl", "The new passwords don't match");
            }
        }
        else{
            MessagesView.warnMessage("passwordControl", "Old Password is incorrect");
        }
    }


    public void saveUserDetails() {
        try {
            this.userService.updateUser(currentUser);
            reloadUser();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/employee/profile.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
