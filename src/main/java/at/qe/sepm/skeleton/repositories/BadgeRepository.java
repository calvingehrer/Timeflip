package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Badge;
import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface BadgeRepository extends AbstractRepository<Badge, String> {

    @Query("SELECT b FROM Badge b WHERE b.user=:user ORDER BY b.dateOfBadge")
    List<Badge> findBadgesFromUser(@Param("user") User user);

    @Query("SELECT b FROM Badge b WHERE b.dateOfBadge>=:date")
    List<Badge> findBadgesAfterDate(@Param("date") Instant date);


}
