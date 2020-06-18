package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.DepartmentRepository;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Component
@Scope("application")
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Logger<String, User> logger;


    /**
     * @return all departments
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * saves a new department
     *
     * @param department
     * @return saved Department
     */


    @PreAuthorize("hasAuthority('ADMIN')")
    public void saveDepartment(Department department, Set<Team> addedTeams, Set<Team> removedTeams, User oldLeader, User newLeader) {
        departmentRepository.save(department);
        if (addedTeams != null) {
            for (Team t : addedTeams) {
                userRepository.findUsersOfTeam(t)
                        .stream()
                        .forEach(user -> user.setDepartment(department));
                taskRepository.findTasksFromTeam(t)
                        .stream()
                        .forEach(task -> task.setDepartment(department));
                t.setDepartment(department);
                teamRepository.save(t);
            }
        }
        if (removedTeams != null) {
            for (Team t : removedTeams) {
                userRepository.findUsersOfTeam(t)
                        .stream()
                        .forEach(user -> {
                            user.setDepartment(null);
                            userRepository.save(user);
                        });

                taskRepository.findTasksFromTeam(t)
                        .stream()
                        .forEach(task -> {
                            task.setDepartment(null);
                            taskRepository.save(task);
                        });
                t.setDepartment(null);
                teamRepository.save(t);
            }
        }

        if (oldLeader != null) {
            oldLeader.setDepartment(null);
            userService.saveUser(oldLeader);
        }

        if (newLeader != null) {
            newLeader.setDepartment(department);
            userService.saveUser(newLeader);
        }

        logger.logUpdate(department.toString(), userService.getAuthenticatedUser());

    }

    /**
     * @param headOfDepartment
     * @param department
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addNewDepartment(User headOfDepartment, Department department) {
        Department newDepartment = new Department();

        newDepartment.setDepartmentName(department.getDepartmentName());
        newDepartment.setCreateDate(new Date());

        saveDepartment(newDepartment, null, null, null, headOfDepartment);

        logger.logCreation(department.toString(), userService.getAuthenticatedUser());
    }

    /**
     * @param departmentName
     * @return department by department name
     */

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Department loadDepartment(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName);
    }

    /**
     * deletes department
     *
     * @param department
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDepartment(Department department) {
        departmentRepository.delete(department);
        logger.logDeletion(department.getDepartmentName(), userService.getAuthenticatedUser());
    }

    /**
     * find  teams of department
     *
     * @param department
     * @return Teams of the department
     */

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public List<Team> getTeamsOfDepartment(Department department) {
        return teamRepository.findByDepartment(department);
    }

    /**
     * find department leader
     *
     * @param department
     * @return Department leader
     */

    public User getDepartmentLeader(Department department) {
        return userService.getDepartmentLeader(department);
    }
}
