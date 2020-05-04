package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("application")
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasksFromUser(User user) {
        return taskRepository.findTasksFromUser(user);
    }


}
