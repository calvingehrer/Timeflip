package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.configs.WebSecurityConfig;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
    TaskService taskService;

    @Autowired
    BadgeService badgeService;

    @Autowired
    RequestService requestService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TimeflipService timeflipService;

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
    }

    /**
     * Deletes the user.
     *
     * @param user the user to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(User user) {
        taskService.deleteTaskOfUser(user);
        badgeService.deleteBadgesOfUser(user);
        requestService.deleteRequestsOfUser(user);
        timeflipService.deleteTimeFlipOfUser(user);
        userRepository.delete(user);
        // :TODO: write some audit log stating who and when this user was permanently deleted.
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
        for (Timeflip timeflip : timeflipService.getAllTimeflips()) {
            if (withoutTimeflip.contains(timeflip.getUser())) {
                withoutTimeflip.remove(timeflip.getUser());
            }
        }
        return withoutTimeflip;
    }
}
