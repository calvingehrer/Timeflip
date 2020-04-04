package at.qe.sepm.skeleton.model;

import javax.persistence.*;
import java.util.Set;
import javax.persistence.Entity;



@Entity
public class Badge {


    @Id
    private String badgeId;

    @ElementCollection(targetClass = BadgeEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Badge_BadgeType")
    @Enumerated(EnumType.STRING)
    private Set<BadgeEnum> badgeType;

    public Set<BadgeEnum> getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(Set<BadgeEnum> badgeType) {
        this.badgeType = badgeType;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.User[ id=" + badgeId + " ]";
    }




}
