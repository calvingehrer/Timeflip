package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Badge;
import at.qe.sepm.skeleton.model.BadgeEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.BadgeService;
import at.qe.sepm.skeleton.services.UserService;
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
    private UserService userService;




    public List<Badge> getBadgesFromUser(){
        return badgeService.getAllBadgesFromUser(userService.getAuthenticatedUser());
    }

    public List<Badge> getBadgesFromDepartment(){
        return badgeService.getAllBadgesFromDepartment(userService.getAuthenticatedUser().getDepartment());
    }

    public List<Badge> getBadgesFromLastWeek(){

        Calendar lastWeek = getWeekStart();
        lastWeek.add(Calendar.DATE, -7);
        return badgeService.getAllBadgesAfterDate(lastWeek.toInstant());
    }

    public static Calendar getWeekStart() {

        Calendar lastWeek = Calendar.getInstance();
        StatisticsController.setDayToBeginning(lastWeek);
        lastWeek.getFirstDayOfWeek();
        return lastWeek;
    }

}
