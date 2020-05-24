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
        auditLogRepository.save(logEntry);
    }

    @Override
    public void logError(Exception e, User executor) {
        LogEntry logEntry = createLogEntry(executor);
        logEntry.setLogActionType(LogEnum.ERROR);
        logEntry.setMessage(e.toString());
        auditLogRepository.save(logEntry);
    }

    @Override
    public void logLogin(String objectIdentifier) {
        LogEntry logEntry = createLogEntry(objectIdentifier);
        logEntry.setLogActionType(LogEnum.LOGIN);
        logEntry.setMessage(" was logged in.");
        auditLogRepository.save(logEntry);


    }

    @Override
    public void logLogout(String objectIdentifier) {
        LogEntry logEntry = createLogEntry(objectIdentifier);
        logEntry.setLogActionType(LogEnum.LOGOUT);
        logEntry.setMessage(objectIdentifier + " was logged out.");
        auditLogRepository.save(logEntry);
    }


    private LogEntry createLogEntry(User changer) {
        LogEntry logEntry = new LogEntry();
        logEntry.setLogDate(Instant.now());
        logEntry.setChangingUser(new User());

        return logEntry;
    }

    private LogEntry createLogEntry(String user) {
        LogEntry logEntry = new LogEntry();
        logEntry.setLogDate(Instant.now());
        logEntry.setChangingUser(userRepository.findDefaultUser());
        return logEntry;
    }


}
