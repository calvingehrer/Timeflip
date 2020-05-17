package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TimeflipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
@Scope("application")
public class TimeflipService {


    @Autowired
    TimeflipRepository timeflipRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private Logger<String, User> logger;

    @Autowired
    UserService userService;
    User currentUser;

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
        logger.logCreation(timeflip.getId(), currentUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Timeflip saveTimeflip(Timeflip timeflip) {
        logger.logUpdate(timeflip.getId(), currentUser);
        return timeflipRepository.save(timeflip);


    }


}
