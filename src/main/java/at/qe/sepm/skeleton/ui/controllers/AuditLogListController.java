package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.services.AuditLogService;
import at.qe.sepm.skeleton.utils.auditlog.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

@Component
@Scope("view")
public class AuditLogListController implements Serializable {

    @Autowired
    private AuditLogService auditLogService;

    private LogEntry logEntry;

    public LogEntry getLogEntry() {
        return logEntry;
    }

    public void setLogEntry(LogEntry logEntry) {
        this.logEntry = logEntry;
    }

    public Collection<LogEntry> getEntries() {
        return auditLogService.getAllEntries();
    }
}
