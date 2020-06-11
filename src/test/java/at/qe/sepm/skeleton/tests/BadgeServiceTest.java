package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.BadgeRepository;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.services.BadgeService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Date;
import java.time.Instant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BadgeServiceTest {

    @Autowired
    BadgeService badgeService;

    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @MockBean
    CurrentUserBean currentUserBean;

    @Autowired
    TimeBean timeBean;

    @Test
    public void deleteBadgeTest() {
        User user = userRepository.findFirstByUsername("user4");
        List<Badge> userBadges = badgeRepository.findBadgesFromUser(user);

        Assert.assertEquals("There should be 1 Badge from user4", 1, userBadges.size());

        Badge badge = userBadges.get(0);

        badgeService.deleteBadge(badge);

        List<Badge> userNewBadges = badgeRepository.findBadgesFromUser(user);

        Assert.assertTrue("after deleting the badge there should be no badge for user4", userNewBadges.isEmpty());
    }

    @Test
    public void evaluateWeeklyBadgesTest() {
        List<Task> taskList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance(timeBean.getUtcTimeZone());
        calendar.set(2021, Calendar.MARCH, 20, 10, 00);
        Instant taskStart = calendar.toInstant();
        calendar.set(2021, Calendar.MARCH, 20, 11, 00);
        Instant taskEnd = calendar.toInstant();

        for (int i = 1; i < 7; i++){
            Task task = new Task();
            task.setDepartment(null);
            task.setTeam(null);
            task.setCreateDate(Date.from(taskEnd));
            task.setStartTime(taskStart);
            task.setEndTime(taskEnd);
            task.setSeconds(3600);
            taskList.add(task);
        }

        calendar.set(2021, Calendar.MARCH, 27, 21, 00);
        taskStart = calendar.toInstant();
        calendar.set(2021, Calendar.MARCH, 27, 22, 00);
        taskEnd = calendar.toInstant();

        taskList.get(0).setUser(userRepository.findFirstByUsername("user1"));
        taskList.get(0).setTask(TaskEnum.IMPLEMENTIERUNG);
        taskList.get(1).setUser(userRepository.findFirstByUsername("user2"));
        taskList.get(1).setTask(TaskEnum.DESIGN);
        taskList.get(2).setUser(userRepository.findFirstByUsername("user3"));
        taskList.get(2).setTask(TaskEnum.KUNDENBESPRECHUNG);
        taskList.get(3).setUser(userRepository.findFirstByUsername("user4"));
        taskList.get(3).setTask(TaskEnum.DOKUMENTATION);
        taskList.get(4).setUser(userRepository.findFirstByUsername("user1"));
        taskList.get(4).setTask(TaskEnum.FEHLERMANAGEMENT);
        taskList.get(4).setStartTime(taskStart);
        taskList.get(4).setEndTime(taskEnd);
        taskList.get(5).setUser(userRepository.findFirstByUsername("user6"));
        taskList.get(5).setTask(TaskEnum.FORTBILDUNG);

        for (Task t: taskList){
            taskRepository.save(t);
        }



        calendar.set(2021, Calendar.MARCH, 25, 00, 00);
        Instant weekStart = calendar.toInstant();
        calendar.set(2021, Calendar.MARCH, 31, 23, 59);
        Instant weekEnd = calendar.toInstant();
        userRepository.findFirstByUsername("user4").getId();


        badgeService.evaluateWeeklyBadges(weekStart, weekEnd);

        calendar.set(2021, Calendar.MARCH, 23, 00, 00);
        Instant beforeBadges = calendar.toInstant();

        List<Badge> badges = badgeRepository.findBadgesAfterDate(beforeBadges);

        Assert.assertTrue(!badges.isEmpty());

        for(Badge b : badges){
            if(b.getBadgeType()== BadgeEnum.WEEKLY_CODE_MONKEY){
                Assert.assertEquals(userRepository.findFirstByUsername("user1"), b.getUser());
            }
            else if(b.getBadgeType()== BadgeEnum.CREATIVE_MIND){
                Assert.assertEquals(b.getUser(),userRepository.findFirstByUsername("user2"));
            }
            else if(b.getBadgeType()== BadgeEnum.FRIEND_AND_HELPER){
                Assert.assertEquals(b.getUser(),userRepository.findFirstByUsername("user3"));
            }
            else if(b.getBadgeType()== BadgeEnum.ALL_ROUNDER){
                Assert.assertEquals(b.getUser(),userRepository.findFirstByUsername("user1"));
            }
            else if(b.getBadgeType()== BadgeEnum.NIGHT_OWL){
                Assert.assertEquals(b.getUser(),userRepository.findFirstByUsername("user1"));
            }
            else if(b.getBadgeType()== BadgeEnum.WISDOM_SEEKER){
                Assert.assertEquals(b.getUser(),userRepository.findFirstByUsername("user6"));
            }

        }

    }
}