package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.exceptions.VacationException;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.beans.HolidayBean;
import at.qe.sepm.skeleton.utils.TimeConverter;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

@Service
@Scope("application")
public class VacationService {

    @Autowired
    private Logger<String, User> logger;

    @Autowired
    CurrentUserBean currentUserBean;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HolidayBean holidayBean;


    /**
     * A Function to get the current user
     */

    @PostConstruct
    public void init() {
        currentUserBean.init();
    }

    /**
     * Add a new Vacation
     * @param user
     * @param vacation
     * @throws VacationException
     */
    public void addVacation(User user, Vacation vacation) throws VacationException {

        checkVacationDates(user, vacation.getStart(), vacation.getEnd());

        int beginYear = TimeConverter.getYear(vacation.getStart());
        int endYear = TimeConverter.getYear(vacation.getEnd());

        if (beginYear != endYear) {
            vacation.setEnd((TimeConverter.endOfYear(beginYear)));
            Vacation nextYear = new Vacation();
            nextYear.setEnd(vacation.getEnd());
            nextYear.setStart(TimeConverter.beginOfYear(endYear));
            this.addVacation(user, nextYear);
        }

        User managedUser = userService.getManagedUser(user);

        long lengthNewVacation = Duration.between(vacation.getStart(), vacation.getEnd()).toDays();

        for (Instant i = vacation.getStart(); i.isBefore(vacation.getEnd()); i = i.plusSeconds(86400)) {
            if (holidayBean.getDatesOfPublicHolidays().contains(i.truncatedTo(ChronoUnit.DAYS))) {
                lengthNewVacation = lengthNewVacation - 1;
            }
        }

        long totalDays = managedUser.getVacationDays() + lengthNewVacation;


        managedUser.setVacationDays(totalDays);
        managedUser.addVacation(vacation);
        userRepository.save(managedUser);
        logger.logCreation("Vacation of " + user.getUsername(), currentUserBean.getCurrentUser());
        user.addVacation(vacation);
    }

    /**
     * @param user
     * @return Set of Vacation from Current User
     */

    @Transactional
    public Set<Vacation> getVacationFromUser(User user) {
        User current = currentUserBean.getCurrentUser();
        if (!current.getVacations().isEmpty()) {
            return current.getVacations();
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * checks if the dates are valid
     * @param user
     * @param startDate
     * @param endDate
     * @throws VacationException
     */

    public void checkVacationDates(User user, Instant startDate, Instant endDate) throws VacationException {
        if (startDate.isBefore(Calendar.getInstance().toInstant()) || endDate.isBefore(Calendar.getInstance().toInstant())) {
            throw new VacationException("The requested Vacation would already have passed.");
        }
        if (startDate.compareTo(endDate) >= 0) {
            throw new VacationException("You have done nonsense. End must be after the beginning.");
        }
        int beginYear = TimeConverter.getYear(startDate);
        int endYear = TimeConverter.getYear(endDate);

        if (beginYear != endYear) {
            if (beginYear + 1 != endYear) {
                throw new VacationException("Vacation exceeds the limit");
            }
        }

        long lengthNewVacation = Duration.between(startDate, endDate).toDays();


        for (Instant i = startDate; i.isBefore(endDate); i = i.plusSeconds(86400)) {
            if (holidayBean.getPublicHolidays().contains(i.truncatedTo(ChronoUnit.DAYS))) {
                lengthNewVacation = lengthNewVacation - 1;
            }
        }


        long totalDays = user.getVacationDays();

        if (totalDays + lengthNewVacation > Vacation.MAX_VACATION_DAYS_PER_YEAR) {
            throw new VacationException("Limit exceeded .You can't take vacation that long.");
        }
        if (user.hasVacationInTime(startDate, endDate)) {
            throw new VacationException("You can take Vacation in this time.");
        }
    }

}
