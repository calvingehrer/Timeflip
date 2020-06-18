package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Badge;
import at.qe.sepm.skeleton.services.BadgeService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@Scope("view")
public class BadgeController implements Serializable {

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private UserService userService;

    private String interval = "";
    private String badgeType = "";
    private Date chosenDate;

    public static Calendar getWeekStart() {

        Calendar lastWeek = Calendar.getInstance();
        StatisticsController.setDayToBeginning(lastWeek);
        lastWeek.getFirstDayOfWeek();
        return lastWeek;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(String badgeType) {
        this.badgeType = badgeType;
    }

    public Date getChosenDate() {
        return chosenDate;
    }

    public void setChosenDate(Date chosenDate) {
        this.chosenDate = chosenDate;
    }

    public List<Badge> getBadges(boolean forUser) {
        Calendar calendar = Calendar.getInstance();
        if (chosenDate != null) {
            calendar.setTime(chosenDate);
        }
        Date startTimeRange;
        Date endTimeRange;
        switch (interval) {
            case ("Daily"):
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startTimeRange = calendar.getTime();
                calendar.add(Calendar.DATE, 1);
                endTimeRange = calendar.getTime();
                return badgeService.getBadgesBetweenDates(userService.getAuthenticatedUser(), forUser,
                        startTimeRange.toInstant(), endTimeRange.toInstant());
            case ("Weekly"):
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startTimeRange = calendar.getTime();
                calendar.add(Calendar.DATE, 7);
                endTimeRange = calendar.getTime();
                return badgeService.getBadgesBetweenDates(userService.getAuthenticatedUser(), forUser,
                        startTimeRange.toInstant(), endTimeRange.toInstant());
            case ("Monthly"):
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startTimeRange = calendar.getTime();
                calendar.add(Calendar.MONTH, 1);
                endTimeRange = calendar.getTime();
                return badgeService.getBadgesBetweenDates(userService.getAuthenticatedUser(), forUser,
                        startTimeRange.toInstant(), endTimeRange.toInstant());
            default:
                return badgeService.getBadgesOfType(userService.getAuthenticatedUser(), forUser, this.badgeType);
        }

    }

    public List<Badge> getBadgesFromDepartment() {
        List<Badge> sorted = getBadges(false);
        Collections.sort(sorted, (task1, task2) -> task2.getDateOfBadge().compareTo(task1.getDateOfBadge()));
        return sorted;
    }

    public List<Badge> getBadgesFromLastWeek() {

        Calendar lastWeek = getWeekStart();
        lastWeek.add(Calendar.DATE, -7);
        return badgeService.getAllBadgesAfterDate(lastWeek.toInstant());
    }

    public void resetFilter() {
        this.setInterval("");
        this.setBadgeType("");
    }

    public List<Badge> getSortedBadgesOfUser() {
        List<Badge> sorted = getBadges(true);
        Collections.sort(sorted, (task1, task2) -> task2.getDateOfBadge().compareTo(task1.getDateOfBadge()));
        return sorted;
    }

}
