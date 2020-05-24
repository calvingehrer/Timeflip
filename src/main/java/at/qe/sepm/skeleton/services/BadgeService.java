package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.BadgeRepository;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

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

    public List<Badge> getAllBadgesFromDepartment(Department department){
        return badgeRepository.findBadgesFromDepartment(department);
    }

    public List<Badge> getAllBadgesAfterDate(Instant date){
        return badgeRepository.findBadgesAfterDate(date);
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

        evaluateCodeMonkey(startDate, endDate);
        evaluateCreativeMind(startDate, endDate);
        evaluateFriendAndHelper(startDate, endDate);
        evaluateNightOwl(startDate, endDate);
        evaluateAllRounder(startDate, endDate);
        evaluateWisdomSeeker(startDate, endDate);


    }

    /**
     * creates Badge for the Code Monkey (Person with most Implementation Time) of the given period
     * @param startDate
     * @param endDate
     */

    private void evaluateCodeMonkey(Instant startDate, Instant endDate){
        List<Task> implementationList;

        implementationList = taskRepository.findTypeTasksBetweenDates(TaskEnum.IMPLEMENTIERUNG, startDate, endDate);


        String userWithMostSeconds = evaluateUserWithMostTime(implementationList);

        if(userWithMostSeconds.isEmpty()){
            return;
        }

        Badge codeMonkey = new Badge();

        codeMonkey.setBadgeType(BadgeEnum.WEEKLY_CODE_MONKEY);
        codeMonkey.setUser(userRepository.findFirstByUsername(userWithMostSeconds));
        codeMonkey.setDateOfBadge(startDate);
        codeMonkey.setImagePath("/resources/badges/weekly_code_monkey.png");

        System.out.println("Code Money goes to "+ codeMonkey.getUser().getId());

        badgeRepository.save(codeMonkey);

        }

    /**
     * creates Badge for the Creative Mind (Person with most Design Time) of the given period
     * @param startDate
     * @param endDate
     */

    private void evaluateCreativeMind(Instant startDate, Instant endDate){
        List<Task> designList;

        designList = taskRepository.findTypeTasksBetweenDates(TaskEnum.DESIGN, startDate, endDate);

        String userWithMostSeconds = evaluateUserWithMostTime(designList);

        if(userWithMostSeconds.isEmpty()){
            return;
        }

        Badge creativeMind = new Badge();

        creativeMind.setBadgeType(BadgeEnum.CREATIVE_MIND);
        creativeMind.setUser(userRepository.findFirstByUsername(userWithMostSeconds));
        creativeMind.setDateOfBadge(startDate);
        creativeMind.setImagePath("/resources/badges/creative_mind.png");

        System.out.println("Creative Mind goes to "+ creativeMind.getUser().getId());

        badgeRepository.save(creativeMind);

    }



    /**
     * creates Badge for the Friend and Helper (Person with most Customer Service Time) of the given period
     * @param startDate
     * @param endDate
     */

    private void evaluateFriendAndHelper(Instant startDate, Instant endDate){
        List<Task> customerServiceList;

        customerServiceList = taskRepository.findTypeTasksBetweenDates(TaskEnum.KUNDENBESPRECHNUNG, startDate, endDate);

        String userWithMostSeconds = evaluateUserWithMostTime(customerServiceList);

        if(userWithMostSeconds.isEmpty()){
            return;
        }

        Badge friendAndHelper = new Badge();

        friendAndHelper.setBadgeType(BadgeEnum.FRIEND_AND_HELPER);
        friendAndHelper.setUser(userRepository.findFirstByUsername(userWithMostSeconds));
        friendAndHelper.setDateOfBadge(startDate);
        friendAndHelper.setImagePath("/resources/badges/friend_and_helper.png");

        System.out.println("Friend and Helper goes to "+ friendAndHelper.getUser().getId());

        badgeRepository.save(friendAndHelper);
    }


    /**
     * creates Badge for the All-Rounder (Person with most different tasks) of the given period by creating a list of
     * every task between startDate and endDate, then creating a HashMap where every new task gets saved per user.
     * Finally the user with the most different tasks is searched in that HashMap.
     *
     * @param startDate
     * @param endDate
     */

    private void evaluateAllRounder(Instant startDate, Instant endDate){
        List<Task> taskList;

        taskList = taskRepository.findTasksBetweenDates(startDate, endDate);


        HashMap<String, List<TaskEnum>> differentTasksPerUser = new HashMap<>();

        for(Task entry : taskList){
            String currentUserID = entry.getUser().getId();
            if(!differentTasksPerUser.containsKey(currentUserID)){
                List<TaskEnum> differentTasks = new ArrayList<>();
                differentTasks.add(entry.getTask());
                differentTasksPerUser.put(currentUserID, differentTasks);
            }
            else{
                if(!differentTasksPerUser.get(currentUserID).contains(entry.getTask())){
                    differentTasksPerUser.get(currentUserID).add(entry.getTask());
                }
            }
        }

        String userWithMostDifferentTasks = "";

        for(String userId : differentTasksPerUser.keySet()){
            if(userWithMostDifferentTasks.isEmpty()){
                userWithMostDifferentTasks = userId;
            }
            else if(differentTasksPerUser.get(userId).size() > differentTasksPerUser.get(userWithMostDifferentTasks).size()){
                userWithMostDifferentTasks = userId;
            }
        }

        if(userWithMostDifferentTasks.isEmpty()){
            return;
        }

        Badge allRounder = new Badge();

        allRounder.setBadgeType(BadgeEnum.ALL_ROUNDER);
        allRounder.setUser(userRepository.findFirstByUsername(userWithMostDifferentTasks));
        allRounder.setDateOfBadge(startDate);
        allRounder.setImagePath("/resources/badges/all_rounder.png");

        System.out.println("All-Rounder goes to "+ allRounder.getUser().getId());

        badgeRepository.save(allRounder);


    }

    /**
     * creates Badge for the Night Owl (Person with most time during 20:00 and 6:00) of the given period
     * @param startDate
     * @param endDate
     */

    private void evaluateNightOwl(Instant startDate, Instant endDate){
        List<Task> taskList;

        taskList = taskRepository.findTasksBetweenDates(startDate, endDate);

        List<Task> nightTasks = new ArrayList<>();

        for(Task entry : taskList){
            Integer hour = entry.getStartTime().atZone(ZoneOffset.UTC).getHour();
            if(hour >= 20 || hour < 6 && entry.getTask() != TaskEnum.AUSZEIT){
                nightTasks.add(entry);
            }
        }

        if(nightTasks.isEmpty()){
            return;
        }

        String userWithMostSeconds = evaluateUserWithMostTime(nightTasks);

        if(userWithMostSeconds.isEmpty()){
            return;
        }

        Badge nightOwl = new Badge();

        nightOwl.setBadgeType(BadgeEnum.NIGHT_OWL);
        nightOwl.setUser(userRepository.findFirstByUsername(userWithMostSeconds));
        nightOwl.setDateOfBadge(startDate);
        nightOwl.setImagePath("/resources/badges/night_owl.png");

        System.out.println("Night owl goes to "+ nightOwl.getUser().getId());

        badgeRepository.save(nightOwl);

    }

    /**
     * creates Badge for the Wisdom Seeker (Person with most Fortbildung Time) of the given period
     * @param startDate
     * @param endDate
     */

    private void evaluateWisdomSeeker(Instant startDate, Instant endDate){
        List<Task> educationList;

        educationList = taskRepository.findTypeTasksBetweenDates(TaskEnum.FORTBILDUNG, startDate, endDate);

        String userWithMostSeconds = evaluateUserWithMostTime(educationList);

        if(userWithMostSeconds.isEmpty()){
            return;
        }

        Badge wisdomSeeker = new Badge();

        wisdomSeeker.setBadgeType(BadgeEnum.WISDOM_SEEKER);
        wisdomSeeker.setUser(userRepository.findFirstByUsername(userWithMostSeconds));
        wisdomSeeker.setDateOfBadge(startDate);
        wisdomSeeker.setImagePath("/resources/badges/wisdom_seeker.png");

        System.out.println("Wisdom seeker goes to "+ wisdomSeeker.getUser().getId());

        badgeRepository.save(wisdomSeeker);
    }

    /**
     * Help function for badges that have a specific taskTypeList and need to find user with most seconds; returns The userWithMostSeconds
     * @param taskTypeList
     * @return userWithMostSeconds
     */

    private String evaluateUserWithMostTime(List<Task> taskTypeList) {

        HashMap<String, Integer> specificTaskTimePerUser = new HashMap<>();

        for(Task entry : taskTypeList){
            String currentUserID = entry.getUser().getId();
            if(!specificTaskTimePerUser.containsKey(currentUserID)){
                specificTaskTimePerUser.put(currentUserID, entry.getSeconds()); // maybe not username?
            }
            else{
                specificTaskTimePerUser.put(currentUserID, specificTaskTimePerUser.get(currentUserID) + entry.getSeconds());
            }

        }

        String userWithMostSeconds = "";

        for(String userId : specificTaskTimePerUser.keySet()){
            if(userWithMostSeconds.isEmpty()){
                userWithMostSeconds = userId;
            }
            else if(specificTaskTimePerUser.get(userId) > specificTaskTimePerUser.get(userWithMostSeconds)){
                userWithMostSeconds = userId;
            }
        }

        return userWithMostSeconds;
    }

}
