package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Request;
import at.qe.sepm.skeleton.model.RequestEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Date;

@Component
@Scope("application")
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

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

    public void deleteRequest (Request request) { requestRepository.delete(request);}

    /**
     * when deleting user delete all open requests
     * when user is a team-leader and the field for department-leader is not null
     * only set the field team-leader null
     * vise versa for department-leader
     * @param user
     */

    public void deleteRequestsOfUser(User user) {
        for (Request r: requestRepository.findAllRequestsOfRequester(user)) {
            deleteRequest(r);
        }
        if (user.getRoles().contains(UserRole.TEAMLEADER)) {
            for (Request r : requestRepository.findAllRequestsOfRequestHandlerTL(user)) {
                if (r.getRequestHandlerDepartmentLeader() == null) {
                    deleteRequest(r);
                }

                else {
                    r.setRequestHandlerTeamLeader(null);
                    requestRepository.save(r);
                }
            }
        }
        if (user.getRoles().contains(UserRole.DEPARTMENTLEADER)) {
            for (Request r : requestRepository.findAllRequestsOfRequestHandlerDL(user)) {
                if (r.getRequestHandlerTeamLeader() == null) {
                    deleteRequest(r);
                }

                else {
                    r.setRequestHandlerDepartmentLeader(null);
                    requestRepository.save(r);
                }
            }
        }
    }
}