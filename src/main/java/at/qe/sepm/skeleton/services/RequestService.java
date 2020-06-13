package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.RequestRepository;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
@Scope("application")
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @Autowired
    TimeBean timeBean;

    @Autowired
    private Logger<String, User> logger;


    public void addRequest(Request request, User requester, String message) {
        User requestHandler1 = userService.getTeamLeader(requester.getTeam());
        if (requester.equals(requestHandler1)) {
            requestHandler1 = null;
        }
        User requestHandler2 = userService.getDepartmentLeader(requester.getDepartment());
        request.setStatus(RequestEnum.OPEN);
        request.setDescription(message);
        request.setRequester(requester);
        request.setRequestHandlerTeamLeader(requestHandler1);
        request.setRequestHandlerDepartmentLeader(requestHandler2);
        request.setCreateDate(new Date());
        requestRepository.save(request);
        logger.logCreation(request.getDescription(), requester);
    }


    public void addTaskRequest(User requester, Instant requestedStartDate, Instant requestedEndDate, TaskEnum type, String message) {
        TaskRequest r = new TaskRequest();
        r.setRequestedStartDate(requestedStartDate);
        r.setRequestedEndDate(requestedEndDate);
        r.setTaskType(type);
        addRequest(r, requester, message);
        String pattern = "MM-dd-yyyy HH,mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String startDate = simpleDateFormat.format(timeBean.instantToDate(requestedStartDate));
        String endDate = simpleDateFormat.format(timeBean.instantToDate(requestedEndDate));
        mailService.sendEmailTo(requester, "Request sent", "Your request to change the time between " + startDate + " and " + endDate + " to " + type.toString() + " has been sent.");

    }

    public void addVacationRequest(User requester, Date requestedStartDate, Date requestedEndDate, String message) {
        VacationRequest r = new VacationRequest();
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String startDate = simpleDateFormat.format(requestedStartDate);
        String endDate = simpleDateFormat.format(requestedEndDate);
        r.setRequestedStartDate(requestedStartDate);
        r.setRequestedEndDate(requestedEndDate);
        addRequest(r, requester, message);
        mailService.sendEmailTo(requester, "Request sent", "Your request to take a vacation from  " + startDate + " to  " + endDate + " has been sent.");

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
        logger.logDeletion(request.getDescription(), userService.getAuthenticatedUser());
    }


}
