package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.controllers.ManageCurrentUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class CurrentUserBean {

    @Autowired
    UserService userService;

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * method to reload the user
     */

    public void reloadUser() {
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

}
