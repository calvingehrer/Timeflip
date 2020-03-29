package at.qe.sepm.skeleton.model;

import javax.persistence.*;
import java.util.Set;
import javax.persistence.Entity;




public class Badge {


    private int badgeId;

    @ElementCollection(targetClass = BadgeEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Badge_BadgeType")
    @Enumerated(EnumType.STRING)
    private Set<UserRole> badgeType;

    public Set<UserRole> getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(Set<UserRole> badgeType) {
        this.badgeType = badgeType;
    }

    public int getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(int badgeId) {
        this.badgeId = badgeId;
    }
    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.User[ id=" + badgeId + " ]";
    }
}
