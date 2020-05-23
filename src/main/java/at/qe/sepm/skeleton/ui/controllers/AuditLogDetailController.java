package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.services.AuditLogService;
import at.qe.sepm.skeleton.utils.auditlog.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope("view")
public class AuditLogDetailController implements Serializable {

    @Autowired
    private AuditLogService auditLogService;

    private LogEntry logEntry;

    public void setLogEntry(LogEntry logEntry) {
        this.logEntry = logEntry;
        //doReloadLogEntry();
    }

    /**
     * Returns the currently displayed LogEntry.
     *
     * @return
     */
    public LogEntry getEntry() {
        return logEntry;
    }

    /**
     * Action to force a reload of the currently displayed room.
     */

    public void doReloadLogEntry() {
        // logEntry = auditLogService.getAllEntries();
    }
}
