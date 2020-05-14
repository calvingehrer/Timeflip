package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Request;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.RequestService;
import at.qe.sepm.skeleton.services.UserService;
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

    private User currentUser;

    private Request request;

    /**
     * A Function to get the current user
     */
    @PostConstruct
    public void init() {
        this.setCurrentUser(userService.getAuthenticatedUser());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * Function to get open requests of request handler
     * @return all the requests the leader has yet to edit
     */

    public List<Request> getOpenRequestsLeader() {
        return requestService.getAllOpenRequestsOfLeader(getCurrentUser());
    }

    /**
     * Function to get open requests of requester
     * @return all the requests of the employee that have yet to be edited
     */

    public List<Request> getOpenRequestsEmployee() { return requestService.getOpenRequestsOfEmployee(getCurrentUser()); }

    /**
     * Function to get accepted requests of requester
     * @return all accepted requests of user
     */

    public List<Request> getAcceptedRequestsEmployee() { return requestService.getAcceptedRequestsOfEmployee(getCurrentUser()); }

    /**
     * Function to get declined requests of requester
     * @return all declined requests of user
     */

    public List<Request> getDeclinedRequestsEmployee() { return requestService.getDeclinedRequestsOfEmployee(getCurrentUser()); }

    /**
     * Function to accept a request
     * @param request
     */
    public void acceptRequest(Request request) {
        requestService.acceptRequest(request);
    }

    /**
     * Function to decline a request
     * @param request
     */
    public void declineRequest(Request request) {
        requestService.declineRequest(request);
    }

    /**
     * If a request is declined or was already used the user can delete it to keep an overview
     */
    public void deleteRequest(Request request) {
        System.out.println("hello");
        requestService.deleteRequest(this.request);
    }

    public void editDate(Request request) {
        System.out.println(request);
    }

}
