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

@Component
@Scope("view")
public class AddRaspberryController implements Serializable {

    @Autowired
    private RaspberryService raspberryService;

    @Autowired
    private RoomDetailController roomDetailController;

    private Raspberry raspberry = new Raspberry();

    private Room room;


    public void add(){
        if(room != null){
            roomDetailController.setRoom(room);
        }
        raspberryService.addNewRaspberry(raspberry, room);
        resetRaspberry();
    }

    public void resetRaspberry(){
        this.raspberry = new Raspberry();
    }


    public Raspberry getRaspberry() {
        return raspberry;
    }

    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
