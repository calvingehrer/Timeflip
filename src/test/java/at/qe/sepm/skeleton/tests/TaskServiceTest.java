package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.services.Logger;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Instant;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functions of the TaskService class
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    void init() {
    }

    @Test
    void getAllTasksBetweenDatesTest() {

        Calendar startDate = Calendar.getInstance();

        Calendar endDate = Calendar.getInstance();

        startDate.set(Calendar.MONTH, Calendar.FEBRUARY);
        startDate.set(Calendar.DAY_OF_MONTH, 1);

        endDate.set(Calendar.MONTH, Calendar.JANUARY);
        endDate.set(Calendar.DAY_OF_MONTH, 31);

        Instant start = startDate.toInstant();
        Instant end =endDate.toInstant();


    }

    @Test
    void getDuration() {
    }

    @Test
    void getUserTasksBetweenDates() {
    }

    @Test
    void getTeamTasksBetweenDates() {
    }

    @Test
    void getDepartmentTasksBetweenDates() {
    }

    @Test
    void saveEditedTask() {
    }

    @Test
    void checkIfEarlierThanTwoWeeks() {
    }

    @Test
    void checkIfAfterToday() {
    }

    @Test
    void checkTime() {
    }

    @Test
    void deleteTask() {
    }
}