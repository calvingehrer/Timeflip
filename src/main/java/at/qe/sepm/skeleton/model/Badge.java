package at.qe.sepm.skeleton.model;

import javax.persistence.*;
import java.time.Instant;

/**
 * Entity representing Badges.
 */


@Entity
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long badgeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "badge_type")
    private BadgeEnum badgeType;

    @Column(name = "badge_date")
    private Instant dateOfBadge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "badge_image")
    private String imagePath;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.User[ id=" + badgeId + " ]";
    }


}
