package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.services.RoomService;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
public class RoomDetailController implements Serializable {

    @Autowired
    private RoomService roomService;

    private Room room;


    public void setRoom(Room room) {
        this.room = room;
        doReloadRoom();
    }

    /**
     * Returns the currently displayed room.
     *
     * @return
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Action to force a reload of the currently displayed room.
     */
    public void doReloadRoom() {
        room = roomService.loadRoom(room.getRoomNumber());
    }

    /**
     * Action to save the currently displayed room.
     */
    public void doSaveRoom() {
        room = this.roomService.saveRoom(room);
    }

    /**
     * Action to delete the currently displayed room.
     */
    public void doDeleteRoom() {
        this.roomService.deleteRoom(room);
        room = null;
        MessagesView.successMessage("room deletion","successfully deleted");

    }



}
