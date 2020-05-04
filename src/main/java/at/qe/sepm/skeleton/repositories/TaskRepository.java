package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends AbstractRepository<Task, String> {

    @Query("SELECT t FROM Task t WHERE t.user=:user")
    List<Task> findTasksFromUser(@Param("user") User user);


}
