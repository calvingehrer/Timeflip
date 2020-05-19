package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Badge;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.BadgeRepository;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("application")
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

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
        badges.add(evaluteCodeMonkey(startDate, endDate));
        badges.add(evaluateCreativeMind(startDate, endDate));
        badges.add(evaluateriendAndHelper(startDate, endDate));
        badges.add(evaluateNightOwl(startDate, endDate));
        badges.add(evaluteAllRounder(startDate, endDate));
        badges.add(evaluateWisdomSeeker(startDate, endDate));


        return badges;
    }

    /**
     * returns the CodeMonkey (Person with most Implementation Time) of the given period
     */

    public Badge evaluteCodeMonkey(Instant startDate, Instant endDate){
        Badge codeMonkey = new Badge();

        return codeMonkey;
    }

    /**
     * returns the Creative Mind (Person with most Design Time) of the given period
     */

    public Badge evaluateCreativeMind(Instant startDate, Instant endDate){
        Badge creativeMind = new Badge();

        return creativeMind;
    }

    /**
     * returns the Friend and Helper (Person with most Customer Service Time) of the given period
     */

    public Badge evaluateriendAndHelper(Instant startDate, Instant endDate){
        Badge friendAndHelper = new Badge();

        return friendAndHelper;
    }


    /**
     * returns the All-Rounder (Person with most different tasks) of the given period
     */

    public Badge evaluteAllRounder(Instant startDate, Instant endDate){
        Badge allRounder = new Badge();

        return allRounder;
    }

    /**
     * returns the Night Owl (Person with most time during 20:00 and 6:00) of the given period
     */

    public Badge evaluateNightOwl(Instant startDate, Instant endDate){
        Badge nightOwl = new Badge();

        return nightOwl;
    }

    /**
     * returns the Wisdom Seeker (Person with most Fortbildung Time) of the given period
     */

    public Badge evaluateWisdomSeeker(Instant startDate, Instant endDate){
        Badge wisdomSeeker = new Badge();

        return wisdomSeeker;
    }

}
