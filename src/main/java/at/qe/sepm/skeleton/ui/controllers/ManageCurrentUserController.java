package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.utils.MessagesView;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@Component
@Scope("view")
public class ManageCurrentUserController implements Serializable {
    private static final long serialVersionUID = -5637562154142043652L;

    @Autowired
    UserService userService;

    private User currentUser;
    private Logger<Exception, User> logger;
    private String oldPassword;
    private String newPassword;
    private String confirmNew;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * method to reload the user
     */

    private void reloadUser() {
        this.setCurrentUser(this.userService.loadUser(currentUser.getUsername()));
    }

    /**
     * method to load the current user
     * has to be called in the function that uses the bean to load it when a new user is logged in
     */
    @PostConstruct
    public void init() {
        this.setCurrentUser(userService.getAuthenticatedUser());
    }

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

    boolean checkOldPassword() {
        return passwordEncoder.matches(oldPassword, this.getCurrentUser().getPassword());
    }

    boolean checkConfirmedPassword() {
        newPassword = passwordEncoder.encode(newPassword);
        return passwordEncoder.matches(confirmNew, newPassword);
    }

    /**
     * method to change the password
     */


    public void changePassword() {
        if (checkOldPassword()) {
            if (checkConfirmedPassword()) {
                MessagesView.successMessage("passwordControl", "The new password will be saved");
                try {
                    User cu = this.getCurrentUser();
                    cu.setPassword(newPassword);
                    this.userService.updateUser(cu);
                    this.reloadUser();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.logError(e, this.getCurrentUser());
                }
            } else {
                MessagesView.warnMessage("passwordControl", "The new passwords don't match");
            }
        } else {
            MessagesView.warnMessage("passwordControl", "Old Password is incorrect");
        }
    }

    /**
     * after a users details have been changed it updates the fields and saves them
     */


    public void saveUserDetails() {
        try {
            this.userService.updateUser(this.getCurrentUser());
            this.reloadUser();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/employee/profile.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
            logger.logError(e, this.getCurrentUser());
        }
    }

}
