package at.qe.sepm.skeleton.repositories;

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

    @Query("SELECT u FROM User u WHERE CONCAT(u.firstName, ' ', u.lastName) = :wholeName")
    List<User> findByWholeNameConcat(@Param("wholeName") String wholeName);

    @Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
    List<User> findByRole(@Param("role") UserRole role);

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT(:usernamePrefix, '%')")
    List<User> findByUsernamePrefix(@Param("usernamePrefix") String usernamePrefix);


    @Query("SELECT u from User u WHERE u.team IS NULL")
    List<User> findUserWithoutTeam();

    @Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles AND u.team = :team")
    User findTeamLeader(@Param("role") UserRole role, @Param("team") Team team);

    @Query("SELECT u  FROM User u WHERE u.team = :team")
    List<User> findUsersOfTeam(@Param("team") Team team);
    /*
    @Query("SELECT u FROM User u WHERE :team MEMBER OF u.teams")
    List<User> findUserOfTeam(@Param("team") Team team);

*/
}
