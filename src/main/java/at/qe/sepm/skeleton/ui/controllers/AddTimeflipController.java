package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TimeflipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope("view")
public class AddTimeflipController {


    @Autowired
    private TimeflipService timeflipServicee;

    private Timeflip timeflip = new Timeflip();

    private User user;


    public void add(){
        timeflipServicee.addNewTimeflip(timeflip, user);
    }




    public TimeflipService getTimeflipServicee() {
        return timeflipServicee;
    }

    public void setTimeflipServicee(TimeflipService timeflipServicee) {
        this.timeflipServicee = timeflipServicee;
    }

    public Timeflip getTimeflip() {
        return timeflip;
    }

    public void setTimeflip(Timeflip timeflip) {
        this.timeflip = timeflip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}