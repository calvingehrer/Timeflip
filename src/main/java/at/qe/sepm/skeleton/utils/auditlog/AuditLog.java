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
public class AuditLog implements Logger<String, User> {

    @Autowired
    AuditLogRepository auditLogRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void logCreation(String objectIdentifier, User changer) {
        LogEntry logEntry = createLogEntry(changer);
        logEntry.setLogActionType(LogEnum.CREATE);
        logEntry.setMessage(objectIdentifier + " was created");
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

    private LogEntry createLogEntry(User changer) {
        LogEntry logEntry = new LogEntry();
        logEntry.setLogDate(Instant.now());
        logEntry.setChangingUser(changer);
        return logEntry;
    }


}
