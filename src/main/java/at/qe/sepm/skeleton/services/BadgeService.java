package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Badge;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.BadgeRepository;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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



}
