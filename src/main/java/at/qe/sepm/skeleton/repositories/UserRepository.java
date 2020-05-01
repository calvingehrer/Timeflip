package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import java.util.List;

import at.qe.sepm.skeleton.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for managing {@link User} entities.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
public interface UserRepository extends AbstractRepository<User, String> {

    User findFirstByUsername(String username);

    List<User> findByUsernameContaining(String username);


    @Query("SELECT u FROM User u WHERE 'Admin' = u.firstName or 'Miranda' = u.firstName")
    List<User> findTestUser();


    @Query("SELECT u FROM User u WHERE CONCAT(u.firstName, ' ', u.lastName) = :wholeName")
    List<User> findByWholeNameConcat(@Param("wholeName") String wholeName);

    @Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
    List<User> findByRole(@Param("role") UserRole role);

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT(:usernamePrefix, '%')")
    List<User> findByUsernamePrefix(@Param("usernamePrefix") String usernamePrefix);

    @Query("SELECT u from User u WHERE u.team IS NULL AND 'TEAMLEADER' NOT MEMBER OF u.roles AND 'DEPARTMENTLEADER' NOT MEMBER OF u.roles AND 'ADMIN' NOT MEMBER OF u.roles" )
    List<User> findEmployeesWithoutTeam();

    @Query("SELECT u FROM User u WHERE 'TEAMLEADER' MEMBER OF u.roles AND u.team = :team")
    User findTeamLeader(@Param("team") Team team);

    @Query("SELECT u  FROM User u WHERE u.team = :team")
    List<User> findUsersOfTeam(@Param("team") Team team);

    @Query("SELECT u FROM User u WHERE 'DEPARTMENTLEADER' MEMBER OF u.roles AND u.department = :department")
    User findDepartmentLeader (@Param("department") Department department);

    @Query("SELECT u FROM User u WHERE u.team IS NULL AND 'TEAMLEADER' MEMBER  OF u.roles")
    List<User> findTeamLeadersWithoutTeam();

    @Query("SELECT u FROM User u WHERE u.department IS NULL AND 'DEPARTMENTLEADER' MEMBER  OF u.roles")
    List<User> findDepartmentLeadersWithoutDepartment();
}
