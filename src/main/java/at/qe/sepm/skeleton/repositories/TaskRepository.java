package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends AbstractRepository<Task, String> {


}
