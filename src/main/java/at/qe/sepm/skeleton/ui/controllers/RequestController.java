package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.RequestService;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.services.VacationService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("view")
public class RequestController implements Serializable  {
    @Autowired
    RequestService requestService;

    @Autowired
    VacationService vacationService;

    @Autowired
    UserService userService;

    @Autowired
    CurrentUserBean currentUserBean;

    @Autowired
    private TaskService taskService;


    /**
     * A Function to get the current user
     */
    @PostConstruct
    public void init() {
        currentUserBean.init();
    }

    /**
     * Function to get open requests of taskRequest handler
     * @return all the requests the leader has yet to edit
     */

    public List<Request> getOpenRequestsLeader() {
        return requestService.getAllOpenRequestsOfLeader(currentUserBean.getCurrentUser());
    }

    /**
     * Function to get open requests of requester
     * @return all the requests of the employee that have yet to be edited
     */

    public List<Request> getOpenRequestsEmployee() { return requestService.getOpenRequestsOfEmployee(currentUserBean.getCurrentUser()); }

    /**
     * Function to get accepted requests of requester
     * @return all accepted requests of user
     */

    public List<Request> getAcceptedRequestsEmployee() { return requestService.getAcceptedRequestsOfEmployee(currentUserBean.getCurrentUser()); }

    /**
     * Function to get declined requests of requester
     * @return all declined requests of user
     */

    public List<Request> getDeclinedRequestsEmployee() { return requestService.getDeclinedRequestsOfEmployee(currentUserBean.getCurrentUser()); }

    /**
     * Function to accept a request
     * @param request
     */
    public void acceptRequest(Request request) {
        if (request.getDiscriminatorValue() == 1) {
            assert request instanceof TaskRequest;
            TaskRequest tr = (TaskRequest) request;
            try {
                taskService.saveEditedTask(tr.getRequester(), tr.getTaskType(), tr.getRequestedStartDate(), tr.getRequestedEndDate());
            }
            catch (Exception e) {
                MessagesView.errorMessage("Edit Tasks", e.getMessage());
                requestService.declineRequest(request);
                return;
            }
        }
        if (request.getDiscriminatorValue() == 2) {
            assert request instanceof VacationRequest;
            VacationRequest vr = (VacationRequest) request;
            Vacation vacation = new Vacation();
            vacation.setStart(vr.getRequestedStartDate().toInstant());
            vacation.setEnd(vr.getRequestedEndDate().toInstant());
            try {
                vacationService.checkVacationDates(vr.getRequester(), vacation.getStart(), vacation.getEnd());
                vacationService.addVacation(vr.getRequester(), vacation);
            }
            catch (Exception e) {
                MessagesView.errorMessage("Vacation", "The granted vacation ist not valid, it is denied");
                requestService.declineRequest(request);
                return;
            }
        }
        requestService.acceptRequest(request);
    }

    /**
     * Function to decline a taskRequest
     * @param request
     */
    public void declineRequest(Request request) {
        requestService.declineRequest(request);
    }

    /**
     * If a taskRequest is declined or was already used the user can delete it to keep an overview
     * @param request
     */
    public void deleteRequest(Request request) {
        requestService.deleteRequest(request);
    }

    public List<Request> getAcceptedTaskRequestsEmployee() { return requestService.getAcceptedRequestsOfEmployee(currentUserBean.getCurrentUser())
            .stream()
            .filter(request -> request.getDiscriminatorValue() == 1)
            .collect(Collectors.toList()); }
}
