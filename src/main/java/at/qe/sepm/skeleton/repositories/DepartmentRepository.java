package at.qe.sepm.skeleton.repositories;


import at.qe.sepm.skeleton.model.Department;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends AbstractRepository<Department, String>{


    @Query("SELECT d FROM Department d WHERE d.departmentName = :departmentName ")
    Department findByDepartmentName(@Param("departmentName") String departmentName);

    @Query("SELECT d FROM Department d WHERE d.headOfDepartment = :headOfDepartment ")
    List<Department> findByHeadOfDepartment(@Param("headOfDepartment") String headOfDepartment);




}
