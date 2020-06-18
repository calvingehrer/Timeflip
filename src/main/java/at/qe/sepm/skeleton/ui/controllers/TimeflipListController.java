package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.services.TimeflipService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;


@Component
@Scope("view")
public class TimeflipListController implements Serializable {

    @Autowired
    UserService userService;
    @Autowired
    ManageCurrentUserController manageCurrentUserController;
    private Timeflip timeflip;
    @Autowired
    private TimeflipService timeflipService;
    private String macAddress = "";
    private String userName = "";

    public Timeflip getTimeflipOfUser() {
        return timeflipService.getTimeflipOfUser(userService.getAuthenticatedUser());
    }


    public Collection<Timeflip> getTimeflips() {
        if (!userName.equals("")) {
            return timeflipService.getTimeflipsByUserPrefix(userName);
        }
        if (!macAddress.equals("")) {
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

    public String getMacId() {
        return macAddress;
    }

    public void setMacId(String macId) {
        this.macAddress = macId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
