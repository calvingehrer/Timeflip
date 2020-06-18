package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.services.RoomService;
import at.qe.sepm.skeleton.ui.controllers.RoomListController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RoomListControllerTest {

    RoomListController roomListController;

    @Autowired
    RoomService roomService;

    @Before
    public void init() {
        roomListController = new RoomListController();

        ReflectionTestUtils.setField(roomListController, "roomService", roomService);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getRooms() {
        Assert.assertEquals(5, roomListController.getRooms().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getRoomsWithoutRaspberry() {
        Assert.assertEquals(0, roomListController.getRoomsWithoutRaspberry().size());
    }
}