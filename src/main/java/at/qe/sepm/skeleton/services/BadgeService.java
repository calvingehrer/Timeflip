package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.BadgeRepository;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

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

    public void evaluateWeeklyBadges(Instant startDate, Instant endDate){

        //evaluateCodeMonkey(startDate, endDate);
        /*evaluateCreativeMind(startDate, endDate);
        evaluateFriendAndHelper(startDate, endDate);
        evaluateNightOwl(startDate, endDate);
        evaluateAllRounder(startDate, endDate);
        evaluateWisdomSeeker(startDate, endDate);
        */

    }

    /**
     * returns the CodeMonkey (Person with most Implementation Time) of the given period
     * @param startDate
     * @param endDate
     */

    private void evaluateCodeMonkey(Instant startDate, Instant endDate){
        List<Task> implementationList;

        implementationList = taskRepository.findTypeTasksBetweenDates(TaskEnum.IMPLEMENTIERUNG, startDate, endDate);


        HashMap<String, Integer> implementationTimePerUser = new HashMap<>();

        for(Task entry : implementationList){
            String currentUserID = entry.getUser().getId();
            if(!implementationTimePerUser.containsKey(currentUserID)){
                implementationTimePerUser.put(currentUserID, entry.getSeconds()); // maybe not username?
            }
            else{
                implementationTimePerUser.put(currentUserID, implementationTimePerUser.get(currentUserID) + entry.getSeconds());
            }

        }

        String userWithMostSeconds = "";

        for(String userId : implementationTimePerUser.keySet()){
            if(userWithMostSeconds.isEmpty()){
                userWithMostSeconds = userId;
            }
            else if(implementationTimePerUser.get(userId) > implementationTimePerUser.get(userWithMostSeconds)){
                userWithMostSeconds = userId;
            }
        }

        if(userWithMostSeconds.isEmpty()){
            return;
        }

        Badge codeMonkey = new Badge();

        codeMonkey.setBadgeType(BadgeEnum.WEEKLY_CODE_MONKEY);
        codeMonkey.setUser(userRepository.findFirstByUsername(userWithMostSeconds));
        codeMonkey.setDateOfBadge(startDate);
        codeMonkey.setImagePath("/resources/badges/weekly_code_monkey.png");

        System.out.println("CodeMoney goes to "+ codeMonkey.getUser().getId());


        }

    /**
     * returns the Creative Mind (Person with most Design Time) of the given period
     * @param startDate
     * @param endDate
     */

    private void evaluateCreativeMind(Instant startDate, Instant endDate){
        List<Task> designList;

        designList = taskRepository.findTypeTasksBetweenDates(TaskEnum.DESIGN, startDate, endDate);

        HashMap<String, Integer> designTimePerUser = new HashMap<>();

        for(Task entry : designList){
            String currentUserID = entry.getUser().getId();
            if(!designTimePerUser.containsKey(currentUserID)){
                designTimePerUser.put(currentUserID, entry.getSeconds()); // maybe not username?
            }
            else{
                designTimePerUser.put(currentUserID, designTimePerUser.get(currentUserID) + entry.getSeconds());
            }

        }

        String userWithMostSeconds = "";

        for(String userId : designTimePerUser.keySet()){
            if(userWithMostSeconds.isEmpty()){
                userWithMostSeconds = userId;
            }
            else if(designTimePerUser.get(userId) > designTimePerUser.get(userWithMostSeconds)){
                userWithMostSeconds = userId;
            }
        }

        if(userWithMostSeconds.isEmpty()){
            return;
        }

        Badge creativeMind = new Badge();

        creativeMind.setBadgeType(BadgeEnum.CREATIVE_MIND);
        creativeMind.setUser(userRepository.findFirstByUsername(userWithMostSeconds));
        creativeMind.setDateOfBadge(startDate);
        creativeMind.setImagePath("/resources/badges/creative_mind.png");


        return;
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

    private Badge evaluateAllRounder(Instant startDate, Instant endDate){
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

        badge.setUser(winner.getUser());
        badge.setDateOfBadge(startDate);

        return badge;
    }

}
