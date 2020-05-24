package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.utils.auditlog.LogEntry;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

/**
 * Manages auditLogEntrys.
 * As only saving is required, no more methods are supported
 */
@Component
@Scope("application")
public interface AuditLogRepository extends Repository<LogEntry, Long> {

    /**
     * Saves a given AuditLogEntry.
     *
     * @param entry the entry to save
     * @return the saved entry
     */
    LogEntry save(LogEntry entry);

}
