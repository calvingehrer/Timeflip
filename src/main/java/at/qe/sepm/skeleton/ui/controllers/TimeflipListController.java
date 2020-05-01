package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.services.TimeflipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;



@Component
@Scope("view")
public class TimeflipListController {

    private Timeflip timeflip;

    @Autowired
    private TimeflipService timeflipService;

    private String macAddress = "";




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
