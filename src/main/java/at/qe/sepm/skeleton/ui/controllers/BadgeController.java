package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Badge;
import at.qe.sepm.skeleton.model.BadgeEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.BadgeService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Scope("view")
public class BadgeController implements Serializable {

    @Autowired
    private UserService userService;

    @Autowired
    BadgeService badgeService;

    private User currentUser;

    @PostConstruct
    public void init() {
        this.setCurrentUser(userService.getAuthenticatedUser());
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Badge> getBadgesFromUser(){
        return badgeService.getAllBadgesFromUser(getCurrentUser());
    }
}
