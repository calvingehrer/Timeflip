package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("view")
public class ManageCurrentUserController {
    private static final long serialVersionUID = -5637562154142043652L;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    @Autowired
    private UserService userService;

    private User currentUser;

    private String intervall;

    private String oldPassword;

    private String newPassword;


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


    public void changePassword(){
        if(checkOldPassword()){
            // System.out.println("SUCCESS: Old Password is correct");
            try {
                this.currentUser.setPassword(passwordEncoder.encode(newPassword));
                reloadUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            // System.out.println("Error: Old Password is not correct");
        }
    }


    public void saveUserDetails() {
        try {
            this.userService.updateUser(currentUser);
            reloadUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
