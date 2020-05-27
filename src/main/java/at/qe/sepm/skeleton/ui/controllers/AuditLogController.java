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
public class AuditLogController implements Serializable {

    @Autowired
    private AuditLogService auditLogService;

    private String id = "";
    private String type = "";
    private String date = "";
    private String changingUser = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChangingUser() {
        return changingUser;
    }

    public void setChangingUser(String changingUser) {
        this.changingUser = changingUser;
    }

    public Collection<LogEntry> logEntries() {
        if (!type.equals("")) {
            return auditLogService.getAllEntriesByType(type);
        }
        if (!date.equals("")) {
            return auditLogService.getAllEntriesByDate(date);
        }
        if (!changingUser.equals("")) {
            return auditLogService.getAllEntriesByChangingUser(changingUser);
        }
        return auditLogService.getAllEntriesByID(id);
    }

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

    public void resetFilter() {
        this.id = "";
        this.changingUser = "";
        this.type = "";
        this.date = "";


    }
}
