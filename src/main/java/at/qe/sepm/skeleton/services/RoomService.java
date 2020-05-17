package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.RoomRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("application")
public class RoomService {


    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RaspberryService raspberryService;


    @Autowired
    private Logger<String, User> logger;


    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Room> getRoomsWithoutRaspberry() {

        List<Room> listRooms = new ArrayList<>(roomRepository.findRoomsWithoutRaspberry());
        for(Raspberry raspberry : raspberryService.getAllRaspberries()){
            listRooms.remove(raspberry.getRoom());
        }

        return listRooms;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewRoom(Room room) {
        Room newRoom = new Room();
        newRoom.setRoomNumber(room.getRoomNumber());
        saveRoom(newRoom);
        System.out.println(newRoom.getRoomNumber());
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Room loadRoom(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Room saveRoom(Room room) {
        /*if (room.isNew()) {
            room.setCreateDate(new Date());
            room.setCreateUser(getAuthenticatedUser());
        }*/
        return roomRepository.save(room);
    }

    /**
     * Deletes the room.
     *
     * @param room the room to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }


}
