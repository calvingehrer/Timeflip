package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.RaspberryRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
@Scope("application")
public class RaspberryService {

    @Autowired
    RaspberryRepository raspberryRepository;
    @Autowired
    UserRepository userRepository;

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
    public List<Raspberry> getAllRaspberries() {
        return raspberryRepository.findAll();
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewRaspberry(Raspberry raspberry, Room room) {
        Raspberry newRaspberry = new Raspberry();
        newRaspberry.setRaspberryId(raspberry.getRaspberryId());
        newRaspberry.setRoom(room);
        saveRaspberry(newRaspberry);
        // add raspberry
        logger.logCreation(raspberry.toString(), currentUser);
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Raspberry loadRaspberry(String raspberryID) {
        return raspberryRepository.findByRaspberryId(raspberryID);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Raspberry saveRaspberry(Raspberry raspberry) {
        if (raspberry.isNew()) {
            raspberry.setCreateDate(new Date());
            raspberry.setCreateUser(getAuthenticatedUser());
        }
        return raspberryRepository.save(raspberry);
    }

    /**
     * Deletes the raspberry.
     *
     * @param raspberry the raspberry to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRaspberry(Raspberry raspberry) {
        raspberryRepository.delete(raspberry);
    }


}
