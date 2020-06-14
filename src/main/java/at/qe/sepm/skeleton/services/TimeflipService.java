package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TimeflipRepository;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
@Scope("application")
public class TimeflipService {


    @Autowired
    private TimeflipRepository timeflipRepository;

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAuthority('ADMIN')")
    public Timeflip getTimeFlipByAddress(String macAddress){
        return timeflipRepository.findByMacAddress(macAddress);
    }

    @Autowired
    private Logger<String, User> logger;



    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Timeflip> getAllTimeflips() {
        return timeflipRepository.findAll();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Timeflip> getAllTimeflipsByMacAddress(String macAddress) {
        return timeflipRepository.findAllTimeflipsByMacAddress(macAddress);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewTimeflip(Timeflip timeflip, User user) {

        Timeflip newTimeflip = new Timeflip();
        newTimeflip.setMacAddress(timeflip.getMacAddress());
        newTimeflip.setUser(user);
        saveTimeflip(newTimeflip);
        logger.logCreation(timeflip.getId(), userService.getAuthenticatedUser());
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public Timeflip saveTimeflip(Timeflip timeflip) {
        logger.logUpdate(timeflip.getId(),userService.getAuthenticatedUser());
        return timeflipRepository.save(timeflip);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTimeflip(Timeflip timeflip) {


        timeflip.setRaspberry(null);
        timeflip.setTasks(null);
        timeflip.setUser(null);
        timeflip.setCreateDate(null);

        timeflipRepository.delete(timeflip);
        logger.logDeletion(timeflip.getId(), userService.getAuthenticatedUser());
    }


    @PreAuthorize("hasAuthority('EMPLOYEE') or principal.username eq #username")
    public Timeflip loadTimeflip(String timeflipId) {
        return timeflipRepository.findByMacAddress(timeflipId);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('ADMIN')")
    public Timeflip getTimeflipOfUser(User currentUser){
        return timeflipRepository.findTimeflipOfUser(currentUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTimeFlipOfUser(User user) {
        Timeflip timeflip = timeflipRepository.findTimeflipOfUser(user);
        if (timeflip != null) {
            timeflip.setUser(null);
            timeflipRepository.save(timeflip);
            timeflipRepository.delete(timeflip);
        }
        logger.logDeletion(timeflip.getId(), user);
    }

    public List<Timeflip> getTimeflipsByUserPrefix(String userName) {
        return timeflipRepository.findTimflipsByUserPrefix(userName);
    }



}
