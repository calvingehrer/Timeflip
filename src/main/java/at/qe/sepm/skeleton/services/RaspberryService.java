package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.RaspberryRepository;
import at.qe.sepm.skeleton.repositories.RoomRepository;
import at.qe.sepm.skeleton.repositories.TimeflipRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
@Scope("application")
public class RaspberryService {

    @Autowired
    private RaspberryRepository raspberryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TimeflipRepository timeflipRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private Logger<String, User> logger;



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
        logger.logCreation(raspberry.getId(),userService.getAuthenticatedUser());
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
        logger.logUpdate(raspberry.getId(), userService.getAuthenticatedUser());
        return raspberryRepository.save(raspberry);
    }

    /**
     * Deletes the raspberry.
     * sets the raspberry fields in other classes to null
     *
     * @param raspberry the raspberry to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void deleteRaspberry(Raspberry raspberry) {
        Room room = raspberry.getRoom();
        if(room != null){
            room.setRaspberry(null);
            room.setEquipped(false);
        }

        raspberry.setRoom(null);
        for (Timeflip t: timeflipRepository.findTimeflipsOfRaspberrys(raspberry)) {
            t.setRaspberry(null);
        }
        raspberryRepository.delete(raspberry);
        logger.logDeletion(raspberry.getId(), userService.getAuthenticatedUser());
    }


}
