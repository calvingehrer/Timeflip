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

import java.util.List;
import java.util.Set;


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
    public List<Timeflip> getAllTimeflips(){
        return timeflipRepository.findAll();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Timeflip> getAllTimeflipsByMacAddress(String macAddress){
        return timeflipRepository.findAllTimeflipsByMacAddress(macAddress);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewTimeflip(Timeflip timeflip, User user) {

        Timeflip newTimeflip = new Timeflip();
        newTimeflip.setMacAddress(timeflip.getMacAddress());
        newTimeflip.setUser(user);
        saveTimeflip(newTimeflip);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Timeflip saveTimeflip(Timeflip timeflip) {

        return timeflipRepository.save(timeflip);


    }


}