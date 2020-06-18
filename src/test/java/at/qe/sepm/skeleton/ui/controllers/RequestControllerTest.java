package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.RequestRepository;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.controllers.AddRaspberryController;
import at.qe.sepm.skeleton.ui.controllers.AddUserController;
import at.qe.sepm.skeleton.ui.controllers.RequestController;
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
import java.util.Calendar;
import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RequestControllerTest {

    RequestController requestController;

    @Autowired
    RequestService requestService;
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserService userService;

    @Before
    public void init() throws IOException {
        requestController = new RequestController();
        ReflectionTestUtils.setField(requestController, "requestService", requestService);
        ReflectionTestUtils.setField(requestController, "userService", userService);
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"ADMIN", "DEPARTMENTLEADER"})
    public void getOpenRequestsLeader() {
        Assert.assertEquals(1, requestController.getOpenRequestsLeader().size());
    }

    @Test
    @WithMockUser(username = "user19", authorities = {"EMPLOYEE"})
    public void getOpenRequestsEmployee() {
        Assert.assertEquals(1, requestController.getOpenRequestsEmployee().size());
    }

    @Test
    @WithMockUser(username = "user19", authorities = {"EMPLOYEE"})
    public void getAcceptedRequestsEmployee() {
        Assert.assertEquals(1, requestController.getAcceptedRequestsEmployee().size());
    }

    @Test
    @WithMockUser(username = "user19", authorities = {"EMPLOYEE"})
    public void getDeclinedRequestsEmployee() {
        Assert.assertEquals(1, requestController.getDeclinedRequestsEmployee().size());
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER", "ADMIN"})
    public void declineAndDeleteRequest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 9, 15);
        Date start = calendar.getTime();
        calendar.set(2020, 9, 20);
        Date end = calendar.getTime();

        Request request1 = requestService.addVacationRequest(userService.loadUser("user19"), start, end, "requesting vacation");
        Request request2 = requestService.addTaskRequest(userService.loadUser("user19"), start.toInstant(), end.toInstant(), TaskEnum.KUNDENBESPRECHUNG, "changing tasks");
        Assert.assertEquals(3,requestService.getOpenRequestsOfEmployee(userService.loadUser("user19")).size());
        requestController.declineRequest(request1);
        Assert.assertEquals(2,requestService.getDeclinedRequestsOfEmployee(userService.loadUser("user19")).size());
        requestController.deleteRequest(request1);
        requestController.deleteRequest(request2);
        Assert.assertEquals(1,requestService.getOpenRequestsOfEmployee(userService.loadUser("user19")).size());
    }

}