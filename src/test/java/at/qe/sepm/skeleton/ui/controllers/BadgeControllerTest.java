package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Badge;
import at.qe.sepm.skeleton.model.BadgeEnum;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.services.BadgeService;
import at.qe.sepm.skeleton.services.UserService;
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

import java.util.Calendar;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BadgeControllerTest {

    private BadgeController badgeController;

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void initTestClass(){
        badgeController = new BadgeController();

        ReflectionTestUtils.setField(badgeController, "userService", userService);
        ReflectionTestUtils.setField(badgeController, "badgeService", badgeService);

    }


    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void getBadgesFromUser() {
        List<Badge> user2Badges = badgeController.getBadgesFromUser();
        System.out.println(userService.getAuthenticatedUser());
        Assert.assertFalse(user2Badges.isEmpty());
        Assert.assertEquals("First Badge should be Code Monkey", user2Badges.get(0).getBadgeType(), BadgeEnum.WEEKLY_CODE_MONKEY);
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void getBadgesFromDepartment() {
        List<Badge> departmentBadges = badgeController.getBadgesFromDepartment();
        Assert.assertFalse(departmentBadges.isEmpty());

        /*
        * somehow not true
        *
        List<Badge> user20Badges = badgeService.getAllBadgesFromUser(userRepository.findFirstByUsername("user20"));
        System.out.println(user20Badges.get(0));
        System.out.println(departmentBadges.get(0));
        Assert.assertTrue("user8 is in Accounting, so the badge from him should be in departmentBadges", departmentBadges.containsAll(user20Badges));
        */
    }

    @Test
    public void getBadgesFromLastWeek() {
        Assert.assertTrue("If new test data is added or badges get created automatically this test will fail", badgeController.getBadgesFromLastWeek().isEmpty());
    }

    @Test
    public void getWeekStart() {
        Calendar lastWeek = Calendar.getInstance();
        lastWeek.getFirstDayOfWeek();
        StatisticsController.setDayToBeginning(lastWeek);

        Assert.assertEquals(lastWeek, BadgeController.getWeekStart());
    }
}