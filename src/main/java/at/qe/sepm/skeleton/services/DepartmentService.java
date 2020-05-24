package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.DepartmentRepository;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
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
    public Department saveDepartment(Department department) {
        logger.logUpdate(department.toString(), currentUserBean.getCurrentUser());
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

        saveDepartment(newDepartment);
        headOfDepartment.setDepartment(newDepartment);

        userService.saveUser(headOfDepartment);
        logger.logCreation(headOfDepartment.toString(), currentUserBean.getCurrentUser());
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

    public User getDepartmentLeader(Department department) {
        return userService.getDepartmentLeader(department);
    }
}
