package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.DepartmentRepository;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
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

    @Autowired
    CurrentUserBean currentUserBean;

    @PostConstruct
    public void init() {
        currentUserBean.init();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public Department saveDepartment(Department department) {
        logger.logUpdate(department.toString(), currentUserBean.getCurrentUser());
        return departmentRepository.save(department);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void addNewDepartment(User headOfDepartment, Department department) {
        Department newDepartment = new Department();

        newDepartment.setDepartmentName(department.getDepartmentName());

        saveDepartment(newDepartment);
        headOfDepartment.setDepartment(newDepartment);

        userService.saveUser(headOfDepartment);
        logger.logCreation(headOfDepartment.toString(), currentUserBean.getCurrentUser());
    }

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Department loadDepartment(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDepartment(Department department){
        departmentRepository.delete(department);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void removeTeamfromDepartment(Team team, Department department) {
        Set<Team> teams = new HashSet<>();
        teams.remove(team);
        team.setDepartment(null);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public List<Team> getTeamsOfDepartment (Department department) {
        return teamRepository.findByDepartment(department);
    }

    public User getDepartmentLeader(Department department) {
        return userService.getDepartmentLeader(department);
    }
}
