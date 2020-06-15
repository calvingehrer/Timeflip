package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.controllers.AddRaspberryController;
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
public class AddRaspberryControllerTest {

    private AddRaspberryController addRaspberryController;

    @Autowired
    private RaspberryService raspberryService;

    @Autowired
    private RoomService roomService;

    @Before
    public void init() throws IOException {
        addRaspberryController = new AddRaspberryController();
        ReflectionTestUtils.setField(addRaspberryController, "raspberryService", raspberryService);
        ReflectionTestUtils.setField(addRaspberryController, "roomService", roomService);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void add() {
        Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId("testRaspberry");
        addRaspberryController.setRaspberry(raspberry);
        Room room = roomService.loadRoom("6");
        addRaspberryController.setRoom(room);
        Assert.assertEquals(5, raspberryService.getAllRaspberries().size());
        Assert.assertFalse(room.isEquipped());
        addRaspberryController.add();
        Assert.assertEquals(6, raspberryService.getAllRaspberries().size());
        Assert.assertTrue(roomService.loadRoom("6").isEquipped());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void resetRaspberry() {
        Raspberry raspberry = raspberryService.loadRaspberry("3");
        addRaspberryController.setRaspberry(raspberry);
        Assert.assertEquals("3", addRaspberryController.getRaspberry().getRaspberryId());
        addRaspberryController.resetRaspberry();
        Assert.assertNull(addRaspberryController.getRaspberry().getRaspberryId());
    }
}