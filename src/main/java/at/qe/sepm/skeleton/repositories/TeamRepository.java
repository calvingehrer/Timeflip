package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends AbstractRepository<Team, String> {


    Team findByTeamName(String teamName);

    @Query("SELECT t FROM Team t WHERE t.teamName LIKE CONCAT(:teamPrefix, '%')")
    List<Team> getAllTeamsByTeamPrefix(@Param("teamPrefix") String teamPrefix);

    @Query("SELECT t FROM Team t WHERE t.department IS NULL")
    List<Team> getTeamsWithoutDepartment();

    @Query("SELECT t FROM Team t WHERE t.teamName != :teamname")
    List<Team> getUsersNotInTeam(@Param("teamname") String teamname);

    @Query("SELECT t FROM Team t WHERE t.department = :department")
    List<Team> findByDepartment(@Param("department") Department department);


}
