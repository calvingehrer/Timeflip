package at.qe.sepm.skeleton.utils.auditlog;


import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.AuditLogRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Scope("application")
public class AuditLogger implements Logger<String, User> {

    @Autowired
    AuditLogRepository auditLogRepository;

    @Autowired
    UserRepository userRepository;


    /**
     * @param objectIdentifier the name of the object
     * @param changer          the changer of the object
     *                         Log Entry when something is added
     */
    @Override
    public void logCreation(String objectIdentifier, User changer) {
        LogEntry logEntry = createLogEntry(changer);
        logEntry.setLogActionType(LogEnum.ADD);
        logEntry.setMessage(objectIdentifier + " was added");
        auditLogRepository.save(logEntry);
    }

    /**
     * Log Entry when something is changed, modified or saved
     *
     * @param objectIdentifier the name of the object
     * @param changer          the changer of the object
     */

    @Override
    public void logUpdate(String objectIdentifier, User changer) {
        LogEntry logEntry = createLogEntry(changer);
        logEntry.setLogActionType(LogEnum.UPDATE);
        logEntry.setMessage(objectIdentifier + " was updated");
        auditLogRepository.save(logEntry);
    }

    /**
     * Log Entry when something is deleted
     *
     * @param objectIdentifier the name of the object
     * @param changer          the changer of the object
     */

    @Override
    public void logDeletion(String objectIdentifier, User changer) {
        LogEntry logEntry = createLogEntry(changer);
        logEntry.setLogActionType(LogEnum.DELETE);
        logEntry.setMessage(objectIdentifier + " was deleted");
        auditLogRepository.save(logEntry);
    }

    /**
     * Log Entry when a ned User is logged in
     *
     * @param e        exception that was thrown
     * @param executor the executor of the operation which threw an error
     */

    @Override
    public void logError(Exception e, User executor) {
        LogEntry logEntry = createLogEntry(executor);
        logEntry.setLogActionType(LogEnum.ERROR);
        logEntry.setMessage(e.toString());
        auditLogRepository.save(logEntry);
    }

    /**
     * Log Entry when new user is logged in
     *
     * @param objectIdentifier the name of the object
     */
    @Override
    public void logLogin(String objectIdentifier) {
        LogEntry logEntry = createLogEntry();
        logEntry.setLogActionType(LogEnum.LOGIN);
        logEntry.setMessage(objectIdentifier + " was logged in.");
        auditLogRepository.save(logEntry);


    }

    /**
     * Log Entry when someone logs out
     *
     * @param objectIdentifier the name of the object
     */

    @Override
    public void logLogout(String objectIdentifier) {
        LogEntry logEntry = createLogEntry();
        logEntry.setLogActionType(LogEnum.LOGOUT);
        logEntry.setMessage(objectIdentifier + " was logged out.");
        auditLogRepository.save(logEntry);
    }

    /**
     * Create the Log Entry in the DB
     *
     * @param changer
     * @return
     */
    private LogEntry createLogEntry(User changer) {
        LogEntry logEntry = new LogEntry();
        logEntry.setLogDate(Instant.now());
        logEntry.setChangingUser(changer);
        return logEntry;
    }

    private LogEntry createLogEntry() {
        LogEntry logEntry = new LogEntry();
        logEntry.setLogDate(Instant.now());
        return logEntry;
    }


}
