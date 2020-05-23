package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.repositories.AuditLogRepository;
import at.qe.sepm.skeleton.utils.auditlog.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("application")
public class AuditLogService {

    @Autowired
    AuditLogRepository auditLogRepository;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('DEPARTMENTLEADER')")
    public List<LogEntry> getAllEntries() {
        return auditLogRepository.findAll();
    }
}
