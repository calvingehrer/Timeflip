package at.qe.sepm.skeleton.tests;


import at.qe.sepm.skeleton.exceptions.VacationException;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.services.VacationService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.beans.HolidayBean;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Instant;
import java.util.Calendar;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class VacationServiceTest {

    @Autowired
    VacationService vacationService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HolidayBean holidayBean;

    @MockBean
    CurrentUserBean currentUserBean;

    @Autowired
    TimeBean timeBean;

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void initTest() {
        vacationService.init();
        Assert.assertNotNull(currentUserBean);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void addVacationCheckValidationTest() {
        User user = userRepository.findFirstByUsername("user5");

        Vacation vacation = new Vacation();

        Calendar calendar = Calendar.getInstance(timeBean.getUtcTimeZone());
        calendar.set(2020, Calendar.AUGUST, 10);
        Instant start = calendar.toInstant();
        calendar.set(2020, Calendar.AUGUST, 7);
        Instant end = calendar.toInstant();
        vacation.setStart(start);
        vacation.setEnd(end);

        Assertions.assertThrows(VacationException.class, () -> vacationService.addVacation(user, vacation));

        calendar.set(2021, Calendar.AUGUST, 14);
        end = calendar.toInstant();
        vacation.setEnd(end);

        Assertions.assertThrows(VacationException.class, () -> vacationService.addVacation(user, vacation));

        calendar.set(2020, Calendar.AUGUST, 14);
        end = calendar.toInstant();
        vacation.setEnd(end);

        try {
            vacationService.addVacation(user, vacation);
        } catch (Exception e) {
            MessagesView.errorMessage("Test adding vacation", e.getMessage());
        }

    }
}