package at.qe.sepm.skeleton.repositories;


import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MailRepository extends AbstractRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.intervall = :interval ")
    List<User> findByInterval(@Param("interval") Interval interval);

}
