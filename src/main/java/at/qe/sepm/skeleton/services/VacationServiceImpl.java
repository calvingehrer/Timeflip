package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.exceptions.VacationException;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.utils.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

@Service
@Scope("application")
public class VacationServiceImpl implements VacationService {

    @Autowired
    private UserService userService;


    @Override
    @Transactional
    public void addVacation(User user, Vacation vacation) throws VacationException {
        if (vacation.getStart().compareTo(vacation.getEnd()) >= 0) {
            throw new VacationException("You have done nonesense. End must be after the beginning.");
        }
        int beginYear = TimeConverter.getYear(vacation.getEnd());
        int endYear = TimeConverter.getYear(vacation.getEnd());

        if (beginYear != endYear) {
            if (beginYear + 1 != endYear) {
                throw new VacationException("Vacation exceeds the limit");
            } else {
                vacation.setEnd((TimeConverter.endOfYear(beginYear)));
                Vacation nextYear = new Vacation();
                nextYear.setEnd(vacation.getEnd());
                nextYear.setStart(TimeConverter.beginOfYear(endYear));
                this.addVacation(user, nextYear);
            }
        }
        User managedUser = this.userService.getAuthenticatedUser();

        long lengthNewVacation = Duration.between(vacation.getStart(), vacation.getEnd()).toDays();

        long totalDays = managedUser.getVacations().stream().filter(x -> TimeConverter.getYear(x.getStart()) == beginYear).map(x -> Duration.between(x.getStart(), x.getEnd()).toDays()).mapToLong(Long::valueOf).sum();

        if (totalDays + lengthNewVacation > Vacation.MAX_VACATION_DAYS_PER_YEAR) {
            throw new VacationException("Limit exceeded .You can't take vacation that long.");
        }
        if (managedUser.hasVacationInTime(vacation.getStart(), vacation.getEnd())) {
            throw new VacationException("You can take Vacation in this time.");
        }

        managedUser.addVacation(vacation);
    }

    @PreAuthorize("@customPermissionEvaluator.hasPermission(EMPLOYEE) or principal.username eq user.username")
    @Override
    @Transactional
    public Set<Vacation> getVacationFromUser(User user) {
        User managedUser = this.userService.getManagedUser(user);
        if (!managedUser.getVacations().isEmpty()) {
            return managedUser.getVacations();
        } else {
            return Collections.emptySet();
        }
    }
}
