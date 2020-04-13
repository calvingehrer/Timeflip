package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends AbstractRepository<Team, String>  {


    Team findByTeamName(String teamName);


    @Query("SELECT t FROM Team t WHERE t.leader = :leader")
    List<Team> findByTeamLeader(@Param("leader") User leader);


}
