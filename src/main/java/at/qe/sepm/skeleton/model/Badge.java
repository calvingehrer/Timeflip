package at.qe.sepm.skeleton.model;

import javax.persistence.*;

/**
 * Entity representing Badges.
 *
 */


@Embeddable
public class Badge {

    private String badgeId;

    private BadgeEnum badgeType;

    private String badgeDescription;

    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public BadgeEnum getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(BadgeEnum badgeType) {
        this.badgeType = badgeType;
    }

    public String getBadgeDescription() {
        return badgeDescription;
    }

    public void setBadgeDescription(String badgeDescription) {
        this.badgeDescription = badgeDescription;
    }


    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.User[ id=" + badgeId + " ]";
    }




}
