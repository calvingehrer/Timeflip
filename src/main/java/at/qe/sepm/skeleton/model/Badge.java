package at.qe.sepm.skeleton.model;

import javax.persistence.*;
import java.time.Instant;

/**
 * Entity representing Badges.
 *
 */


@Entity
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "badge_id")
    private String badgeId;

    @Column(name = "badge_type")
    private BadgeEnum badgeType;

    @Column(name = "badge_date")
    private Instant dateOfBadge;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

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

    public Instant getDateOfBadge() {
        return dateOfBadge;
    }

    public void setDateOfBadge(Instant dateOfBadge) {
        this.dateOfBadge = dateOfBadge;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.User[ id=" + badgeId + " ]";
    }




}
