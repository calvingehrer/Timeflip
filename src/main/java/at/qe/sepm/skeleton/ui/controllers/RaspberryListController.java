package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.services.RaspberryService;
import at.qe.sepm.skeleton.services.TimeflipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("view")
public class RaspberryListController {

    private Raspberry raspberry;

    @Autowired
    private RaspberryService raspberryService;

    public Collection<Raspberry> getRaspberries(){
        return raspberryService.getAllRaspberries();
    }

    public Raspberry getRaspberry() {
        return raspberry;
    }

    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
    }

}
