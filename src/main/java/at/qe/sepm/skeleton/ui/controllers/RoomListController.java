package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

@Component
@Scope("view")
public class RoomListController implements Serializable {

    private Room room;

    @Autowired
    private RoomService roomService;


    public Collection<Room> getRooms() {
        return roomService.getAllRooms();
    }

    public Collection<Room> getRoomsWithoutRaspberry() {
        return roomService.getRoomsWithoutRaspberry();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
