package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("view")
public class ManageCurrentUserController implements Serializable {
    private static final long serialVersionUID = -5637562154142043652L;

    @Autowired
    CurrentUserBean currentUserBean;

    @Autowired
    private UserService userService;

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

    @PostConstruct
    public void init() {
        currentUserBean.init();
    }

    public String getFullName() {
        return currentUserBean.getCurrentUser().getFirstName() + " " + currentUserBean.getCurrentUser().getLastName();
    }

    public boolean checkOldPassword(){
        return passwordEncoder.matches(oldPassword, currentUserBean.getCurrentUser().getPassword());
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
                    User cu = currentUserBean.getCurrentUser();
                    cu.setPassword(newPassword);
                    this.userService.updateUser(cu);
                    currentUserBean.reloadUser();
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
            this.userService.updateUser(currentUserBean.getCurrentUser());
            currentUserBean.reloadUser();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/employee/profile.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
