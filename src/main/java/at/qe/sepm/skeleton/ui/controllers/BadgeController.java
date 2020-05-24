package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Badge;
import at.qe.sepm.skeleton.model.BadgeEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.BadgeService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Scope("view")
public class BadgeController implements Serializable {

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private CurrentUserBean currentUserBean;

    /**
     *  initializes the current user and the Badges for that week that most of our test data is in
     */

    @PostConstruct
    public void init() {
        currentUserBean.init();
        /*Calendar start = Calendar.getInstance();
        start.set(2020, Calendar.MAY, 4, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(2020, Calendar.MAY, 10, 23, 59, 59);
        badgeService.evaluateWeeklyBadges(start.toInstant(), end.toInstant());*/
    }

    public User getCurrentUser() {
        return currentUserBean.getCurrentUser();
    }


    public List<Badge> getBadgesFromUser(){
        return badgeService.getAllBadgesFromUser(currentUserBean.getCurrentUser());
    }

    public List<Badge> getBadgesFromDepartment(){
        return badgeService.getAllBadgesFromDepartment(currentUserBean.getCurrentUser().getDepartment());
    }

    public List<Badge> getBadgesFromLastWeek(){

        Calendar lastWeek = getWeekStart();
        lastWeek.add(Calendar.DATE, -7);
        return badgeService.getAllBadgesAfterDate(lastWeek.toInstant());
    }

    public static Calendar getWeekStart() {

        Calendar lastWeek = Calendar.getInstance();
        lastWeek.set(Calendar.HOUR_OF_DAY, 0);
        lastWeek.set(Calendar.MINUTE, 0);
        lastWeek.set(Calendar.SECOND, 0);
        lastWeek.set(Calendar.MILLISECOND, 0);
        lastWeek.getFirstDayOfWeek();
        return lastWeek;
    }

}
