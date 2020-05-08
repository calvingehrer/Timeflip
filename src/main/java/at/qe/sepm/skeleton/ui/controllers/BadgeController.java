package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Badge;
import at.qe.sepm.skeleton.model.BadgeEnum;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope("view")
public class BadgeController {

    @Autowired
    private UserService userService;

    private Badge badge_monkey = new Badge();

    private Badge badge_creative = new Badge();

    private Badge badge_friend = new Badge();

    private Set<Badge> allBadges = new HashSet<>();

    public Set<Badge> getAllBadges() {
        return allBadges;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setAllBadges(Set<Badge> allBadges) {
        this.allBadges = allBadges;
        this.allBadges.add(badge_monkey);
        this.allBadges.add(badge_creative);
        this.allBadges.add(badge_friend);
    }

    public Badge getBadge_monkey() {
        return badge_monkey;
    }

    public void setBadge_monkey(Badge badge_monkey) {
        this.badge_monkey = badge_monkey;
    }

    public Badge getBadge_creative() {
        return badge_creative;
    }

    public void setBadge_creative(Badge badge_creative) {
        this.badge_creative = badge_creative;
    }

    public Badge getBadge_friend() {
        return badge_friend;
    }

    public void setBadge_friend(Badge badge_friend) {
        this.badge_friend = badge_friend;
    }
}
