package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.DepartmentRepository;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
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
    private UserService userService;

    @Autowired
    private Logger<String, User> logger;


    /**
     *
     * @return all departments
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * saves a new department
     * @param department
     * @return saved Department
     */


    @PreAuthorize("hasAuthority('ADMIN')")
    public Department saveDepartment(Department department, Set<Team> addedTeams, Set<Team> removedTeams, User oldLeader, User newLeader) {
        if(addedTeams !=  null) {
            for (Team t:addedTeams) {
                t.setDepartment(department);
                teamRepository.save(t);
            }
        }
        if(removedTeams !=  null) {
            for (Team t:removedTeams) {
                t.setDepartment(null);
                teamRepository.save(t);
            }
        }

        if (oldLeader !=  null) {
            oldLeader.setDepartment(null);
            userService.saveUser(oldLeader);
        }

        if (newLeader !=  null) {
            newLeader.setDepartment(department);
            userService.saveUser(newLeader);
        }

        logger.logUpdate(department.toString(), userService.getAuthenticatedUser());
        return departmentRepository.save(department);

    }

    /**
     *
     * @param headOfDepartment
     * @param department
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addNewDepartment(User headOfDepartment, Department department) {
        Department newDepartment = new Department();

        newDepartment.setDepartmentName(department.getDepartmentName());

        saveDepartment(newDepartment, null,null, null, headOfDepartment);

        logger.logCreation(headOfDepartment.toString(), userService.getAuthenticatedUser());
    }

    /**
     *
     * @param departmentName
     * @return department by department name
     */

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Department loadDepartment(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName);
    }

    /**
     * deletes department
     * @param department
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDepartment(Department department){
        departmentRepository.delete(department);
        logger.logDeletion(department.getDepartmentName(), userService.getAuthenticatedUser());
    }

    /**
     * find  teams of department
     * @param department
     * @return Teams of the department
     */

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public List<Team> getTeamsOfDepartment (Department department) {
        return teamRepository.findByDepartment(department);
    }

    /**
     * find department leader
     * @param department
     * @return Department leader
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public User getDepartmentLeader(Department department) {
        return userService.getDepartmentLeader(department);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Set<Department> getByDepartmentName(String departmentName) {
        return departmentRepository.findByDepartmentNamePrefix(departmentName);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Set<Department> getByTeamName(String teamName) {
        Set<Department> departments = new HashSet<>();
        for (Team t : teamRepository.getAllTeamsByTeamPrefix(teamName)) {
            if (t.getDepartment() != null) {
                departments.add(t.getDepartment());
            }
        }
        return departments;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Set<Department> getByEmployee (String employee) {
        List<User> employees = userService.getAllUsersByUsername(employee);
        Set<Department> departments = new HashSet<>();
        for(User e: employees) {
            if (e.getDepartment() != null)
                departments.add(e.getDepartment());
        }
        return departments;
    }
}
