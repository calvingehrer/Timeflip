package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TimeflipService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;



@Component
@Scope("view")
public class TimeflipListController implements Serializable {

    private Timeflip timeflip;

    @Autowired
    private TimeflipService timeflipService;

    private String macAddress = "";

    @Autowired
    private SessionInfoBean sessionInfoBean;

    @Autowired
    UserService userService;


    public Collection<Timeflip> getTimeflipOfUser() {
        User currentUser = sessionInfoBean.getCurrentUser();

       // System.out.println(currentUser.getUsername());

        return timeflipService.getTimeflipOfUser(currentUser);
    }




    public Collection<Timeflip> getTimeflips(){
        if(!macAddress.equals("")){
            return timeflipService.getAllTimeflipsByMacAddress(macAddress);
        }
        return timeflipService.getAllTimeflips();
    }

    public Timeflip getTimeflip() {
        return timeflip;
    }

    public void setTimeflip(Timeflip timeflip) {
        this.timeflip = timeflip;
    }

    public TimeflipService getTimeflipService() {
        return timeflipService;
    }

    public void setTimeflipService(TimeflipService timeflipService) {
        this.timeflipService = timeflipService;
    }

    public String getMacId() {
        return macAddress;
    }

    public void setMacId(String macId) {
        this.macAddress = macId;
    }
}
