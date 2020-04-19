package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.exceptions.VacationException;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Vacation;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

public interface VacationService {

    @PreAuthorize("hasAuthority('ADMIN')")
    void addVacation(User user, Vacation vacation) throws VacationException;

    @PreAuthorize("hasAuthority('ADMIN')")
    Set<Vacation> getVacationFromUser(User user);

}
