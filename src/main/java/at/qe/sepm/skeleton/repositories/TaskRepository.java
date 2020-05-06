package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface TaskRepository extends AbstractRepository<Task, String> {

    @Query("SELECT t FROM Task t WHERE t.user=:user")
    List<Task> findTasksFromUser(@Param("user") User user);

    @Query("SELECT t.task as task FROM Task t WHERE t.user=:user and t.start_time>=:start_date and t.start_time <=:end_time")
    Set<TaskEnum> findDateTasks(@Param("user") User user, @Param("start_time") Instant start_time, @Param("end_time") Instant end_time);

}
