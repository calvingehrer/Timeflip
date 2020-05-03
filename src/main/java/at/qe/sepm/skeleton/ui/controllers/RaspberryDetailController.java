package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.RaspberryService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Controller for the user detail view.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("view")
public class RaspberryDetailController implements Serializable {

    @Autowired
    private RaspberryService raspberryService;

    private Raspberry raspberry;


    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
        doReloadRaspberry();
    }

    /**
     * Returns the currently displayed user.
     *
     * @return
     */
    public Raspberry getRaspberry() {
        return raspberry;
    }

    /**
     * Action to force a reload of the currently displayed user.
     */
    public void doReloadRaspberry() {
        raspberry = raspberryService.loadRaspberry(raspberry.getRaspberryId());
    }

    /**
     * Action to save the currently displayed user.
     */
    public void doSaveRaspberry() {
        raspberry = this.raspberryService.saveRaspberry(raspberry);
    }

    /**
     * Action to delete the currently displayed raspberry.
     */
    public void doDeleteRaspberry() {
        this.raspberryService.deleteRaspberry(raspberry);
        raspberry = null;
        successMessage("raspberry deletion","successfully deleted");

    }

    public static void successMessage(String target, String message) {
        addMessage(target, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", message));
    }

    private static void addMessage(String target, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(target, message);
    }

}
