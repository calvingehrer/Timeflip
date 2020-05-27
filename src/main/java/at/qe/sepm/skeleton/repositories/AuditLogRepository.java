package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.utils.auditlog.LogEntry;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

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

    @Query("SELECT e FROM LogEntry e")
    List<LogEntry> findAll();

    @Query("SELECT e FROM LogEntry e WHERE e.logActionType LIKE CONCAT(:type,'%')")
    Collection<LogEntry> findbyType(@Param("type") String type);

    @Query("SELECT e FROM LogEntry e WHERE e.logDate LIKE CONCAT(:date,'%')")
    Collection<LogEntry> findByDate(@Param("date") String date);

    @Query("SELECT e FROM LogEntry e WHERE e.changingUser LIKE CONCAT(:user,'%')")
    Collection<LogEntry> findByChangingUser(@Param("user") String changingUser);

    @Query("SELECT e FROM LogEntry e WHERE e.id LIKE CONCAT(:id,'%')")
    Collection<LogEntry> findById(@Param("id") String id);
}
