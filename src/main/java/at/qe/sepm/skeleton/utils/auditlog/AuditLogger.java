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

    @Override
    public void logCreation(String objectIdentifier, User changer) {
        LogEntry logEntry = createLogEntry(changer);
        logEntry.setLogActionType(LogEnum.ADD);
        logEntry.setMessage(objectIdentifier + " was added");
        auditLogRepository.save(logEntry);
    }

    @Override
    public void logUpdate(String objectIdentifier, User changer) {
        LogEntry logEntry = createLogEntry(changer);
        logEntry.setLogActionType(LogEnum.UPDATE);
        logEntry.setMessage(objectIdentifier + " was updated");
        auditLogRepository.save(logEntry);
    }

    @Override
    public void logDeletion(String objectIdentifier, User changer) {
        LogEntry logEntry = createLogEntry(changer);
        logEntry.setLogActionType(LogEnum.DELETE);
        logEntry.setMessage(objectIdentifier + " was deleted");
    }

    @Override
    public void logError(Exception e, User executor) {
        LogEntry logEntry = createLogEntry(executor);
        logEntry.setLogActionType(LogEnum.ERROR);
        logEntry.setMessage(e.toString());
    }

    @Override
    public void logLogin(String objectIdentifier, User changer) {
        LogEntry logEntry = createLogEntry(changer);
        logEntry.setLogActionType(LogEnum.LOGIN);
        logEntry.setMessage(objectIdentifier + " was logged in.");
    }

    @Override
    public void logLogout(String objectIdentifier, User changer) {
        LogEntry logEntry = createLogEntry(changer);
        logEntry.setLogActionType(LogEnum.LOGOUT);
        logEntry.setMessage(objectIdentifier + " was logged out.");
    }


    private LogEntry createLogEntry(User changer) {
        LogEntry logEntry = new LogEntry();
        logEntry.setLogDate(Instant.now());
        logEntry.setChangingUser(changer);
        return logEntry;
    }


}
