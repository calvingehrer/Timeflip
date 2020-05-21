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

    @PostConstruct
    public void init() {
        currentUserBean.init();
    }

    public List<Badge> getBadgesFromUser(){ return badgeService.getAllBadgesFromUser(currentUserBean.getCurrentUser()); }
}
