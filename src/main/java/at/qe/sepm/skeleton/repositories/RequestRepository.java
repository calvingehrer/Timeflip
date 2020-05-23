package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.TaskRequest;
import at.qe.sepm.skeleton.model.RequestEnum;
import at.qe.sepm.skeleton.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends AbstractRepository<TaskRequest,String> {
    @Query("SELECT r FROM TaskRequest r WHERE r.requester=:user AND r.status=:status")
    List<TaskRequest> findAllRequestsOfUser(@Param("user") User user, @Param("status") RequestEnum status);

    @Query("SELECT r FROM TaskRequest r WHERE r.requester=:user")
    List<TaskRequest> findAllRequestsOfRequester(@Param("user") User user);

    @Query("SELECT r FROM TaskRequest r WHERE r.requestHandlerTeamLeader=:user")
    List<TaskRequest> findAllRequestsOfRequestHandlerTL(@Param("user") User user);

    @Query("SELECT r FROM TaskRequest r WHERE r.requestHandlerDepartmentLeader=:user")
    List<TaskRequest> findAllRequestsOfRequestHandlerDL(@Param("user") User user);

    @Query("SELECT r FROM TaskRequest r WHERE (r.requestHandlerTeamLeader=:user OR r.requestHandlerDepartmentLeader=:user) AND r.status=:status")
    List<TaskRequest> findOpenMotionsOfRequestHandler(@Param("user") User user, @Param("status") RequestEnum status);

}
