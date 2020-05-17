package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.RaspberryService;
import at.qe.sepm.skeleton.services.RoomService;
import at.qe.sepm.skeleton.services.TimeflipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope("view")
public class AddRoomController implements Serializable {

    @Autowired
    private RoomService roomService;

    private Room room = new Room();

    public void add(){
        roomService.addNewRoom(room);
        resetRoom();
    }


    public void resetRoom(){
        this.room = new Room();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
