package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.controllers.AddRaspberryController;
import at.qe.sepm.skeleton.ui.controllers.AddRoomController;
import at.qe.sepm.skeleton.ui.controllers.AddUserController;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AddRoomControllerTest {

    private AddRoomController addRoomController;


    @Autowired
    private RoomService roomService;

    @Before
    public void init() throws IOException {
        addRoomController = new AddRoomController();
        ReflectionTestUtils.setField(addRoomController, "roomService", roomService);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void add() {
        Room room = new Room();
        room.setRoomNumber("7");
        addRoomController.setRoom(room);
        Assert.assertEquals(6, roomService.getAllRooms().size());
        addRoomController.add();
        Assert.assertEquals(7, roomService.getAllRooms().size());
    }

    @Test
    public void resetRoom() {
        Room room = new Room();
        room.setRoomNumber("7");
        addRoomController.setRoom(room);
        Assert.assertEquals("7", addRoomController.getRoom().getRoomNumber());
        addRoomController.resetRoom();
        Assert.assertNull(addRoomController.getRoom().getRoomNumber());
    }
}