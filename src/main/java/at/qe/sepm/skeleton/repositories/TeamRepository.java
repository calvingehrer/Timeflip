package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.CollectionTable;
import java.util.Collection;
import java.util.List;

public interface TeamRepository extends AbstractRepository<Team, String>  {


    Team findByTeamName(String teamName);


    @Query("SELECT t FROM Team t WHERE t.leader = :leader")
    Collection<Team> findByTeamLeader(@Param("leader") User leader);

    @Query("SELECT t FROM Team t WHERE t.teamName = :teamName")
    Collection<User> findByTeamName2(@Param("teamName") String teamName);

    @Query("SELECT t FROM Team t WHERE t.teamName LIKE CONCAT(:teamPrefix, '%')")
    Collection<Team> getAllTeamsByTeamPrefix(@Param("teamPrefix") String teamPrefix);

    @Query("SELECT t FROM Team t WHERE t.department IS NULL")
    Collection<Team> getTeamsWithoutDepartment();

}
