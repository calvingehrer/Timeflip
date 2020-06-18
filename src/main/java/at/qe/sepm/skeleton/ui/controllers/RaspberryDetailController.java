package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.services.RaspberryService;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Controller for the user detail view.
 * <p>
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

    /**
     * Returns the currently displayed user.
     */
    public Raspberry getRaspberry() {
        return raspberry;
    }

    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
        doReloadRaspberry();
    }

    /**
     * Action to force a reload of the currently displayed user.
     */
    private void doReloadRaspberry() {
        raspberry = raspberryService.loadRaspberry(raspberry.getRaspberryId());
    }

    /**
     * Action to delete the currently displayed raspberry.
     */
    public void doDeleteRaspberry() {
        this.raspberryService.deleteRaspberry(raspberry);
        raspberry = null;
        MessagesView.successMessage("raspberry deletion", "successfully deleted");

    }


}
