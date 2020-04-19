package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Scope("application")
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;



    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Department> getAllDepartments(){return departmentRepository.findAll();}


    @PreAuthorize("hasAuthority('ADMIN')")
    public Department saveDepartment(Department department) { return departmentRepository.save(department); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void addNewDepartment(Department department) {
        Department newDepartment = new Department();

        newDepartment.setDepartmentName(department.getDepartmentName());
        newDepartment.setTeams(department.getTeams());
        newDepartment.setHeadOfDepartment(department.getHeadOfDepartment());

        saveDepartment(newDepartment);

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
        Set<Team> teams = department.getTeams();
        if (teams.contains(team)) { teams.remove(team); }
        team.setDepartment(null);
    }

}
