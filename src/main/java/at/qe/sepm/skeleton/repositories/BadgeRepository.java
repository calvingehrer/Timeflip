package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface BadgeRepository extends AbstractRepository<Badge, String> {

    @Query("SELECT b FROM Badge b WHERE b.user=:user ORDER BY b.dateOfBadge DESC")
    List<Badge> findBadgesFromUser(@Param("user") User user);

    @Query("SELECT b FROM Badge b WHERE b.user.department=:department ORDER BY b.dateOfBadge DESC ")
    List<Badge> findBadgesFromDepartment(@Param("department") Department department);

    @Query("SELECT b FROM Badge b WHERE b.dateOfBadge>=:date")
    List<Badge> findBadgesAfterDate(@Param("date") Instant date);

    @Query("SELECT b FROM Badge b WHERE b.user=:user AND b.badgeType=:badgeType")
    List<Badge> findBadgeFromUserByType(@Param("user") User user, @Param("badgeType")BadgeEnum badgeType);


}
