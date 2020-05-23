package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.TaskRequest;
import at.qe.sepm.skeleton.services.RequestService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class RequestController implements Serializable  {
    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @Autowired
    CurrentUserBean currentUserBean;

    private TaskRequest taskRequest;


    /**
     * A Function to get the current user
     */
    @PostConstruct
    public void init() {
        currentUserBean.init();
    }

    public TaskRequest getTaskRequest() {
        return taskRequest;
    }

    public void setTaskRequest(TaskRequest taskRequest) {
        this.taskRequest = taskRequest;
    }

    /**
     * Function to get open requests of taskRequest handler
     * @return all the requests the leader has yet to edit
     */

    public List<TaskRequest> getOpenRequestsLeader() {
        return requestService.getAllOpenRequestsOfLeader(currentUserBean.getCurrentUser());
    }

    /**
     * Function to get open requests of requester
     * @return all the requests of the employee that have yet to be edited
     */

    public List<TaskRequest> getOpenRequestsEmployee() { return requestService.getOpenRequestsOfEmployee(currentUserBean.getCurrentUser()); }

    /**
     * Function to get accepted requests of requester
     * @return all accepted requests of user
     */

    public List<TaskRequest> getAcceptedRequestsEmployee() { return requestService.getAcceptedRequestsOfEmployee(currentUserBean.getCurrentUser()); }

    /**
     * Function to get declined requests of requester
     * @return all declined requests of user
     */

    public List<TaskRequest> getDeclinedRequestsEmployee() { return requestService.getDeclinedRequestsOfEmployee(currentUserBean.getCurrentUser()); }

    /**
     * Function to accept a taskRequest
     * @param taskRequest
     */
    public void acceptRequest(TaskRequest taskRequest) {
        requestService.acceptRequest(taskRequest);
    }

    /**
     * Function to decline a taskRequest
     * @param taskRequest
     */
    public void declineRequest(TaskRequest taskRequest) {
        requestService.declineRequest(taskRequest);
    }

    /**
     * If a taskRequest is declined or was already used the user can delete it to keep an overview
     */
    public void deleteRequest(TaskRequest taskRequest) {
        requestService.deleteRequest(taskRequest);
    }

}
