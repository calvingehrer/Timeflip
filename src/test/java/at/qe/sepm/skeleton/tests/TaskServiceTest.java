package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.services.Logger;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.security.Principal;
import java.time.Instant;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * This class tests the functions of the TaskService class
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class TaskServiceTest{


    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @MockBean
    CurrentUserBean currentUserBean;


    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getAllTasksBetweenDatesTest() {


        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(Calendar.MONTH, Calendar.FEBRUARY);
        startDate.set(Calendar.DAY_OF_MONTH, 1);

        endDate.set(Calendar.MONTH, Calendar.JANUARY);
        endDate.set(Calendar.DAY_OF_MONTH, 31);

        Instant start = startDate.toInstant();
        Instant end = endDate.toInstant();


        User user = currentUserBean.getCurrentUser();
        Assert.assertEquals(taskService.getAllTasksBetweenDates(user, start, end), taskRepository.findUserTasksBetweenDates(user, start, end));

    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getDuration() {
        Task task = new Task();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(2020, 5, 9, 17, 30);
        endDate.set(2020, 5, 9, 18, 50);

        task.setStartTime(startDate.toInstant());
        task.setEndTime(endDate.toInstant());


        Assert.assertEquals(80, taskService.getDuration(task));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getUserTasksBetweenDates() {

    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getTeamTasksBetweenDates() {
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getDepartmentTasksBetweenDates() {
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void saveEditedTask() {
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void checkIfEarlierThanTwoWeeks() {
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void checkIfAfterToday() {
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void checkTime() {
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void deleteTask() {
    }
}