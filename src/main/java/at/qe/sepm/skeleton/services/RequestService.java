package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Request;
import at.qe.sepm.skeleton.model.RequestEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.repositories.RequestRepository;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
@Scope("application")
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    CurrentUserBean currentUserBean;
    @Autowired
    private Logger<String, User> logger;

    /**
     * A Function to get the current user
     */

    @PostConstruct
    public void init() {
        currentUserBean.init();
    }


    public void addRequest(User requester, User requestHandler1, User requestHandler2, Date requestedDate, RequestEnum status, String message) {
        Request r = new Request();
        r.setStatus(status);
        r.setDescription(message);
        r.setRequester(requester);
        r.setRequestHandlerTeamLeader(requestHandler1);
        r.setRequestHandlerDepartmentLeader(requestHandler2);
        r.setCreateDate(new Date());
        r.setRequestedDate(requestedDate);
        requestRepository.save(r);
        logger.logCreation(r.getDescription(), requester);
    }

    @PreAuthorize("hasAuthority('TEAMLEADER') or hasAuthority('DEPARTMENTLEADER')")
    public List<Request> getAllOpenRequestsOfLeader(User user) {
        return requestRepository.findOpenMotionsOfRequestHandler(user, RequestEnum.OPEN);
    }

    @PreAuthorize("hasAuthority('TEAMLEADER') or hasAuthority('DEPARTMENTLEADER')")
    public void acceptRequest(Request request) {
        request.setStatus(RequestEnum.ACCEPTED);
        requestRepository.save(request);
    }


    @PreAuthorize("hasAuthority('TEAMLEADER') or hasAuthority('DEPARTMENTLEADER')")
    public void declineRequest(Request request) {
        request.setStatus(RequestEnum.DECLINED);
        requestRepository.save(request);
    }

    public List<Request> getOpenRequestsOfEmployee(User user) { return requestRepository.findAllRequestsOfUser(user, RequestEnum.OPEN); }

    public List<Request> getAcceptedRequestsOfEmployee(User user) { return requestRepository.findAllRequestsOfUser(user, RequestEnum.ACCEPTED); }

    public List<Request> getDeclinedRequestsOfEmployee(User user) { return requestRepository.findAllRequestsOfUser(user, RequestEnum.DECLINED); }

    public void deleteRequest (Request request) {
        request.setRequester(null);
        request.setRequestHandlerTeamLeader(null);
        request.setRequestHandlerDepartmentLeader(null);
        requestRepository.delete(request);
        logger.logDeletion(request.getDescription(), currentUserBean.getCurrentUser());
    }


}
