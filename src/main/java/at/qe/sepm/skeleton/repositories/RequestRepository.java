package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Request;
import at.qe.sepm.skeleton.model.RequestEnum;
import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends AbstractRepository<Request,String> {
    @Query("SELECT r FROM Request r WHERE r.requester=:user AND r.status=:status")
    List<Request> findAllRequestsOfUser(@Param("user") User user, @Param("status") RequestEnum status);

    @Query("SELECT r FROM Request r WHERE r.requester=:user")
    List<Request> findAllRequestsOfRequester(@Param("user") User user);

    @Query("SELECT r FROM Request r WHERE r.requestHandlerTeamLeader=:user")
    List<Request> findAllRequestsOfRequestHandlerTL(@Param("user") User user);

    @Query("SELECT r FROM Request r WHERE r.requestHandlerDepartmentLeader=:user")
    List<Request> findAllRequestsOfRequestHandlerDL(@Param("user") User user);

    @Query("SELECT r FROM Request r WHERE (r.requestHandlerTeamLeader=:user OR r.requestHandlerDepartmentLeader=:user) AND r.status=:status")
    List<Request> findOpenMotionsOfRequestHandler(@Param("user") User user, @Param("status") RequestEnum status);

}
