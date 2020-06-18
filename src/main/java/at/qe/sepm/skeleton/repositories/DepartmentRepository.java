package at.qe.sepm.skeleton.repositories;


import at.qe.sepm.skeleton.model.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends AbstractRepository<Department, String> {

    Department findByDepartmentName(String departmentName);
}
