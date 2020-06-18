package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Badge;
import at.qe.sepm.skeleton.model.BadgeEnum;
import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.User;
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
    List<Badge> findBadgeFromUserByType(@Param("user") User user, @Param("badgeType") BadgeEnum badgeType);

    @Query("SELECT b FROM Badge b WHERE b.user=:user AND b.dateOfBadge>=:startDate AND b.dateOfBadge<=:endDate")
    List<Badge> findBadgesFromUserInInterval(@Param("user") User user, @Param("startDate") Instant startDate,
                                             @Param("endDate") Instant endDate);

    @Query("SELECT b FROM Badge b WHERE b.user.department=:department AND b.badgeType=:badgeType")
    List<Badge> findBadgesFromDepartmentByType(@Param("department") Department department,
                                               @Param("badgeType") BadgeEnum badgeType);

    @Query("SELECT b FROM Badge b WHERE b.user.department=:department AND b.dateOfBadge>=:startDate AND b.dateOfBadge<=:endDate")
    List<Badge> findBadgesFromDepartmentInIntervall(@Param("department") Department department,
                                                    @Param("startDate") Instant startDate,
                                                    @Param("endDate") Instant endDate);


}
