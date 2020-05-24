package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Component
@Scope("view")
public class DepartmentListController implements Serializable {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CurrentUserBean currentUserBean;

    @PostConstruct
    public void init() {
        currentUserBean.init();
    }

    /**
     * @return all department
     */

    public Collection<Department> getDepartments(){
        return departmentService.getAllDepartments();
    }

    /**
     * @param department
     * @return all teams in the department
     */

    public List<Team> getTeamsOfDepartment (Department department) {
        return departmentService.getTeamsOfDepartment(department);
    }

    /**
     * @param department
     * @return the head of the department
     */

    public User getDepartmentLeader(Department department) {
        return departmentService.getDepartmentLeader(department);
    }

    public List<Team> getTeamsOfLeadersDepartment() {
        return getTeamsOfDepartment(currentUserBean.getCurrentUser().getDepartment());
    }
}
