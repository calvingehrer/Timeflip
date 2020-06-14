package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Component
@Scope("view")
public class AddUserController implements Serializable {

    @Autowired
    private UserService userService;

    private Set<UserRole> roles;
    private boolean admin = false;
    private boolean departmentleader = false;
    private boolean teamleader = false;
    private boolean employee = false;

    /**
     * The new user to be added to the system
     */
    private User user = new User();

    /**
     * Action to add the user to the system
     */
    public void add(){

        if(user.getUsername() == null){
            return;
        }

        this.roles = new HashSet<>();
        if(admin){
            roles.add(UserRole.ADMIN);
        }
        if(departmentleader){
            roles.add(UserRole.DEPARTMENTLEADER);
        }
        if(teamleader){
            roles.add(UserRole.TEAMLEADER);
        }
        if(employee){
            roles.add(UserRole.EMPLOYEE);
        }

        user.setRoles(roles);



        userService.addNewUser(user);

        resetUser();
    }



    public void resetUser(){
        this.user = new User();
        if (!roles.isEmpty()) {
            roles.clear();
        }
        employee = false;
        teamleader = false;
        departmentleader = false;
        admin = false;
        user.setEnabled(false);
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isDepartmentleader() { return departmentleader; }

    public void setDepartmentleader(boolean departmentleader) { this.departmentleader = departmentleader; }

    public boolean isTeamleader() {
        return teamleader;
    }

    public void setTeamleader(boolean teamleader) {
        this.teamleader = teamleader;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
