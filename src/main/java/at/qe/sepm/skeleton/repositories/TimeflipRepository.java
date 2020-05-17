package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeflipRepository extends AbstractRepository<Timeflip, String> {


    Timeflip findByMacAddress(String macAddress);


    @Query("SELECT t FROM Timeflip t WHERE t.macAddress = :macAddress")
    List<Timeflip> findAllTimeflipsByMacAddress(@Param("macAddress") String macAddress);

    @Query("SELECT t FROM Timeflip t WHERE t.user = :user")
    List<Timeflip> findTimeflipOfUser(@Param("user") User user);

}
