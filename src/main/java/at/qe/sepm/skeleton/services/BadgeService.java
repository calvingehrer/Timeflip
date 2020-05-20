package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.BadgeRepository;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("application")
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<Badge> getAllBadgesFromUser(User user){
        return badgeRepository.findBadgesFromUser(user);
    }

    public void deleteBadge(Badge badge) {
        badgeRepository.delete(badge);
    }

    public void deleteBadgesOfUser(User user) {
        for(Badge b: badgeRepository.findBadgesFromUser(user)) {
            deleteBadge(b);
        }
    }

    /**
     * creates the Badges of the given period
     */

    public List<Badge> evaluateWeeklyBadges(Instant startDate, Instant endDate){
        List<Badge> badges = new ArrayList<>();

        badges.add(evaluateCodeMonkey(startDate, endDate));
        badges.add(evaluateCreativeMind(startDate, endDate));
        badges.add(evaluateFriendAndHelper(startDate, endDate));
        badges.add(evaluateNightOwl(startDate, endDate));
        badges.add(evaluteAllRounder(startDate, endDate));
        badges.add(evaluateWisdomSeeker(startDate, endDate));

        return badges;
    }

    /**
     * returns the CodeMonkey (Person with most Implementation Time) of the given period
     * @param startDate
     * @param endDate
     */

    private Badge evaluateCodeMonkey(Instant startDate, Instant endDate){
        Badge codeMonkey = new Badge();
        List<Task> implementationList;

        implementationList = taskRepository.findTypeTasksBetweenDates(TaskEnum.IMPLEMENTIERUNG, startDate, endDate);

        return evaluateMostTimeFromList(startDate, codeMonkey, implementationList);
    }

    /**
     * returns the Creative Mind (Person with most Design Time) of the given period
     * @param startDate
     * @param endDate
     */

    private Badge evaluateCreativeMind(Instant startDate, Instant endDate){
        Badge creativeMind = new Badge();
        List<Task> designList;

        designList = taskRepository.findTypeTasksBetweenDates(TaskEnum.DESIGN, startDate, endDate);

        return evaluateMostTimeFromList(startDate, creativeMind, designList);
    }



    /**
     * returns the Friend and Helper (Person with most Customer Service Time) of the given period
     * @param startDate
     * @param endDate
     */

    private Badge evaluateFriendAndHelper(Instant startDate, Instant endDate){
        Badge friendAndHelper = new Badge();
        List<Task> customerServiceList;

        customerServiceList = taskRepository.findTypeTasksBetweenDates(TaskEnum.KUNDENBESPRECHNUNG, startDate, endDate);

        return evaluateMostTimeFromList(startDate, friendAndHelper, customerServiceList);
    }


    /**
     * returns the All-Rounder (Person with most different tasks) of the given period
     * @param startDate
     * @param endDate
     */

    private Badge evaluteAllRounder(Instant startDate, Instant endDate){
        Badge allRounder = new Badge();

        return allRounder;
    }

    /**
     * returns the Night Owl (Person with most time during 20:00 and 6:00) of the given period
     * @param startDate
     * @param endDate
     */

    private Badge evaluateNightOwl(Instant startDate, Instant endDate){
        Badge nightOwl = new Badge();

        return nightOwl;
    }

    /**
     * returns the Wisdom Seeker (Person with most Fortbildung Time) of the given period
     * @param startDate
     * @param endDate
     */

    private Badge evaluateWisdomSeeker(Instant startDate, Instant endDate){
        Badge wisdomSeeker = new Badge();
        List<Task> educationList;

        educationList = taskRepository.findTypeTasksBetweenDates(TaskEnum.FORTBILDUNG, startDate, endDate);

        return evaluateMostTimeFromList(startDate, wisdomSeeker, educationList);
    }

    /**
     * Help function for badges that have a list and need to find user with most seconds; returns The completed badge
     * @param startDate
     * @param badge
     * @param taskTypeList
     * @return badge
     */

    private Badge evaluateMostTimeFromList(Instant startDate, Badge badge, List<Task> taskTypeList) {
        Task winner = taskTypeList.get(0);

        for(Task entry : taskTypeList){
            if (entry.getSeconds() > winner.getSeconds()){
                winner = entry;
            }
        }

        badge.setBadgeType(BadgeEnum.WEEKLY_CODE_MONKEY);
        badge.setUser(winner.getUser());
        badge.setDateOfBadge(startDate);

        return badge;
    }

}
