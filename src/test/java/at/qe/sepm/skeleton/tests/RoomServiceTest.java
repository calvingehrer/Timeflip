package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.services.RoomService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class RoomServiceTest {

    @Autowired
    RoomService roomService;


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getAllRooms() {
        Assert.assertEquals(6, roomService.getAllRooms().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getRoomsWithoutRaspberry() {
        Room room = new Room();
        room.setRoomNumber("22");

        Assert.assertEquals(1, roomService.getRoomsWithoutRaspberry().size());

        roomService.addNewRoom(room);

        Assert.assertEquals(2, roomService.getRoomsWithoutRaspberry().size());

        roomService.deleteRoom(roomService.loadRoom("22"));

        Assert.assertEquals(1, roomService.getRoomsWithoutRaspberry().size());


    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void addNewRoom() {

        Assert.assertEquals(6, roomService.getAllRooms().size());

        Room room = new Room();
        room.setRoomNumber("22");

        roomService.addNewRoom(room);

        Assert.assertEquals(7, roomService.getAllRooms().size());

        roomService.deleteRoom(roomService.loadRoom("22"));

        Assert.assertEquals(6, roomService.getAllRooms().size());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void loadRoom() {
        Assert.assertEquals("3", roomService.loadRoom("3").getRaspberry().getRaspberryId());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteRoom() {
        Room room = roomService.loadRoom("4");

        Assert.assertEquals(6, roomService.getAllRooms().size());

        roomService.deleteRoom(roomService.loadRoom("4"));

        Assert.assertEquals(5, roomService.getAllRooms().size());

        roomService.addNewRoom(room);

        Assert.assertEquals(6, roomService.getAllRooms().size());




    }
}