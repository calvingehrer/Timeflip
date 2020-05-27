package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.exceptions.VacationException;
import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.RoomService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.services.VacationService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Instant;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.coyote.http11.Constants.Z;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class VacationServiceTest {

    @Autowired
    VacationService vacationService;

    @MockBean
    CurrentUserBean currentUserBean;

    @Autowired
    UserService userService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void addVacation() throws VacationException {
        /*
        User user = userService.getAllUsersByUsername("user1").get(0);
        currentUserBean.setCurrentUser(user);

        Calendar startDate = Calendar.getInstance();
        startDate.set(2021, Calendar.NOVEMBER, 17, 0, 0, 0);
        Instant instantStart = startDate.toInstant();

        Calendar endDate = Calendar.getInstance();
        endDate.set(2021, Calendar.NOVEMBER, 22, 0, 0, 0);
        Instant instantEnd = endDate.toInstant();

        Vacation vacation = new Vacation();

        vacation.setStart(instantStart);
        vacation.setEnd(instantEnd);
        //user.setVacationDays(40);


        //Assert.assertNull(vacationService.getVacationFromUser(user));
        vacationService.addVacation(user, vacation);

        Assert.assertNotNull(vacationService.getVacationFromUser(user));
        //Set<Vacation> vacation1 = new HashSet<>(vacationService.getVacationFromUser(user));



        //Assert.assertEquals("2020-06-17T00:00:00Z", vacation1.);
        //vacationService.addVacation(user, );
*/

    }

    @Test
    void getVacationFromUser() {
    }

    @Test
    void checkVacationDates() {
    }
}