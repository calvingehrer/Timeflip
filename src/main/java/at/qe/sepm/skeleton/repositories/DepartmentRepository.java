package at.qe.sepm.skeleton.repositories;


import at.qe.sepm.skeleton.model.Department;

import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface DepartmentRepository extends AbstractRepository<Department, String>{


    Department findByDepartmentName(String departmentName);


    @Query("SELECT d FROM Department d WHERE d.departmentName LIKE CONCAT(:departmentPrefix, '%') ")
    Set<Department> findByDepartmentNamePrefix(@Param("departmentPrefix") String departmentPrefix);

}
