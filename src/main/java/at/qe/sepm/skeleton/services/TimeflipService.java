package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TimeflipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    public Timeflip getTimeFlipByAddress(String macAddress){
        return timeflipRepository.findByMacAddress(macAddress);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Timeflip> getAllTimeflips(){
        return timeflipRepository.findAll();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Timeflip> getAllTimeflipsByMacAddress(String macAddress){
        return timeflipRepository.findAllTimeflipsByMacAddress(macAddress);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewTimeflip(Timeflip timeflip, User user, Raspberry raspberry) {

        Timeflip newTimeflip = new Timeflip();
        newTimeflip.setMacAddress(timeflip.getMacAddress());
        newTimeflip.setUser(user);
        newTimeflip.setRaspberry(raspberry);
        saveTimeflip(newTimeflip);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Timeflip saveTimeflip(Timeflip timeflip) {
        if (timeflip.isNew()) {
            timeflip.setCreateDate(new Date());
        } else {
            timeflip.setHistoryTime(new Date());
        }
        return timeflipRepository.save(timeflip);

    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public List<Timeflip> getTimeflipOfUser(User currentUser){
        return timeflipRepository.findTimeflipOfUser(currentUser);
    }

    public void deleteTimeFlipOfUser(User user) {
        Timeflip timeflip = timeflipRepository.findTimeflipOfUser(user);
        if (timeflip != null) {
            timeflipRepository.delete(timeflip);
        }
    }


}
