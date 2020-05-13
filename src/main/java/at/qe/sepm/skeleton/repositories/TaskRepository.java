package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface TaskRepository extends AbstractRepository<Task, String> {

    @Query("SELECT t FROM Task t WHERE t.user=:user")
    List<Task> findTasksFromUser(@Param("user") User user);

    @Query("SELECT t FROM Task t WHERE t.user=:user and t.startTime>=:start_date and t.endTime <=:end_date")
    List<Task> findUserTasksBetweenDates(@Param("user") User user, @Param("start_date") Instant start_date, @Param("end_date") Instant end_date);

    @Query("SELECT t FROM Task t WHERE t.team=:team")
    List<Task> findTasksFromTeam(@Param("team") Team team);

    @Query("SELECT t FROM Task t WHERE t.team=:team and t.startTime>=:start_date and t.endTime<=:end_date")
    List<Task> findTeamTasksBetweenDates(@Param("team") Team team, @Param("start_date") Instant start_date, @Param("end_date") Instant end_date);

    @Query("SELECT t FROM Task t WHERE t.department=:department and t.startTime>=:start_date and t.endTime<=:end_date")
    List<Task> findDepartmentTasksBetweenDates(@Param("department") Department department, @Param("start_date") Instant start_date, @Param("end_date") Instant end_date);

}
