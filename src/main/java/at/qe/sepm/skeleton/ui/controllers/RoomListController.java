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

import java.util.Collection;

@Component
@Scope("view")
public class RoomListController {

    private Room room;

    @Autowired
    private RoomService roomService;


    public Collection<Room> getRooms(){
        return roomService.getAllRooms();
    }

    public Collection<Room> getRoomsWithoutRaspberry() {
        return roomService.getRoomsWithoutRaspberry();
    }

    public void doDeleteRoom(){
        this.roomService.deleteRoom(room);
        room = null;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}