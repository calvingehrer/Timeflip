package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.configs.WebSecurityConfig;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.*;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for accessing and manipulating user data.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("application")
public class UserService {

    @Autowired
    MailService mailService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TimeflipRepository timeflipRepository;

    @Autowired

    private Logger<String, User> logger;

    /**
     * Returns a collection of all users.
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getTestUser() {
        return userRepository.findTestUser();
    }


    /**
     * Returns a list of all users with the given role
     *
     * @param role
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsersByRole(String role) {
        if (role.equals("Admin")) {
            return userRepository.findByRole(UserRole.ADMIN);
        } else if (role.equals("Departmentleader")) {
            return userRepository.findByRole(UserRole.DEPARTMENTLEADER);
        } else if (role.equals("Teamleader")) {
            return userRepository.findByRole(UserRole.TEAMLEADER);
        } else if (role.equals("Employee")) {
            return userRepository.findByRole(UserRole.EMPLOYEE);
        } else {
            return userRepository.findAll();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsersByUsername(String username) {
        return userRepository.findByUsernamePrefix(username);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsersOfTeamByTeamname(String teamname) {
        return userRepository.findByTeamnamePrefix(teamname);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsersOfDepartmentByDepartmentname(String department) {
        return userRepository.findByDepartmentnamePrefix(department);
    }



    /**
     * Loads a single user identified by its username.
     *
     * @param username the username to search for
     * @return the user with the given username
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public User loadUser(String username) {
        return userRepository.findFirstByUsername(username);
    }

    /**
     * Saves the user. This method will also set {@link User# createDate} for new
     * entities or {@link User# updateDate} for updated entities. The user
     * requesting this operation will also be stored as {@link User# createDate}
     * or {@link User# updateUser} respectively.
     *
     * @param user the user to save
     * @return the updated user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public User saveUser(User user) {
        if (user.isNew()) {
            user.setCreateDate(new Date());
            user.setCreateUser(getAuthenticatedUser());
        } else {
            user.setUpdateDate(new Date());
            user.setUpdateUser(getAuthenticatedUser());
        }
        logger.logUpdate(user.getUsername(), getAuthenticatedUser());
        return userRepository.save(user);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewUser(User user) {

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setEnabled(user.isEnabled());
        newUser.setRoles(user.getRoles());
        mailService.sendEmailTo(newUser, "New user added", "You've been added as a new user");
        saveUser(newUser);
        logger.logCreation(newUser.getUsername(), getAuthenticatedUser());
    }

    /**
     * Deletes the user.
     *
     * @param user the user to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(User user) {
        deleteTaskOfUser(user);
        deleteBadgesOfUser(user);
        deleteRequestsOfUser(user);
        Timeflip t = timeflipRepository.findTimeflipOfUser(user);
        if (t != null) {
            timeflipRepository.delete(t);
        }
        userRepository.delete(user);
        logger.logDeletion(user.getUsername(), getAuthenticatedUser());
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }

    @Transactional
    public User getManagedUser(User user) {
        return this.userRepository.findFirstByUsername(user.getUsername());
    }

    protected User setUpdatingFieldsBeforePersist(User toSave) {
        if (toSave.isNew()) {
            if (toSave.getPassword() != null) {
                toSave.getPassword();
                toSave.setPassword(WebSecurityConfig.passwordEncoder().encode(toSave.getPassword()));
            }
            toSave.setCreateUser(getAuthenticatedUser());
        } else {
            toSave.setUpdateUser(getAuthenticatedUser());
        }

        return toSave;
    }





    @Transactional
    public User updateUser(User toSave) {
        logger.logUpdate(toSave.getUsername(), getAuthenticatedUser());
        return userRepository.save(setUpdatingFieldsBeforePersist(toSave));
    }


    public User getTeamLeader(Team team) {
        return userRepository.findTeamLeader(team);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsersWithoutTeam() {
        return userRepository.findEmployeesWithoutTeam();
    }


    public List<User> getUsersOfTeam(Team team) {
        return userRepository.findUsersOfTeam(team);
    }


    public User getDepartmentLeader(Department department) {
        return userRepository.findDepartmentLeader(department);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getTeamLeaderWithoutTeam() {
        return userRepository.findTeamLeadersWithoutTeam();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getDepartmentLeaderWithoutDepartment() {
        return userRepository.findDepartmentLeadersWithoutDepartment();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getUsersWithoutTimeflip() {
        List<User> withoutTimeflip = new ArrayList<>(userRepository.getAllUsers());
        for (Timeflip timeflip : timeflipRepository.findAll()) {
            withoutTimeflip.remove(timeflip.getUser());
        }
        return withoutTimeflip;
    }

    /**
     * deletes all tasks of the user
     * @param user
     */

    public void deleteTaskOfUser (User user) {
        for (Task t: taskRepository.findTasksFromUser(user)) {
            t.setUser(null);
            t.setDepartment(null);
            t.setTeam(null);
            taskRepository.save(t);
            taskRepository.delete(t);
            logger.logDeletion(t.getTask().toString(), getAuthenticatedUser());
        }

    }

    /**
     * deletes all badges of the user
     * @param user
     */

    public void deleteBadgesOfUser(User user) {
        for(Badge b: badgeRepository.findBadgesFromUser(user)) {
            badgeRepository.delete(b);
            logger.logDeletion(b.getBadgeType().toString(), getAuthenticatedUser());
        }
    }

    /**
     * when deleting user delete all open requests
     * when user is a team-leader and the field for department-leader is not null
     * only set the field team-leader null
     * vise versa for department-leader
     * @param user
     */

    public void deleteRequestsOfUser(User user) {
        for (Request r: requestRepository.findAllRequestsOfRequester(user)) {
            requestRepository.delete(r);
            logger.logDeletion(r.getDescription(), getAuthenticatedUser());
        }
        if (user.getRoles().contains(UserRole.TEAMLEADER)) {
            for (Request r : requestRepository.findAllRequestsOfRequestHandlerTL(user)) {
                if (r.getRequestHandlerDepartmentLeader() == null) {
                    requestRepository.delete(r);
                }

                else {
                    r.setRequestHandlerTeamLeader(null);
                    requestRepository.save(r);
                }
            }
        }
        if (user.getRoles().contains(UserRole.DEPARTMENTLEADER)) {
            for (Request r : requestRepository.findAllRequestsOfRequestHandlerDL(user)) {
                if (r.getRequestHandlerTeamLeader() == null) {
                    requestRepository.delete(r);
                }

                else {
                    r.setRequestHandlerDepartmentLeader(null);
                    requestRepository.save(r);
                }
            }
        }
    }
}
